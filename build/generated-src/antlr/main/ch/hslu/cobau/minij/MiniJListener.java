// Generated from ch/hslu/cobau/minij/MiniJ.g4 by ANTLR 4.8
 package ch.hslu.cobau.minij; 
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MiniJParser}.
 */
public interface MiniJListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MiniJParser#unit}.
	 * @param ctx the parse tree
	 */
	void enterUnit(MiniJParser.UnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#unit}.
	 * @param ctx the parse tree
	 */
	void exitUnit(MiniJParser.UnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#recordDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterRecordDeclaration(MiniJParser.RecordDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#recordDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitRecordDeclaration(MiniJParser.RecordDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(MiniJParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(MiniJParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#returnType}.
	 * @param ctx the parse tree
	 */
	void enterReturnType(MiniJParser.ReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#returnType}.
	 * @param ctx the parse tree
	 */
	void exitReturnType(MiniJParser.ReturnTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(MiniJParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(MiniJParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(MiniJParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(MiniJParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(MiniJParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(MiniJParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#nonVoidType}.
	 * @param ctx the parse tree
	 */
	void enterNonVoidType(MiniJParser.NonVoidTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#nonVoidType}.
	 * @param ctx the parse tree
	 */
	void exitNonVoidType(MiniJParser.NonVoidTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#functionBlock}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBlock(MiniJParser.FunctionBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#functionBlock}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBlock(MiniJParser.FunctionBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#blockNV}.
	 * @param ctx the parse tree
	 */
	void enterBlockNV(MiniJParser.BlockNVContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#blockNV}.
	 * @param ctx the parse tree
	 */
	void exitBlockNV(MiniJParser.BlockNVContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#statementNV}.
	 * @param ctx the parse tree
	 */
	void enterStatementNV(MiniJParser.StatementNVContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#statementNV}.
	 * @param ctx the parse tree
	 */
	void exitStatementNV(MiniJParser.StatementNVContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#location}.
	 * @param ctx the parse tree
	 */
	void enterLocation(MiniJParser.LocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#location}.
	 * @param ctx the parse tree
	 */
	void exitLocation(MiniJParser.LocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#assignStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignStatement(MiniJParser.AssignStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#assignStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignStatement(MiniJParser.AssignStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#callStatement}.
	 * @param ctx the parse tree
	 */
	void enterCallStatement(MiniJParser.CallStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#callStatement}.
	 * @param ctx the parse tree
	 */
	void exitCallStatement(MiniJParser.CallStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(MiniJParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(MiniJParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MiniJParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MiniJParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(MiniJParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(MiniJParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(MiniJParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(MiniJParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#equality}.
	 * @param ctx the parse tree
	 */
	void enterEquality(MiniJParser.EqualityContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#equality}.
	 * @param ctx the parse tree
	 */
	void exitEquality(MiniJParser.EqualityContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#relational}.
	 * @param ctx the parse tree
	 */
	void enterRelational(MiniJParser.RelationalContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#relational}.
	 * @param ctx the parse tree
	 */
	void exitRelational(MiniJParser.RelationalContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#additive}.
	 * @param ctx the parse tree
	 */
	void enterAdditive(MiniJParser.AdditiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#additive}.
	 * @param ctx the parse tree
	 */
	void exitAdditive(MiniJParser.AdditiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#multiplicative}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicative(MiniJParser.MultiplicativeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#multiplicative}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicative(MiniJParser.MultiplicativeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#prefix}.
	 * @param ctx the parse tree
	 */
	void enterPrefix(MiniJParser.PrefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#prefix}.
	 * @param ctx the parse tree
	 */
	void exitPrefix(MiniJParser.PrefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#postfix}.
	 * @param ctx the parse tree
	 */
	void enterPostfix(MiniJParser.PostfixContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#postfix}.
	 * @param ctx the parse tree
	 */
	void exitPostfix(MiniJParser.PostfixContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(MiniJParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(MiniJParser.PrimaryContext ctx);
}