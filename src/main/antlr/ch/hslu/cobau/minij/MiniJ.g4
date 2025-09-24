grammar MiniJ;

@header { package ch.hslu.cobau.minij; }

unit : (recordDeclaration | functionDeclaration | varDeclaration)* EOF ;

recordDeclaration : 'record' ID '{' varDeclaration* '}' ;

functionDeclaration : returnType ID '(' paramList? ')' functionBlock ';'? ;
returnType          : 'void' | nonVoidType ;
paramList           : param (',' param)* ;
param               : ('ref')? nonVoidType ID ;

varDeclaration : nonVoidType ID ('=' expr)? ';' ;

nonVoidType : 'int'
            | 'boolean'
            | 'string'
            | ID
            | nonVoidType '[' ']'
            ;

functionBlock : '{' varDeclaration* statementNV* '}' ;
blockNV       : '{' statementNV* '}' ;

statementNV : blockNV
            | 'if' '(' expr ')' statementNV ('else' statementNV)?
            | 'while' '(' expr ')' statementNV
            | 'return' expr? ';'
            | assignStatement
            | callStatement
            | ';'
            ;

location : ID ('.' ID | '[' expr ']')* ;

assignStatement : location '=' expr ';' ;

callStatement : ID '(' argList? ')' ';' ;
argList       : expr (',' expr)* ;

expr            : orExpr ;
orExpr          : andExpr ( '||' andExpr )* ;
andExpr         : equality ( '&&' equality )* ;
equality        : relational ( ('==' | '!=') relational )* ;
relational      : additive ( ('<' | '>' | '<=' | '>=') additive )? ;
additive        : multiplicative ( ('+' | '-') multiplicative )* ;
multiplicative  : prefix ( ('*' | '/' | '%') prefix )* ;
prefix          : ('++' | '--' | '+' | '-' | '!') prefix | postfix ;
postfix         : primary ( '++' | '--' )? ;

primary         : INT
                | BOOL
                | STRING
                | ID '(' argList? ')'
                | location
                | '(' expr ')'
                ;

BOOL         : 'true' | 'false' ;
INT          : [0-9]+ ;
STRING       : '"' (~["\\] | '\\' . | '$')* '"' ;
ID           : [a-zA-Z_$][a-zA-Z0-9_$]* ;
WS           : [ \t\r\n]+ -> skip ;
LINE_COMMENT : '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT: '/*' .*? '*/' -> skip ;
