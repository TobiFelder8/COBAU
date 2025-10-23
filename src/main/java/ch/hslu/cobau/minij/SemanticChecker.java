package ch.hslu.cobau.minij;


import ch.hslu.cobau.minij.ast.constants.FalseConstant;
import ch.hslu.cobau.minij.ast.constants.IntegerConstant;
import ch.hslu.cobau.minij.ast.constants.StringConstant;
import ch.hslu.cobau.minij.ast.constants.TrueConstant;
import ch.hslu.cobau.minij.ast.entity.Declaration;
import ch.hslu.cobau.minij.ast.entity.Function;
import ch.hslu.cobau.minij.ast.entity.Struct;
import ch.hslu.cobau.minij.ast.entity.Unit;
import ch.hslu.cobau.minij.ast.expression.*;
import ch.hslu.cobau.minij.ast.statement.*;
import ch.hslu.cobau.minij.ast.type.*;

import java.util.*;
import java.util.stream.Stream;

public class SemanticChecker {

    static <T> boolean hasDuplicates(Collection<T> collection) {
        return collection.stream()
                .anyMatch(s -> Collections.frequency(collection, s) > 1);
    }

    static boolean isVoidDeclaration(Declaration declaration) {
        return declaration.getType() instanceof VoidType;
    }


    /**
     * Returns all declarations
     * Those can be global declarations, inside of structs and inside of functions (parameters and blocks)
     */
    static List<Declaration> getAllDeclarations(Unit unit) {
        final var globalDeclarations = unit.getGlobals();
        final var structDeclarations = unit.getStructs().stream()
                .map(Struct::getDeclarations)
                .flatMap(List::stream)
                .toList();
        final var allFunctionDeclarations = MiniJCompiler.getAllFunctions(unit).stream()
                .map(SemanticChecker::getAllDeclarationsForFunction)
                .flatMap(List::stream)
                .toList();

        List<Declaration> returnedDeclarations = new ArrayList<>();
        returnedDeclarations.addAll(globalDeclarations);
        returnedDeclarations.addAll(structDeclarations);
        returnedDeclarations.addAll(allFunctionDeclarations);
        return returnedDeclarations;
    }

    static List<Declaration> getAllDeclarationsForFunction(Function function) {
        final var functionParameterDeclarations = function.getFormalParameters();

        final List<Declaration> functionTopLevelDeclarations = function.getStatements().stream()
                .filter(DeclarationStatement.class::isInstance)
                .map(DeclarationStatement.class::cast)
                .map(DeclarationStatement::getDeclaration)
                .toList();

        final var functionBlocks = function.getStatements().stream()
                .filter(Block.class::isInstance)
                .map(Block.class::cast)
                .toList();

        List<DeclarationStatement> blockDeclarationStatements = new ArrayList<>();
        functionBlocks.forEach(block -> blockDeclarationStatements.addAll(getStatementsByType(block, DeclarationStatement.class)));

        final List<Declaration> blockDeclarations = blockDeclarationStatements.stream()
                .map(DeclarationStatement::getDeclaration)
                .toList();

        List<Declaration> returnedDeclarations = new ArrayList<>();
        returnedDeclarations.addAll(functionParameterDeclarations);
        returnedDeclarations.addAll(functionTopLevelDeclarations);
        returnedDeclarations.addAll(blockDeclarations);
        return returnedDeclarations;
    }

    private static <T extends Statement> List<T> getStatementsByType(Block block, Class<T> searchedType) {
        List<T> foundStatements = new ArrayList<>();

        final var blocks = block.getStatements().stream()
                .filter(Block.class::isInstance)
                .map(Block.class::cast)
                .toList();

        for (Block subblock : blocks) {
            foundStatements.addAll(getStatementsByType(subblock, searchedType));
        }

        final List<T> declarationsOfCurrentBlock = block.getStatements().stream()
                .filter(searchedType::isInstance)
                .map(searchedType::cast)
                .toList();
        foundStatements.addAll(declarationsOfCurrentBlock);
        return foundStatements;
    }

    static List<AssignmentStatement> getAllAssignmentStatementsForFunction(Function function) {
        final List<AssignmentStatement> functionTopLevelAssignments = function.getStatements().stream()
                .filter(AssignmentStatement.class::isInstance)
                .map(AssignmentStatement.class::cast)
                .toList();

        final var functionBlocks = function.getStatements().stream()
                .filter(Block.class::isInstance)
                .map(Block.class::cast)
                .toList();

        List<AssignmentStatement> blockAssignmentStatements = new ArrayList<>();
        functionBlocks.forEach(block -> blockAssignmentStatements.addAll(getStatementsByType(block, AssignmentStatement.class)));

        List<AssignmentStatement> returnedAssignments = new ArrayList<>();
        returnedAssignments.addAll(functionTopLevelAssignments);
        returnedAssignments.addAll(blockAssignmentStatements);
        return returnedAssignments;
    }

    static List<CallStatement> getAllCallStatementsForFunction(Function function) {
        final List<CallStatement> functionTopLevelCalls = function.getStatements().stream()
                .filter(CallStatement.class::isInstance)
                .map(CallStatement.class::cast)
                .toList();

        final var functionBlocks = function.getStatements().stream()
                .filter(Block.class::isInstance)
                .map(Block.class::cast)
                .toList();

        List<CallStatement> blockCallStatements = new ArrayList<>();
        functionBlocks.forEach(block -> blockCallStatements.addAll(getStatementsByType(block, CallStatement.class)));

        List<CallStatement> returnedAssignments = new ArrayList<>();
        returnedAssignments.addAll(functionTopLevelCalls);
        returnedAssignments.addAll(blockCallStatements);
        return returnedAssignments;
    }

    static List<IfStatement> getAllIfStatementsForFunction(Function function) {
        final List<IfStatement> functionTopLevelAssignments = function.getStatements().stream()
                .filter(IfStatement.class::isInstance)
                .map(IfStatement.class::cast)
                .toList();

        final var functionBlocks = function.getStatements().stream()
                .filter(Block.class::isInstance)
                .map(Block.class::cast)
                .toList();

        List<IfStatement> blockIfStatements = new ArrayList<>();
        functionBlocks.forEach(block -> blockIfStatements.addAll(getStatementsByType(block, IfStatement.class)));

        List<IfStatement> returnedIfs = new ArrayList<>();
        returnedIfs.addAll(functionTopLevelAssignments);
        returnedIfs.addAll(blockIfStatements);
        return returnedIfs;
    }

    static List<WhileStatement> getAllWhileStatementsForFunction(Function function) {
        final List<WhileStatement> functionTopLevelAssignments = function.getStatements().stream()
                .filter(WhileStatement.class::isInstance)
                .map(WhileStatement.class::cast)
                .toList();

        final var functionBlocks = function.getStatements().stream()
                .filter(Block.class::isInstance)
                .map(Block.class::cast)
                .toList();

        List<WhileStatement> blockWhileStatements = new ArrayList<>();
        functionBlocks.forEach(block -> blockWhileStatements.addAll(getStatementsByType(block, WhileStatement.class)));

        List<WhileStatement> returnedWhiles = new ArrayList<>();
        returnedWhiles.addAll(functionTopLevelAssignments);
        returnedWhiles.addAll(blockWhileStatements);
        return returnedWhiles;
    }

    static Optional<Struct> getStructByIdentifier(Unit unit, String identifier) {
        return unit.getStructs().stream()
                .filter(s -> s.getIdentifier().equals(identifier))
                .findFirst();
    }

    static Optional<Function> getFunctionByIdentifier(Unit unit, String identifier) {
        return MiniJCompiler.getAllFunctions(unit).stream()
                .filter(s -> s.getIdentifier().equals(identifier))
                .findFirst();
    }

    private static Optional<Type> getStructFieldTypeByIdentifier(Unit unit, String structIdentifier, String fieldIdentifier) {
        final var struct = SemanticChecker.getStructByIdentifier(unit, structIdentifier);

        final var optionalStructDeclaration = struct.map(Struct::getDeclarations)
                .orElse(new ArrayList<>())
                .stream()
                .filter(s -> s.getIdentifier().equals(fieldIdentifier))
                .findFirst();
        return optionalStructDeclaration.map(Declaration::getType);
    }

    private static List<Declaration> getAllDeclarationsRelevantForContext(Unit unit, Function function) {
        final var functionDeclarations = SemanticChecker.getAllDeclarationsForFunction(function);
        final var globalDeclarations = unit.getGlobals();
        return Stream.concat(functionDeclarations.stream(), globalDeclarations.stream())
                .toList();
    }

    static Optional<Type> getBaseTypeForExpression(Expression expression, Function function, Unit unit) {
        if (expression instanceof MemoryAccess) {
            return SemanticChecker.getBaseTypeForMemoryAccessExpression(expression, function, unit);
        } else if (expression instanceof BinaryExpression binaryExpression) {
            if (doOperatorTypesMatchForBinaryExpression(binaryExpression, function, unit)) {
                return SemanticChecker.getBaseTypeForBinaryExpression(binaryExpression, function, unit);
            }
            return Optional.empty();
        } else if (expression instanceof UnaryExpression unaryExpression) {
            if (doOperatorTypesMatchForUnaryExpression(unaryExpression, function, unit)) {
                return getBaseTypeForUnaryExpression(unaryExpression);
            }
            return Optional.empty();
        } else if (expression instanceof CallExpression callExpression) {
            return MiniJCompiler.getAllFunctions(unit).stream()
                    .filter(f -> f.getIdentifier().equals(callExpression.getIdentifier()))
                    .map(Function::getReturnType)
                    .findFirst();
        } else {
            return getConstantType(expression);
        }
    }

    private static Optional<Type> getConstantType(Expression expression) {
        return switch (expression) {
            case FalseConstant f -> Optional.of(new BooleanType());
            case TrueConstant t -> Optional.of(new BooleanType());
            case StringConstant s -> Optional.of(new StringType());
            case IntegerConstant i -> Optional.of(new IntegerType());
            default -> Optional.empty();
        };
    }

    static Optional<Type> getBaseTypeForMemoryAccessExpression(Expression expression, Function function, Unit unit) {
        if (expression instanceof FieldAccess fieldAccess) {
            final var optionalBaseType = getBaseTypeForMemoryAccessExpression(fieldAccess.getBase(), function, unit);

            if (optionalBaseType.isPresent() && optionalBaseType.get() instanceof RecordType recordType) {
                return getStructFieldTypeByIdentifier(unit, recordType.getIdentifier(), fieldAccess.getField());
            }

            return optionalBaseType;
        }
        if (expression instanceof ArrayAccess arrayAccess) {
            final var optionalBaseType = getBaseTypeForMemoryAccessExpression(arrayAccess.getBase(), function, unit);

            if (optionalBaseType.isPresent() && optionalBaseType.get() instanceof ArrayType arrayType) {
                return Optional.of(arrayType.getType());
            }

            return optionalBaseType;
        }
        if (expression instanceof VariableAccess variableAccess) {
            final var declarations = SemanticChecker.getAllDeclarationsRelevantForContext(unit, function);

            return declarations.stream()
                    .filter(d -> d.getIdentifier().equals(variableAccess.getIdentifier()))
                    .map(Declaration::getType)
                    .findFirst();
        }

        return Optional.empty();
    }

    private static Optional<Type> getBaseTypeForBinaryExpression(BinaryExpression expression, Function function, Unit unit) {
        // Operator type always defines the return value type
        final var boolOperators = List.of(BinaryOperator.EQUAL, BinaryOperator.UNEQUAL, BinaryOperator.LESSER, BinaryOperator.LESSER_EQ, BinaryOperator.GREATER, BinaryOperator.GREATER_EQ, BinaryOperator.AND, BinaryOperator.OR);
        final var integerOperators = List.of(BinaryOperator.MINUS, BinaryOperator.TIMES, BinaryOperator.DIV, BinaryOperator.MOD);

        if (expression.getBinaryOperator().equals(BinaryOperator.PLUS)) {
            final var leftType = getBaseTypeForExpression(expression.getLeft(), function, unit);
            if (leftType.isPresent() && leftType.get() instanceof IntegerType) {
                return Optional.of(new IntegerType());
            } else if (leftType.isPresent() && leftType.get() instanceof StringType) {
                return Optional.of(new StringType());
            } else {
                return Optional.empty();
            }
        }
        if (boolOperators.contains(expression.getBinaryOperator())) {
            return Optional.of(new BooleanType());
        }
        if (integerOperators.contains(expression.getBinaryOperator())) {
            return Optional.of(new IntegerType());
        }

        return Optional.empty();
    }

    private static List<Type> getAllowedTypesForBinaryExpression(BinaryExpression expression) {
        // Operator type always defines the return value type
        final var addAndConcatOperators = List.of(BinaryOperator.PLUS);
        final var boolOperators = List.of(BinaryOperator.AND, BinaryOperator.OR);
        final var integerOperators = List.of(BinaryOperator.MINUS, BinaryOperator.TIMES, BinaryOperator.DIV, BinaryOperator.MOD);
        final var equalityOperators = List.of(BinaryOperator.EQUAL, BinaryOperator.UNEQUAL);
        final var comparisonOperators = List.of(BinaryOperator.LESSER, BinaryOperator.LESSER_EQ, BinaryOperator.GREATER, BinaryOperator.GREATER_EQ);

        if (addAndConcatOperators.contains(expression.getBinaryOperator())) {
            return List.of(new StringType(), new IntegerType());
        }
        if (boolOperators.contains(expression.getBinaryOperator())) {
            return List.of(new BooleanType());
        }
        if (integerOperators.contains(expression.getBinaryOperator())) {
            return List.of(new IntegerType());
        }
        if (equalityOperators.contains(expression.getBinaryOperator())) {
            return List.of(new BooleanType(), new IntegerType(), new StringType());
        }
        if (comparisonOperators.contains(expression.getBinaryOperator())) {
            return List.of(new IntegerType(), new StringType());
        }

        return List.of();
    }

    private static Optional<Type> getBaseTypeForUnaryExpression(UnaryExpression expression) {
        if (expression.getUnaryOperator().equals(UnaryOperator.NOT)) {
            return Optional.of(new BooleanType());
        }
        return Optional.of(new IntegerType());
    }

    private static boolean doOperatorTypesMatchForBinaryExpression(BinaryExpression expression, Function function, Unit unit) {
        // Operator type always defines the return value type
        final List<Type> allowedTypes = getAllowedTypesForBinaryExpression(expression);

        final Optional<Type> leftType = getBaseTypeForExpression(expression.getLeft(), function, unit);
        final Optional<Type> rightType = getBaseTypeForExpression(expression.getRight(), function, unit);

        if (!allowedTypes.isEmpty() && leftType.isPresent() && rightType.isPresent()) {
            return leftType.get().equals(rightType.get()) && allowedTypes.contains(leftType.get());
        }

        return false;
    }

    private static boolean doOperatorTypesMatchForUnaryExpression(UnaryExpression expression, Function function, Unit unit) {
        // Operator type always defines the return value type
        final Optional<Type> expectedType = getBaseTypeForUnaryExpression(expression);
        final Optional<Type> expressionType = getBaseTypeForExpression(expression.getExpression(), function, unit);

        if (expectedType.isPresent() && expressionType.isPresent()) {
            return expectedType.equals(expressionType);
        }

        return false;
    }
}
