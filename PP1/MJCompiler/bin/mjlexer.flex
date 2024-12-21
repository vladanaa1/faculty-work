package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

// Whitespace characters (Beline)

" " 	{ /* ignore */ }
"\b" 	{ /* ignore */ }
"\t" 	{ /* ignore */ }
"\r\n" 	{ /* ignore */ }
"\f" 	{ /* ignore */ }

// Keywords (Ključne reci)
"program"              { return new_symbol(sym.PROGRAM); }
"break"                { return new_symbol(sym.BREAK); }
"class"                { return new_symbol(sym.CLASS); }
"else"                 { return new_symbol(sym.ELSE); }
"if"                   { return new_symbol(sym.IF); }
"new"                  { return new_symbol(sym.NEW); }
"print"                { return new_symbol(sym.PRINT); }
"read"                 { return new_symbol(sym.READ); }
"return"               { return new_symbol(sym.RETURN); }
"void"                 { return new_symbol(sym.VOID); }
"extends"			   { return new_symbol(sym.EXTENDS); }
"continue"			   { return new_symbol(sym.CONTINUE); }
"set"			  	   { return new_symbol(sym.SET); }
"union"			       { return new_symbol(sym.UNION); }
"do"                   { return new_symbol(sym.DO); }
"while"                { return new_symbol(sym.WHILE); }
"map"                  { return new_symbol(sym.MAP); }
"interface"            { return new_symbol(sym.INTERFACE); }
"const"				   { return new_symbol(sym.CONST); }

// Operators (Operatori)
// 1. Arithmetic (Aritmetički)

<YYINITIAL> "+"       { return new_symbol(sym.PLUS, yytext()); }
<YYINITIAL> "-"       { return new_symbol(sym.MINUS, yytext()); }
<YYINITIAL> "*"       { return new_symbol(sym.MULT, yytext()); }
<YYINITIAL> "/"       { return new_symbol(sym.DIV, yytext()); }
<YYINITIAL> "%"       { return new_symbol(sym.MOD, yytext()); }

// 2. Relational (Relacioni)

<YYINITIAL> "=="      { return new_symbol(sym.EQUALS, yytext()); }
<YYINITIAL> "!="      { return new_symbol(sym.NOTEQUAL, yytext()); }
<YYINITIAL> ">"       { return new_symbol(sym.GREATER, yytext()); }
<YYINITIAL> ">="      { return new_symbol(sym.GREATEREQ, yytext()); }
<YYINITIAL> "<"       { return new_symbol(sym.LESS, yytext()); }
<YYINITIAL> "<="      { return new_symbol(sym.LESSEQ, yytext()); }

// 3. Logical (Logički)

<YYINITIAL> "&&"      { return new_symbol(sym.AND, yytext()); }
<YYINITIAL> "||"      { return new_symbol(sym.OR, yytext()); }

// 4. Assignment (Dodela vrednosti)

<YYINITIAL> "="       { return new_symbol(sym.ASSIGN, yytext()); }
<YYINITIAL> "++"      { return new_symbol(sym.INC, yytext()); }
<YYINITIAL> "--"      { return new_symbol(sym.DEC, yytext()); }

// 5. Separators (Separatori)

<YYINITIAL> ";"       { return new_symbol(sym.SEMI, yytext()); }
<YYINITIAL> ":"       { return new_symbol(sym.COLON, yytext()); }
<YYINITIAL> ","       { return new_symbol(sym.COMMA, yytext()); }
<YYINITIAL> "."       { return new_symbol(sym.DOT, yytext()); }

// 6. Delimiters (Delimiteri)

<YYINITIAL> "("       { return new_symbol(sym.LPAREN, yytext()); }
<YYINITIAL> ")"       { return new_symbol(sym.RPAREN, yytext()); }
<YYINITIAL> "["       { return new_symbol(sym.LBRACKET, yytext()); }
<YYINITIAL> "]"       { return new_symbol(sym.RBRACKET, yytext()); }
<YYINITIAL> "{"       { return new_symbol(sym.LBRACE, yytext()); }
<YYINITIAL> "}"       { return new_symbol(sym.RBRACE, yytext()); }

// 7. Comments (Komentari)

<YYINITIAL> "//" { yybegin(COMMENT); }
<COMMENT> .     { /* Consume comment line */ }
<COMMENT> "\r\n"|"\r"|"\n" { yybegin(YYINITIAL); }

// Literals (Literali)

[0-9]+						{ return new_symbol(sym.NUMBER, new Integer (yytext())); }
"true"|"false"				{ return new_symbol(sym.BOOL, Boolean.valueOf(yytext())); }
"'"[ -~]"'"					{ return new_symbol(sym.CHAR, new Character(yytext().charAt(1))); }

// Identifiers (Identifikatori)

([a-z]|[A-Z])[a-zA-Z0-9_]* 	{return new_symbol (sym.IDENT, yytext());}

// Lexical error (Leksička greška)

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); }






