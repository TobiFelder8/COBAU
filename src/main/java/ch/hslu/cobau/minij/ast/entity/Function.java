/**
 * Copyright (c) 2020-2025 HSLU Informatik. All rights reserved.
 * This code and any derivative work thereof must remain private.
 * Public distribution is prohibited.
 */
package ch.hslu.cobau.minij.ast.entity;

import ch.hslu.cobau.minij.ast.AstVisitor;
import ch.hslu.cobau.minij.ast.statement.Block;
import ch.hslu.cobau.minij.ast.statement.Statement;
import ch.hslu.cobau.minij.ast.type.Type;

import java.util.List;
import java.util.Objects;

public class Function extends Block {
    private final String identifier;
	private final Type returnType;
    private final List<Declaration> formalParameters;

    public Function(String ident, Type returnType, List<Declaration> formalParams, List<Statement> statements) {
        super(statements);
        Objects.requireNonNull(ident);
        Objects.requireNonNull(returnType);
        Objects.requireNonNull(formalParams);

        this.identifier = ident;
        this.returnType = returnType;
        this.formalParameters = formalParams;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Type getType() {
        return returnType;
    }

    public List<Declaration> getFormalParameters() {
        return formalParameters;
    }

    public void accept(AstVisitor astVisitor) {
        astVisitor.visit(this);
    }

    @Override
    public void visitChildren(AstVisitor astVisitor) {
        formalParameters.forEach(parameter -> parameter.accept(astVisitor));
        super.visitChildren(astVisitor); // statements
    }
}
