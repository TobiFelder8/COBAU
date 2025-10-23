package ch.hslu.cobau.minij;


import ch.hslu.cobau.minij.ast.BaseAstVisitor;
import ch.hslu.cobau.minij.ast.constants.TrueConstant;
import ch.hslu.cobau.minij.ast.constants.FalseConstant;
import ch.hslu.cobau.minij.ast.constants.IntegerConstant;
import ch.hslu.cobau.minij.ast.entity.Declaration;
import ch.hslu.cobau.minij.ast.entity.Unit;
import ch.hslu.cobau.minij.ast.expression.BinaryExpression;
import ch.hslu.cobau.minij.ast.expression.BinaryOperator;
import ch.hslu.cobau.minij.ast.expression.VariableAccess;
import ch.hslu.cobau.minij.ast.statement.AssignmentStatement;
import ch.hslu.cobau.minij.ast.statement.CallStatement;
import ch.hslu.cobau.minij.ast.statement.IfStatement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ProgramGenerator extends BaseAstVisitor {

    private final List<String> PARAMETER_REGISTERS_SORTED = List.of("rdi", "rsi", "rdx", "rcx", "r8", "r9");

    private String assemblyCode = "";

    private final Stack<String> statementsStack = new Stack<>();

    private final Map<String, Integer> localsMap = new HashMap<>();

    private int localVarCounter = 0; // Track stack offset for local variables
    private int labelCounter = 0; // Generate unique labels for jumps

    private void addLocal(String identifier) {
        if (!localsMap.containsKey(identifier)) {
            localVarCounter++;
            localsMap.put(identifier, localVarCounter * 8); // Allocate 8 bytes per variable
        }
    }

    private String getNextLabel(String base) {
        return base + "_" + (labelCounter++);
    }

    @Override
    public void visit(CallStatement callStatement) {
        callStatement.visitChildren(this);

        // Visit each parameter and prepare it for function call
        var parameters = callStatement.getCallExpression().getParameters();
        for (int i = 0; i < parameters.size(); i++) {
            parameters.get(i).accept(this);
            statementsStack.push("   mov " + PARAMETER_REGISTERS_SORTED.get(i) + ", rax"); // Move to parameter register
        }

        statementsStack.push("   call " + callStatement.getCallExpression().getIdentifier());
    }

    @Override
    public void visit(Unit program) {
        program.visitChildren(this);

        assemblyCode += """
                DEFAULT REL
                extern writeInt
                extern writeChar
                extern _exit
                extern readInt
                extern readChar

                section .data
                section .text
                global _start
                _start:
                   push rbp
                   mov  rbp, rsp
                   sub  rsp, """ + (localVarCounter * 8) + "\n"; // Reserve space for locals

        StringBuilder statements = new StringBuilder();
        while (!statementsStack.isEmpty()) {
            statements.insert(0, statementsStack.pop() + "\n");
        }
        assemblyCode += statements.toString();

        assemblyCode += """
                   mov rdi, 0
                   call _exit
                .return:
                   mov rsp, rbp
                   pop rbp
                   ret
                """;
    }

    @Override
    public void visit(Declaration declaration) {
        declaration.visitChildren(this);
        addLocal(declaration.getIdentifier());
    }

    @Override
    public void visit(AssignmentStatement assignment) {
        assignment.visitChildren(this);

        if (assignment.getLeft() instanceof VariableAccess variable) {
            final var variableName = variable.getIdentifier();
            int position = localsMap.get(variableName);

            statementsStack.push("   mov [rbp-" + position + "], rax"); // Store value from rax to variable
        }
    }

    @Override
    public void visit(VariableAccess variable) {
        super.visit(variable);
        int position = localsMap.get(variable.getIdentifier());
        statementsStack.push("   mov rax, [rbp-" + position + "]"); // Load variable value to rax
    }


    @Override
    public void visit(IntegerConstant integerConstant) {
        integerConstant.visitChildren(this);

        statementsStack.push("   mov rax, " + integerConstant.getValue()); // Handle constant values
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        binaryExpression.getLeft().accept(this); // Evaluate left-hand side
        statementsStack.push("   push rax"); // Save the result
        binaryExpression.getRight().accept(this); // Evaluate right-hand side
        statementsStack.push("   pop rbx"); // Restore left-hand side

        // Handle equality comparison (==)
        if (BinaryOperator.EQUAL.equals(binaryExpression.getBinaryOperator())) {
            statementsStack.push("   cmp rbx, rax");
            statementsStack.push("   mov rax, 0");
            statementsStack.push("   sete al"); // Set rax to 1 if equal
        }
    }
    @Override
    public void visit(TrueConstant trueConstant) {
        trueConstant.visitChildren(this);

        // True maps to 1
        statementsStack.push("   mov rax, 1");
    }

    @Override
    public void visit(FalseConstant falseConstant) {
        falseConstant.visitChildren(this);

        // False maps to 0
        statementsStack.push("   mov rax, 0");
    }


    @Override
    public void visit(IfStatement ifStatement) {
        String elseLabel = getNextLabel("else");
        String endLabel = getNextLabel("end");

        // Evaluate the condition
        ifStatement.getExpression().accept(this);
        statementsStack.push("   cmp rax, 1");
        statementsStack.push("   jne " + elseLabel);

        // If body
        ifStatement.getIfBody().accept(this);
        statementsStack.push("   jmp " + endLabel);

        // Else body
        statementsStack.push(elseLabel + ":");
        if (ifStatement.getElseBlock() != null) {
            ifStatement.getElseBlock().accept(this);
        }

        // End label
        statementsStack.push(endLabel + ":");
    }

    public String getAssemblyCode() {
        return assemblyCode;
    }
}
