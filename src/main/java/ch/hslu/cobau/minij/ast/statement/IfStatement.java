/**
 * Copyright (c) 2020-2025 HSLU Informatik. All rights reserved.
 * This code and any derivative work thereof must remain private.
 * Public distribution is prohibited.
 */
package ch.hslu.cobau.minij.ast.statement;

import ch.hslu.cobau.minij.ast.AstElement;
import ch.hslu.cobau.minij.ast.AstVisitor;
import ch.hslu.cobau.minij.ast.expression.Expression;

import java.util.List;
import java.util.Objects;

public class IfStatement extends Block {
    private final Expression expression;
    private final Block elseBlock;
    private final Block ifBody;

    public IfStatement(Expression expression, List<Statement> statements, Block elseBlock) {
        super(statements);
        Objects.requireNonNull(expression);

        this.expression = expression;
        this.ifBody = new Block(statements);
        this.elseBlock = elseBlock;
    }

    public Expression getExpression() {
        return expression;
    }

    public Block getElseBlock() {
        return elseBlock;
    }
    public Block getIfBody() { return ifBody; }

    @Override
    public void accept(AstVisitor astVisitor) {
        astVisitor.visit(this);
    }

    @Override
    public void visitChildren(AstVisitor astVisitor) {
        expression.accept(astVisitor);
        super.visitChildren(astVisitor); // statements in "if"-branch
        if (elseBlock != null) {
            elseBlock.accept(astVisitor);    // statements in "else"-branch
        }
    }
}
