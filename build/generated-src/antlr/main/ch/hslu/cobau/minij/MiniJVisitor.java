// Generated from ch/hslu/cobau/minij/MiniJ.g4 by ANTLR 4.8

package ch.hslu.cobau.minij;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MiniJParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MiniJVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MiniJParser#unit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnit(MiniJParser.UnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#member}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMember(MiniJParser.MemberContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#struct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct(MiniJParser.StructContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(MiniJParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(MiniJParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#declarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarations(MiniJParser.DeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#functionBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionBody(MiniJParser.FunctionBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MiniJParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#declarationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationStatement(MiniJParser.DeclarationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MiniJParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(MiniJParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#callStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStatement(MiniJParser.CallStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(MiniJParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(MiniJParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#elseClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseClause(MiniJParser.ElseClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(MiniJParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MiniJParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(MiniJParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(MiniJParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#trueConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueConstant(MiniJParser.TrueConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#falseConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseConstant(MiniJParser.FalseConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#integerConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerConstant(MiniJParser.IntegerConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#stringConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringConstant(MiniJParser.StringConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#memoryAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemoryAccess(MiniJParser.MemoryAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(MiniJParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MiniJParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#basicType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicType(MiniJParser.BasicTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#integerType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerType(MiniJParser.IntegerTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#stringType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringType(MiniJParser.StringTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#booleanType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanType(MiniJParser.BooleanTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#structType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructType(MiniJParser.StructTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(MiniJParser.IdentifierContext ctx);
}