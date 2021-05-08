/*
Copyright (c) 2019, Robert Futrell
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the author nor the names of its contributors may
      be used to endorse or promote products derived from this software
      without specific prior written permission.
 */

package org.parker.mips.gui.userpanes.editor.rsyntax;

import org.fife.ui.rsyntaxtextarea.Token;
import javax.swing.text.Segment;
import java.io.*;
import org.fife.ui.rsyntaxtextarea.*;


/**
 * This class takes plain text and returns tokens representing MIPS
 * assembler.<p>
 *
 * This implementation was created using
 * <a href="http://www.jflex.de/">JFlex</a> 1.6.1; however, the generated file
 * was modified for performance.  Memory allocation needs to be almost
 * completely removed to be competitive with the handwritten lexers (subclasses
 * of <code>AbstractTokenMaker</code>, so this class has been modified so that
 * Strings are never allocated (via yytext()), and the scanner never has to
 * worry about refilling its buffer (needlessly copying chars around).
 * We can achieve this because RText always scans exactly 1 line of tokens at a
 * time, and hands the scanner this line as an array of characters (a Segment
 * really).  Since tokens contain pointers to char arrays instead of Strings
 * holding their contents, there is no need for allocating new memory for
 * Strings.<p>
 *
 * The actual algorithm generated for scanning has, of course, not been
 * modified.<p>
 *
 * If you wish to regenerate this file yourself, keep in mind the following:
 * <ul>
 *   <li>The generated <code>MIPSTokenMaker.java</code> file will contain two
 *       definitions of both <code>zzRefill</code> and <code>yyreset</code>.
 *       You should hand-delete the second of each definition (the ones
 *       generated by the lexer), as these generated methods modify the input
 *       buffer, which we'll never have to do.</li>
 *   <li>You should also change the declaration/definition of zzBuffer to NOT
 *       be initialized.  This is a needless memory allocation for us since we
 *       will be pointing the array somewhere else anyway.</li>
 *   <li>You should NOT call <code>yylex()</code> on the generated scanner
 *       directly; rather, you should use <code>getTokenList</code> as you would
 *       with any other <code>TokenMaker</code> instance.</li>
 * </ul>
 *
 * @author Robert Futrell
 * @version 0.2
 *
 */
%%

%public
%class MIPSTokenMaker
%extends AbstractJFlexTokenMaker
%unicode
%ignorecase
%type org.fife.ui.rsyntaxtextarea.Token


%{


	/**
	 * Constructor.  We must have this here as JFLex does not generate a
	 * no parameter constructor.
	 */
	public MIPSTokenMaker() {
		super();
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addToken(int, int, int)
	 */
	private void addHyperlinkToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so, true);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int tokenType) {
		addToken(zzStartRead, zzMarkedPos-1, tokenType);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addHyperlinkToken(int, int, int)
	 */
	private void addToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param array The character array.
	 * @param start The starting offset in the array.
	 * @param end The ending offset in the array.
	 * @param tokenType The token's type.
	 * @param startOffset The offset in the document at which this token
	 *                    occurs.
	 */
	@Override
	public void addToken(char[] array, int start, int end, int tokenType, int startOffset) {
		super.addToken(array, start,end, tokenType, startOffset);
		zzStartRead = zzMarkedPos;
	}


	@Override
	public String[] getLineCommentStartAndEnd(int languageIndex) {
		return new String[] { ";", null };
	}


	/**
	 * Returns the first token in the linked list of tokens generated
	 * from <code>text</code>.  This method must be implemented by
	 * subclasses so they can correctly implement syntax highlighting.
	 *
	 * @param text The text from which to get tokens.
	 * @param initialTokenType The token type we should start with.
	 * @param startOffset The offset into the document at which
	 *                    <code>text</code> starts.
	 * @return The first <code>Token</code> in a linked list representing
	 *         the syntax highlighted text.
	 */
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {

		resetTokenList();
		this.offsetShift = -text.offset + startOffset;

		// Start off in the proper state.
		int state = YYINITIAL;
		switch (initialTokenType) {
			default:
				state = YYINITIAL;
		}

		s = text;
		try {
			yyreset(zzReader);
			yybegin(state);
			return yylex();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new TokenImpl();
		}

	}


	/**
	 * Refills the input buffer.
	 *
	 * @return      <code>true</code> if EOF was reached, otherwise
	 *              <code>false</code>.
	 */
	private boolean zzRefill() {
		return zzCurrentPos>=s.offset+s.count;
	}


	/**
	 * Resets the scanner to read from a new input stream.
	 * Does not close the old reader.
	 *
	 * All internal variables are reset, the old input stream
	 * <b>cannot</b> be reused (internal buffer is discarded and lost).
	 * Lexical state is set to <tt>YY_INITIAL</tt>.
	 *
	 * @param reader   the new input stream
	 */
	public final void yyreset(Reader reader) {
		// 's' has been updated.
		zzBuffer = s.array;
		/*
		 * We replaced the line below with the two below it because zzRefill
		 * no longer "refills" the buffer (since the way we do it, it's always
		 * "full" the first time through, since it points to the segment's
		 * array).  So, we assign zzEndRead here.
		 */
		//zzStartRead = zzEndRead = s.offset;
		zzStartRead = s.offset;
		zzEndRead = zzStartRead + s.count - 1;
		zzCurrentPos = zzMarkedPos = zzPushbackPos = s.offset;
		zzLexicalState = YYINITIAL;
		zzReader = reader;
		zzAtBOL  = true;
		zzAtEOF  = false;
	}


%}

Letter				= ([A-Za-z_])
NonzeroDigit		= ([1-9])
BinaryDigit			= ([0-1])
Digit				= ("0"|{NonzeroDigit})
HexDigit			= ({Digit}|[A-Fa-f])
OctalDigit			= ([0-7])

/*Numbers*/
DigitOrUnderscore			= ({Digit}|[_])
DigitsAndUnderscoresEnd		= ({DigitOrUnderscore}*{Digit})
IntegerHelper				= (({NonzeroDigit}{DigitsAndUnderscoresEnd}?)|"0")
IntegerLiteral				= ({IntegerHelper}[lL]?)

BinaryDigitOrUnderscore		= ({BinaryDigit}|[_])
BinaryDigitsAndUnderscores	= ({BinaryDigit}({BinaryDigitOrUnderscore}*{BinaryDigit})?)
BinaryLiteral				= ("0"[bB]{BinaryDigitsAndUnderscores})

HexDigitOrUnderscore		= ({HexDigit}|[_])
HexDigitsAndUnderscores		= ({HexDigit}({HexDigitOrUnderscore}*{HexDigit})?)
OctalDigitOrUnderscore		= ({OctalDigit}|[_])
OctalDigitsAndUnderscoresEnd= ({OctalDigitOrUnderscore}*{OctalDigit})
HexHelper					= ("0"(([xX]{HexDigitsAndUnderscores})|({OctalDigitsAndUnderscoresEnd})))
HexLiteral					= ({HexHelper}[lL]?)
/*Numbers*/


EscapedSourceCharacter				= ("u"{HexDigit}{HexDigit}{HexDigit}{HexDigit})

IdentifierStart                     = ([:jletter:])
IdentifierPart						= ([:jletterdigit:]|("\\"{EscapedSourceCharacter}))
Identifier				= ({IdentifierStart}{IdentifierPart}*)

UnclosedStringLiteral	= ([\"][^\"]*)
StringLiteral			= ({UnclosedStringLiteral}[\"])
UnclosedCharLiteral		= ([\'][^\']*)
CharLiteral			= ({UnclosedCharLiteral}[\'])

CommentBegin			= ([;])

LineTerminator			= (\n)
WhiteSpace			= ([ \t\f])

Label				= (({Letter}|{Digit})+[\:])

Operator				= ("+"|"-"|"~"|"^"|"*"|"/"|"%"|"&"|"<<"|">>"|"|"|"!="|"=="|"<"|">"|"<="|">="|"&&"|"||"|"!"|":")


%state EOL_COMMENT

%%

<YYINITIAL> {

	/* PreProcessor Directives*/
	".DEFINE" |
	".ASG" |
	".UNASG" |
	".UNDEFINE" |
	".MACRO" |
	".ENDM" |
	".INCLUDE" |
	".IF" |
	".ELSE" |
	".ELSEIF" |
	".ENDIF" |
	".LOOP" |
	".BREAK" |
	".ENDLOOP"	{ addToken(Token.PREPROCESSOR); }

	/* Assembler Directives*/
	".ASCIIZ" |
	".ASCII" |
	".LONG" |
	".WORD" |
	".SHORT" |
	".BYTE" |
	".DB" |
	".BCD" |
	".SPACE" |
	".ALIGN" |
	".ORG" |
	".GLOBAL" |
	".DEF" |
	".REF" |
	".EMSG" |
	".WMSG" |
	".MMSG" 	{ addToken(Token.PREPROCESSOR); }

	"BYTE" |
	"WORD" |
	"INTEGER" |
	"SHORT" |
	"LONG" |
	"CHAR"		{ addToken(Token.DATA_TYPE); }

	/* Registers */
	"AT" |
	"V0" |
	"V1" |
	"A0" |
	"A1" |
	"A2" |
	"A3" |
	"T0" |
	"T1" |
	"T2" |
	"T3" |
	"T4" |
	"T5" |
	"T6" |
	"T7" |
	"S0" |
	"S1" |
	"S2" |
	"S3" |
	"S4" |
	"S5" |
	"S6" |
	"S7" |
	"T8" |
	"T9" |
	"KT0" |
	"K1" |
	"KT1" |
	"GP" |
	"SP" |
	"FP" |
	"RA"		{ addToken(Token.VARIABLE); }

	/* Keywords */
	"ADD" |
    "ADDU" |
    "ADDI" |
    "ADDIU" |
    "AND" |
    "ANDI" |
    "DIV" |
    "DIVU" |
    "MULT" |
    "MULTU" |
    "NOR" |
    "OR" |
    "ORI" |
    "SLL" |
    "SLLV" |
    "SRA" |
    "SRAV" |
    "SRL" |
    "SRLV" |
    "SUB" |
    "SUBU" |
    "XOR" |
    "XORI" |
    "LHI" |
    "LLO" |
    "SLT" |
    "SLTU" |
    "SLTI" |
    "SLTIU" |
    "BEQ" |
    "BGTZ" |
    "BLEZ" |
    "BNE" |
    "J" |
    "JAL" |
    "JALR" |
    "JR" |
    "LB" |
    "LBU" |
    "LH" |
    "LHU" |
    "LW" |
    "SB" |
    "SH" |
    "SW" |
    "MFHI" |
    "MFLO" |
    "MTHI" |
    "TRAP"      { addToken(Token.RESERVED_WORD); }


}

<YYINITIAL> {

	{LineTerminator}				{ addNullToken(); return firstToken; }

	{WhiteSpace}+					{ addToken(Token.WHITESPACE); }

	/* String/Character Literals. */
	{CharLiteral}					{ addToken(Token.LITERAL_CHAR); }
	{UnclosedCharLiteral}			{ addToken(Token.ERROR_CHAR); /*addNullToken(); return firstToken;*/ }
	{StringLiteral}				{ addToken(Token.LITERAL_STRING_DOUBLE_QUOTE); }
	{UnclosedStringLiteral}			{ addToken(Token.ERROR_STRING_DOUBLE); addNullToken(); return firstToken; }

	/* Labels. */
	{Label}						{ addToken(Token.PREPROCESSOR); }

	/* ^%({Letter}|{Digit})*			{ addToken(Token.FUNCTION); } */

	/* Comment Literals. */
	{CommentBegin}.*				{ addToken(Token.COMMENT_EOL); addNullToken(); return firstToken; }

	/* Operators. */
	{Operator}					{ addToken(Token.OPERATOR); }

	/* Numbers */
	{IntegerLiteral}				{ addToken(Token.LITERAL_NUMBER_DECIMAL_INT); }
	{BinaryLiteral}					{ addToken(Token.LITERAL_NUMBER_DECIMAL_INT); }
	{HexLiteral}					{ addToken(Token.LITERAL_NUMBER_HEXADECIMAL); }

	/* Ended with a line not in a string or comment. */
	<<EOF>>						{ addNullToken(); return firstToken; }

	/* Catch any other (unhandled) characters. */
	{Identifier}					{ addToken(Token.VARIABLE); }
	.							{ addToken(Token.IDENTIFIER); }

}