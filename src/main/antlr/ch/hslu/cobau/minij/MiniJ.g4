grammar MiniJ;

@header {
package ch.hslu.cobau.minij;
}

unit : (functionDeclaration | varDeclaration)* EOF ;

functionDeclaration : type ID '(' paramList? ')' block ;
paramList           : param (',' param)* ;
param               : ('ref')? type ID ;

varDeclaration : type ID ('=' expr)? ';' ;

type : 'int'
     | 'boolean'
     | 'string'
     | 'void'
     | ID
     | type '[' ']'
     ;

block     : '{' statement* '}' ;
statement : block
          | varDeclaration
          | 'if' '(' expr ')' statement ('else' statement)?
          | 'while' '(' expr ')' statement
          | 'return' expr? ';'
          | assignStatement
          | expr ';'
          | ';'
          ;

assignStatement : (ID ('[' expr ']')?) '=' expr ';' ;

expr            : orExpr ;
orExpr          : andExpr ( '||' andExpr )* ;
andExpr         : equality ( '&&' equality )* ;
equality        : relational ( ('==' | '!=') relational )* ;
relational      : additive ( ('<' | '>' | '<=' | '>=') additive )? ;
additive        : multiplicative ( ('+' | '-') multiplicative )* ;
multiplicative  : prefix ( ('*' | '/' | '%') prefix )* ;
prefix          : ('++' | '--' | '+' | '-' | '!') prefix | postfix ;
postfix         : primary ( '++' | '--' )? ;
primary         : INT | BOOL | STRING | ID | '(' expr ')' ;

BOOL         : 'true' | 'false' ;
INT          : [0-9]+ ;
STRING       : '"' (~["\\] | '\\' .)* '"' ;
ID           : [a-zA-Z_][a-zA-Z_0-9]* ;
WS           : [ \t\r\n]+ -> skip ;
LINE_COMMENT : '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT: '/*' .*? '*/' -> skip ;
