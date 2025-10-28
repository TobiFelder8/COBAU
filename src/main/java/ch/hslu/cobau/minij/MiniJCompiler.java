package ch.hslu.cobau.minij;

import ch.hslu.cobau.minij.ast.AstBuilder;
import ch.hslu.cobau.minij.ast.constants.IntegerConstant;
import ch.hslu.cobau.minij.ast.entity.Declaration;
import ch.hslu.cobau.minij.ast.entity.Function;
import ch.hslu.cobau.minij.ast.entity.Struct;
import ch.hslu.cobau.minij.ast.entity.Unit;
import ch.hslu.cobau.minij.ast.expression.*;
import ch.hslu.cobau.minij.ast.statement.*;
import ch.hslu.cobau.minij.ast.type.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.ConsoleErrorListener;


import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class MiniJCompiler {

    static List<Function> getAllFuncts(Unit unit) {
        return Stream.concat(unit.getFunctions().stream(), BUILT_IN_FUNCTIONS.stream()).toList();
    }

    private static final List<Function> BUILT_IN_FUNCTIONS = List.of(
            new Function(
                    "writeInt",
                    new VoidType(),
                    List.of(new Declaration("i", new IntegerType(), false)),
                    List.of()
            ),
            new Function(
                    "writeChar",
                    new VoidType(),
                    List.of(new Declaration("c", new IntegerType(), false)),
                    List.of()
            ),
            new Function(
                    "readInt",
                    new IntegerType(),
                    List.of(),
                    List.of(new ReturnStatement(new IntegerConstant(0)))
            ),
            new Function(
                    "readChar",
                    new IntegerType(),
                    List.of(),
                    List.of(new ReturnStatement(new IntegerConstant(0)))
            )
    );

    private static class EnhancedConsoleErrorListener extends ConsoleErrorListener {
        private boolean hasErrors;

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
            hasErrors = true;
        }

        public void semanticError(String msg) {
            System.err.println("Semantic error: " + msg);
            hasErrors = true;
        }

        public boolean hasErrors() {
            return hasErrors;
        }
    }

    public static void main(String[] args) throws IOException {
        // initialize lexer and parser
        CharStream charStream;
        if (args.length > 0) {
            charStream = CharStreams.fromFileName(args[0]);
        } else {
            charStream = CharStreams.fromStream(System.in);
        }

        MiniJLexer miniJLexer = new MiniJLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(miniJLexer);
        MiniJParser miniJParser = new MiniJParser(commonTokenStream);

        EnhancedConsoleErrorListener errorListener = new EnhancedConsoleErrorListener();
        miniJParser.removeErrorListeners();
        miniJParser.addErrorListener(errorListener);

        // start parsing at outermost level (milestone 2)
        MiniJParser.UnitContext unitContext = miniJParser.unit();

        // semantic check (milestone 3)
        final var astBuilder = new AstBuilder();
        astBuilder.visitUnit(unitContext);
        final var unit = astBuilder.getUnit();

        // Check duplicate global identifiers
        final var globalIdentifiers = unit.getGlobals().stream().map(Declaration::getIdentifier).toList();
        if (SemanticChecker.hasDuplicates(globalIdentifiers)) {
            errorListener.semanticError("Duplikate bei den Globalen identifiers");
        }

        // Check duplicate struct identifiers
        final var structIdentifiers = unit.getStructs().stream().map(Struct::getIdentifier).toList();
        if (SemanticChecker.hasDuplicates(structIdentifiers)) {
            errorListener.semanticError("Duplikate in den struct identifiers");
        }

        // Check duplicate identifiers inside of struct
        final var structDeclarationIdentifiers = unit.getStructs().stream().map(s -> s.getDeclarations().stream().map(Declaration::getIdentifier).toList()).toList();
        structDeclarationIdentifiers.forEach(i -> {
            if (SemanticChecker.hasDuplicates(i)) {
                errorListener.semanticError("Duplizierte identifiers in einem Struct");
            }
        });

        // Check duplicate function identifiers
        final var functionIdentifiers = getAllFuncts(unit).stream().map(Function::getIdentifier).toList();
        if (SemanticChecker.hasDuplicates(functionIdentifiers)) {
            errorListener.semanticError("Duplizierte Funktions-Identifiers!");
        }

        // Check duplicate variable identifiers in context
        for (var function : getAllFuncts(unit)) {
            final List<String> functionDeclarations = SemanticChecker.getAllDeclarationsForFunction(function).stream()
                    .map(Declaration::getIdentifier)
                    .toList();

            if (SemanticChecker.hasDuplicates(functionDeclarations)) {
                errorListener.semanticError("Duplizierte identifiers!");
            }
        }

        // Check for void declarations on all levels
        SemanticChecker.getAllDeclarations(unit).stream()
                .filter(SemanticChecker::isVoidDeclaration)
                .findAny()
                .ifPresent(declaration -> errorListener.semanticError(String.format("Inkorrekte void declaration... Identifier: '%s' Type: '%s'", declaration.getIdentifier(), declaration.getType())));

        // Check for existence identifiers for variable assignment
        for (Function function : getAllFuncts(unit)) {
            final List<String> variableAccessIdentifiers = function.getStatements().stream()
                    .filter(AssignmentStatement.class::isInstance)
                    .map(AssignmentStatement.class::cast)
                    .map(AssignmentStatement::getLeft)
                    .filter(VariableAccess.class::isInstance)
                    .map(VariableAccess.class::cast)
                    .map(VariableAccess::getIdentifier)
                    .toList();

            final List<String> functionDeclarations = SemanticChecker.getAllDeclarationsForFunction(function).stream()
                    .map(Declaration::getIdentifier)
                    .toList();

            variableAccessIdentifiers.stream()
                    .filter(not(globalIdentifiers::contains))
                    .filter(not(functionDeclarations::contains))
                    .findAny()
                    .ifPresent(identifier -> errorListener.semanticError(String.format("Variable Zugriff ohne declaration Gefunden! Identifier: '%s'", identifier)));
        }

        // Check for nonexistent struct types on variable declaration
        final List<String> structTypeIdentifiers = SemanticChecker.getAllDeclarations(unit).stream()
                .map(Declaration::getType)
                .filter(RecordType.class::isInstance)
                .map(RecordType.class::cast)
                .map(RecordType::getIdentifier)
                .toList();
        structTypeIdentifiers.stream()
                .filter(not(structIdentifiers::contains))
                .findAny()
                .ifPresent(identifier -> errorListener.semanticError(String.format("Variable Deklaration ohne existierenden Typ erstellt. Type: '%s'", identifier)));

        for (Function function : getAllFuncts(unit)) {
            final List<AssignmentStatement> assignments = SemanticChecker.getAllAssignmentStatementsForFunction(function);
            var leftSides = assignments.stream()
                    .map(AssignmentStatement::getLeft)
                    .toList();
            var rightSides = assignments.stream()
                    .map(AssignmentStatement::getRight)
                    .toList();

            var allAssignmentExpressions = Stream.concat(leftSides.stream(), rightSides.stream()).toList();
            final List<FieldAccess> fieldAccesses = allAssignmentExpressions.stream()
                    .filter(FieldAccess.class::isInstance)
                    .map(FieldAccess.class::cast)
                    .toList();
            for (FieldAccess fieldAccess : fieldAccesses) {
                final String accessedField = fieldAccess.getField();
                final Expression baseExpression =  fieldAccess.getBase();

                if (baseExpression instanceof VariableAccess baseType) {
                    final String identifier = baseType.getIdentifier();
                    final var struct = SemanticChecker.getAllDeclarationsForFunction(function).stream()
                            .filter(a -> a.getIdentifier().equals(identifier))
                            .map(Declaration::getType)
                            .filter(RecordType.class::isInstance)
                            .map(RecordType.class::cast)
                            .map(RecordType::getIdentifier)
                            .findFirst()
                            .flatMap(s -> SemanticChecker.getStructByIdentifier(unit, s));

                    if (struct.isPresent()) {
                        final var identifiers = struct.get().getDeclarations().stream()
                                .map(Declaration::getIdentifier)
                                .toList();
                        if (!identifiers.contains(accessedField)) {
                            errorListener.semanticError(String.format("Nicht existierendes Feld '%s' hat auf struct '%s' zugegriffen", accessedField, identifier));
                        }
                    } else {
                        errorListener.semanticError(String.format("Feld '%s' wurde mittels nichtexistierendem Struct Type '%s' aufgerufen.", accessedField, identifier));
                    }

                }
            }
        }

        // FUNCTION: Check that function does exist
        for (Function function : getAllFuncts(unit)) {
            final var allCallStatements = SemanticChecker.getAllCallStatementsForFunction(function);
            final var allCallIdentifiers = allCallStatements.stream().map(CallStatement::getCallExpression)
                    .map(CallExpression::getIdentifier)
                    .toList();
            allCallIdentifiers.stream()
                    .filter(not(functionIdentifiers::contains))
                    .findAny()
                    .ifPresent(identifier -> errorListener.semanticError(String.format("Es wurde einen Nonexistence Funktion  function: '%s' aufgerufen ", identifier)));
        }

        // This checks if the left hand site type and the right hand site type match
        for (Function function : getAllFuncts(unit)) {
            final var assignments = SemanticChecker.getAllAssignmentStatementsForFunction(function);

            for (AssignmentStatement assignment : assignments) {
                // Here we can assume that the left side of an assignment is always a MemoryAccess
                final Optional<Type> assignmentTypeLHS = SemanticChecker.getBaseTypeForMemoryAccessExpression(assignment.getLeft(), function, unit);
                final Optional<Type> assignmentTypeRHS = SemanticChecker.getBaseTypeForExpression(assignment.getRight(), function, unit);

                if (assignmentTypeLHS.isPresent() && assignmentTypeRHS.isPresent()) {
                    if (!assignmentTypeLHS.get().equals(assignmentTypeRHS.get())) {
                        errorListener.semanticError("Assignment mit falschem type");
                    }
                } else {
                    errorListener.semanticError("Assignment mit falschem type");
                }
            }
        }

        // Function argument amount
        for (Function function : getAllFuncts(unit)) {
            final var allCallExpressions = SemanticChecker.getAllCallStatementsForFunction(function).stream()
                    .map(CallStatement::getCallExpression)
                    .toList();

            for (CallExpression callExpression : allCallExpressions) {
                final Optional<Function> functionDefinition = SemanticChecker.getFunctionByIdentifier(unit, callExpression.getIdentifier());
                if (functionDefinition.isPresent()) {
                    final List<Declaration> parameters = functionDefinition.get().getFormalParameters();

                    final var definedParameterTypes = parameters.stream()
                            .map(Declaration::getType)
                            .toList();
                    final var parameterTypes = callExpression.getParameters().stream()
                            .map(e -> SemanticChecker.getBaseTypeForExpression(e, function, unit))
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .toList();

                    if (definedParameterTypes.size() != parameterTypes.size()) {
                        errorListener.semanticError("Function call mit falscher Nummer von Parametern");
                    } else {
                        for (int i = 0; i < definedParameterTypes.size(); i++) {
                            var definedType = definedParameterTypes.get(i);
                            var actualType = parameterTypes.get(i);
                            if (!definedType.equals(actualType)) {
                                errorListener.semanticError(String.format("Function call mit falscher Function '%s', Parameter Type '%s'", callExpression.getIdentifier(), definedParameterTypes.get(i)));
                                break;
                            }
                        }
                    }
                } else {
                    errorListener.semanticError(String.format("Function call mit unbekannter Funktion '%s'", callExpression.getIdentifier()));
                }
            }
        }

        // Function must have correct return type
        for (Function function : getAllFuncts(unit)) {
            final var expectedReturnType = function.getType();
            if (expectedReturnType instanceof VoidType) {
                final var returnStatements = function.getStatements().stream()
                        .filter(ReturnStatement.class::isInstance)
                        .map(ReturnStatement.class::cast)
                        .toList();
                final Optional<Expression> returnExpression = returnStatements.stream()
                        .map(ReturnStatement::getExpression)
                        .filter(Objects::nonNull)
                        .findAny();

                if (returnExpression.isPresent()) {
                    errorListener.semanticError(String.format("Function '%s' hat den falschem type im return statement", function.getIdentifier()));
                }
            } else {
                final var returnStatements = function.getStatements().stream()
                        .filter(ReturnStatement.class::isInstance)
                        .map(ReturnStatement.class::cast)
                        .toList();

                final var wrongReturnTypes = returnStatements.stream()
                        .map(ReturnStatement::getExpression)
                        .filter(Objects::nonNull)
                        .map(e -> SemanticChecker.getBaseTypeForExpression(e, function, unit))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .filter(a -> !a.equals(expectedReturnType))
                        .findAny();

                final var voidReturnStatements = returnStatements.stream()
                        .filter(s -> s.getExpression() == null)
                        .findAny();

                if (wrongReturnTypes.isPresent() || voidReturnStatements.isPresent()) {
                    errorListener.semanticError(String.format("Function '%s' falschen Typ in return statement", function.getIdentifier()));
                }
            }
        }

        // MAIN-FUNCTION: must have no parameters
        final Optional<Function> main = getAllFuncts(unit).stream()
                .filter(f -> f.getIdentifier().equals("main"))
                .findFirst();
        if (main.isPresent() && !main.get().getFormalParameters().isEmpty()) {
            errorListener.semanticError("Main function kann keine Parameter haben!");
        }

        // Check if statements for correct expression types
        for (Function function : getAllFuncts(unit)) {
            final var ifStatements = SemanticChecker.getAllIfStatementsForFunction(function);
            final Optional<Type> ifStatementsWithoutBooleanExpression = ifStatements.stream()
                    .map(IfStatement::getExpression)
                    .map(e -> SemanticChecker.getBaseTypeForExpression(e, function, unit))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(not(BooleanType.class::isInstance))
                    .findAny();
            if (ifStatementsWithoutBooleanExpression.isPresent()) {
                errorListener.semanticError("If Statements brauchen eine Boolean Expression");
            }
        }

        // Check while statements for correct expression types
        for (Function function : getAllFuncts(unit)) {
            final var whileStatements = SemanticChecker.getAllWhileStatementsForFunction(function);
            final Optional<Type> whileStatementsWithoutBooleanExpression = whileStatements.stream()
                    .map(WhileStatement::getExpression)
                    .map(e -> SemanticChecker.getBaseTypeForExpression(e, function, unit))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(not(BooleanType.class::isInstance))
                    .findAny();
            if (whileStatementsWithoutBooleanExpression.isPresent()) {
                errorListener.semanticError("While statements brauchen eine boolean expression");
            }
        }

        // code generation (milestone 4)
        ProgramGenerator programGenerator = new ProgramGenerator();
        unit.accept(programGenerator);

        System.out.println(programGenerator.getAssemblyCode());

        System.exit(errorListener.hasErrors() ? 1 : 0);
    }

}
