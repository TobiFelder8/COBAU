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
	 * Visit a parse tree produced by {@link MiniJParser#recordDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordDeclaration(MiniJParser.RecordDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(MiniJParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#returnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnType(MiniJParser.ReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(MiniJParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(MiniJParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclaration(MiniJParser.VarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#nonVoidType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonVoidType(MiniJParser.NonVoidTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#functionBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionBlock(MiniJParser.FunctionBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#blockNV}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockNV(MiniJParser.BlockNVContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#statementNV}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementNV(MiniJParser.StatementNVContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#location}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocation(MiniJParser.LocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#assignStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStatement(MiniJParser.AssignStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#callStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStatement(MiniJParser.CallStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(MiniJParser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(MiniJParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#orExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(MiniJParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#andExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(MiniJParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#equality}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquality(MiniJParser.EqualityContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#relational}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational(MiniJParser.RelationalContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#additive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditive(MiniJParser.AdditiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#multiplicative}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicative(MiniJParser.MultiplicativeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#prefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefix(MiniJParser.PrefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#postfix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix(MiniJParser.PostfixContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(MiniJParser.PrimaryContext ctx);
}