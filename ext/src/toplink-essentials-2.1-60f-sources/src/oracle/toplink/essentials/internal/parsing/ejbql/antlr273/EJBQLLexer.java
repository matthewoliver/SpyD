// $ANTLR 2.7.3: "EJBQLParser.g" -> "EJBQLLexer.java"$

    package oracle.toplink.essentials.internal.parsing.ejbql.antlr273;

    import java.util.List;
    import java.util.ArrayList;

    import static oracle.toplink.essentials.internal.parsing.NodeFactory.*;
    import oracle.toplink.essentials.exceptions.EJBQLException;

import java.io.InputStream;
import persistence.antlr.TokenStreamException;
import persistence.antlr.TokenStreamIOException;
import persistence.antlr.TokenStreamRecognitionException;
import persistence.antlr.CharStreamException;
import persistence.antlr.CharStreamIOException;
import persistence.antlr.ANTLRException;
import java.io.Reader;
import java.util.Hashtable;
import persistence.antlr.CharScanner;
import persistence.antlr.InputBuffer;
import persistence.antlr.ByteBuffer;
import persistence.antlr.CharBuffer;
import persistence.antlr.Token;
import persistence.antlr.CommonToken;
import persistence.antlr.RecognitionException;
import persistence.antlr.NoViableAltForCharException;
import persistence.antlr.MismatchedCharException;
import persistence.antlr.TokenStream;
import persistence.antlr.ANTLRHashString;
import persistence.antlr.LexerSharedInputState;
import persistence.antlr.collections.impl.BitSet;
import persistence.antlr.SemanticException;

/** */
public class EJBQLLexer extends persistence.antlr.CharScanner implements EJBQLTokenTypes, TokenStream
 {
public EJBQLLexer(InputStream in) {
	this(new ByteBuffer(in));
}
public EJBQLLexer(Reader in) {
	this(new CharBuffer(in));
}
public EJBQLLexer(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public EJBQLLexer(LexerSharedInputState state) {
	super(state);
	caseSensitiveLiterals = false;
	setCaseSensitive(false);
	literals = new Hashtable();
	literals.put(new ANTLRHashString("between", this), new Integer(11));
	literals.put(new ANTLRHashString("substring", this), new Integer(57));
	literals.put(new ANTLRHashString("delete", this), new Integer(20));
	literals.put(new ANTLRHashString("new", this), new Integer(44));
	literals.put(new ANTLRHashString("object", this), new Integer(47));
	literals.put(new ANTLRHashString("concat", this), new Integer(14));
	literals.put(new ANTLRHashString("abs", this), new Integer(4));
	literals.put(new ANTLRHashString("distinct", this), new Integer(21));
	literals.put(new ANTLRHashString("where", this), new Integer(65));
	literals.put(new ANTLRHashString("trailing", this), new Integer(59));
	literals.put(new ANTLRHashString("locate", this), new Integer(38));
	literals.put(new ANTLRHashString("select", this), new Integer(52));
	literals.put(new ANTLRHashString("and", this), new Integer(6));
	literals.put(new ANTLRHashString("outer", this), new Integer(51));
	literals.put(new ANTLRHashString("not", this), new Integer(45));
	literals.put(new ANTLRHashString("fetch", this), new Integer(26));
	literals.put(new ANTLRHashString("from", this), new Integer(27));
	literals.put(new ANTLRHashString("null", this), new Integer(46));
	literals.put(new ANTLRHashString("count", this), new Integer(15));
	literals.put(new ANTLRHashString("sqrt", this), new Integer(55));
	literals.put(new ANTLRHashString("mod", this), new Integer(43));
	literals.put(new ANTLRHashString("upper", this), new Integer(64));
	literals.put(new ANTLRHashString("like", this), new Integer(37));
	literals.put(new ANTLRHashString("inner", this), new Integer(31));
	literals.put(new ANTLRHashString("leading", this), new Integer(34));
	literals.put(new ANTLRHashString("trim", this), new Integer(60));
	literals.put(new ANTLRHashString("set", this), new Integer(53));
	literals.put(new ANTLRHashString("current_time", this), new Integer(17));
	literals.put(new ANTLRHashString("escape", this), new Integer(23));
	literals.put(new ANTLRHashString("join", this), new Integer(33));
	literals.put(new ANTLRHashString("of", this), new Integer(48));
	literals.put(new ANTLRHashString("is", this), new Integer(32));
	literals.put(new ANTLRHashString("member", this), new Integer(41));
	literals.put(new ANTLRHashString("or", this), new Integer(49));
	literals.put(new ANTLRHashString("any", this), new Integer(7));
	literals.put(new ANTLRHashString("length", this), new Integer(36));
	literals.put(new ANTLRHashString("min", this), new Integer(42));
	literals.put(new ANTLRHashString("as", this), new Integer(8));
	literals.put(new ANTLRHashString("by", this), new Integer(13));
	literals.put(new ANTLRHashString("all", this), new Integer(5));
	literals.put(new ANTLRHashString("order", this), new Integer(50));
	literals.put(new ANTLRHashString("both", this), new Integer(12));
	literals.put(new ANTLRHashString("current_date", this), new Integer(16));
	literals.put(new ANTLRHashString("some", this), new Integer(56));
	literals.put(new ANTLRHashString("size", this), new Integer(54));
	literals.put(new ANTLRHashString("false", this), new Integer(25));
	literals.put(new ANTLRHashString("exists", this), new Integer(24));
	literals.put(new ANTLRHashString("asc", this), new Integer(9));
	literals.put(new ANTLRHashString("unknown", this), new Integer(62));
	literals.put(new ANTLRHashString("left", this), new Integer(35));
	literals.put(new ANTLRHashString("lower", this), new Integer(39));
	literals.put(new ANTLRHashString("desc", this), new Integer(19));
	literals.put(new ANTLRHashString("max", this), new Integer(40));
	literals.put(new ANTLRHashString("empty", this), new Integer(22));
	literals.put(new ANTLRHashString("sum", this), new Integer(58));
	literals.put(new ANTLRHashString("in", this), new Integer(30));
	literals.put(new ANTLRHashString("avg", this), new Integer(10));
	literals.put(new ANTLRHashString("update", this), new Integer(63));
	literals.put(new ANTLRHashString("true", this), new Integer(61));
	literals.put(new ANTLRHashString("group", this), new Integer(28));
	literals.put(new ANTLRHashString("having", this), new Integer(29));
	literals.put(new ANTLRHashString("current_timestamp", this), new Integer(18));
}

public Token nextToken() throws TokenStreamException {
	Token theRetToken=null;
tryAgain:
	for (;;) {
		Token _token = null;
		int _ttype = Token.INVALID_TYPE;
		resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				switch ( LA(1)) {
				case '\t':  case '\n':  case '\r':  case ' ':
				{
					mWS(true);
					theRetToken=_returnToken;
					break;
				}
				case '(':
				{
					mLEFT_ROUND_BRACKET(true);
					theRetToken=_returnToken;
					break;
				}
				case ')':
				{
					mRIGHT_ROUND_BRACKET(true);
					theRetToken=_returnToken;
					break;
				}
				case ',':
				{
					mCOMMA(true);
					theRetToken=_returnToken;
					break;
				}
				case '.':  case '0':  case '1':  case '2':
				case '3':  case '4':  case '5':  case '6':
				case '7':  case '8':  case '9':
				{
					mNUM_INT(true);
					theRetToken=_returnToken;
					break;
				}
				case '=':
				{
					mEQUALS(true);
					theRetToken=_returnToken;
					break;
				}
				case '*':
				{
					mMULTIPLY(true);
					theRetToken=_returnToken;
					break;
				}
				case '/':
				{
					mDIVIDE(true);
					theRetToken=_returnToken;
					break;
				}
				case '+':
				{
					mPLUS(true);
					theRetToken=_returnToken;
					break;
				}
				case '-':
				{
					mMINUS(true);
					theRetToken=_returnToken;
					break;
				}
				case '?':
				{
					mPOSITIONAL_PARAM(true);
					theRetToken=_returnToken;
					break;
				}
				case ':':
				{
					mNAMED_PARAM(true);
					theRetToken=_returnToken;
					break;
				}
				case '"':
				{
					mSTRING_LITERAL_DOUBLE_QUOTED(true);
					theRetToken=_returnToken;
					break;
				}
				case '\'':
				{
					mSTRING_LITERAL_SINGLE_QUOTED(true);
					theRetToken=_returnToken;
					break;
				}
				default:
					if ((LA(1)=='>') && (LA(2)=='=')) {
						mGREATER_THAN_EQUAL_TO(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='<') && (LA(2)=='=')) {
						mLESS_THAN_EQUAL_TO(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='<') && (LA(2)=='>')) {
						mNOT_EQUAL_TO(true);
						theRetToken=_returnToken;
					}
					else if ((_tokenSet_0.member(LA(1)))) {
						mIDENT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='>') && (true)) {
						mGREATER_THAN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='<') && (true)) {
						mLESS_THAN(true);
						theRetToken=_returnToken;
					}
				else {
					if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}
				else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				}
				if ( _returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = _returnToken.getType();
				_ttype = testLiteralsTable(_ttype);
				_returnToken.setType(_ttype);
				return _returnToken;
			}
			catch (RecognitionException e) {
				throw new TokenStreamRecognitionException(e);
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException ) {
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			}
			else {
				throw new TokenStreamException(cse.getMessage());
			}
		}
	}
}

	protected final void mHEX_DIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = HEX_DIGIT;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			matchRange('0','9');
			break;
		}
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':
		{
			matchRange('a','f');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = WS;
		int _saveIndex;
		
		{
		int _cnt170=0;
		_loop170:
		do {
			switch ( LA(1)) {
			case ' ':
			{
				match(' ');
				break;
			}
			case '\t':
			{
				match('\t');
				break;
			}
			case '\n':
			{
				match('\n');
				break;
			}
			case '\r':
			{
				match('\r');
				break;
			}
			default:
			{
				if ( _cnt170>=1 ) { break _loop170; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			}
			_cnt170++;
		} while (true);
		}
		_ttype = Token.SKIP;
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLEFT_ROUND_BRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LEFT_ROUND_BRACKET;
		int _saveIndex;
		
		match('(');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mRIGHT_ROUND_BRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RIGHT_ROUND_BRACKET;
		int _saveIndex;
		
		match(')');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMA;
		int _saveIndex;
		
		match(',');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mIDENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = IDENT;
		int _saveIndex;
		
		mTEXTCHAR(false);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mTEXTCHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = TEXTCHAR;
		int _saveIndex;
		char  c1 = '\0';
		char  c2 = '\0';
		
		{
		switch ( LA(1)) {
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':  case 'g':  case 'h':
		case 'i':  case 'j':  case 'k':  case 'l':
		case 'm':  case 'n':  case 'o':  case 'p':
		case 'q':  case 'r':  case 's':  case 't':
		case 'u':  case 'v':  case 'w':  case 'x':
		case 'y':  case 'z':
		{
			matchRange('a','z');
			break;
		}
		case '_':
		{
			match('_');
			break;
		}
		case '$':
		{
			match('$');
			break;
		}
		default:
			if (((LA(1) >= '\u0080' && LA(1) <= '\ufffe'))) {
				c1 = LA(1);
				matchRange('\u0080','\uFFFE');
				
				if (!Character.isJavaIdentifierStart(c1)) {
				throw new NoViableAltForCharException(
				c1, getFilename(), getLine(), getColumn());
				}
				
			}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		_loop178:
		do {
			switch ( LA(1)) {
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':  case 'g':  case 'h':
			case 'i':  case 'j':  case 'k':  case 'l':
			case 'm':  case 'n':  case 'o':  case 'p':
			case 'q':  case 'r':  case 's':  case 't':
			case 'u':  case 'v':  case 'w':  case 'x':
			case 'y':  case 'z':
			{
				matchRange('a','z');
				break;
			}
			case '_':
			{
				match('_');
				break;
			}
			case '$':
			{
				match('$');
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				matchRange('0','9');
				break;
			}
			default:
				if (((LA(1) >= '\u0080' && LA(1) <= '\ufffe'))) {
					c2 = LA(1);
					matchRange('\u0080','\uFFFE');
					
					if (!Character.isJavaIdentifierPart(c2)) {
					throw new NoViableAltForCharException(
					c2, getFilename(), getLine(), getColumn());
					}
					
				}
			else {
				break _loop178;
			}
			}
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mNUM_INT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NUM_INT;
		int _saveIndex;
		
		boolean isDecimal=false; 
		int tokenType = NUM_DOUBLE;
		
		
		switch ( LA(1)) {
		case '.':
		{
			match('.');
			_ttype = DOT;
			{
			if (((LA(1) >= '0' && LA(1) <= '9'))) {
				{
				int _cnt182=0;
				_loop182:
				do {
					if (((LA(1) >= '0' && LA(1) <= '9'))) {
						matchRange('0','9');
					}
					else {
						if ( _cnt182>=1 ) { break _loop182; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
					}
					
					_cnt182++;
				} while (true);
				}
				tokenType = NUM_DOUBLE;
				{
				if ((LA(1)=='e')) {
					mEXPONENT(false);
				}
				else {
				}
				
				}
				{
				if ((LA(1)=='d'||LA(1)=='f')) {
					tokenType=mFLOAT_SUFFIX(false);
				}
				else {
				}
				
				}
				_ttype = tokenType;
			}
			else {
			}
			
			}
			break;
		}
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			{
			switch ( LA(1)) {
			case '0':
			{
				match('0');
				isDecimal = true;
				{
				switch ( LA(1)) {
				case 'x':
				{
					{
					match('x');
					}
					{
					int _cnt189=0;
					_loop189:
					do {
						if ((_tokenSet_1.member(LA(1))) && (true) && (true) && (true)) {
							mHEX_DIGIT(false);
						}
						else {
							if ( _cnt189>=1 ) { break _loop189; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
						}
						
						_cnt189++;
					} while (true);
					}
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				{
					{
					int _cnt191=0;
					_loop191:
					do {
						if (((LA(1) >= '0' && LA(1) <= '7'))) {
							matchRange('0','7');
						}
						else {
							if ( _cnt191>=1 ) { break _loop191; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
						}
						
						_cnt191++;
					} while (true);
					}
					break;
				}
				default:
					{
					}
				}
				}
				break;
			}
			case '1':  case '2':  case '3':  case '4':
			case '5':  case '6':  case '7':  case '8':
			case '9':
			{
				{
				matchRange('1','9');
				}
				{
				_loop194:
				do {
					if (((LA(1) >= '0' && LA(1) <= '9'))) {
						matchRange('0','9');
					}
					else {
						break _loop194;
					}
					
				} while (true);
				}
				isDecimal=true;
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			{
			if ((LA(1)=='l')) {
				{
				match('l');
				}
				_ttype = NUM_LONG;
			}
			else if (((_tokenSet_2.member(LA(1))))&&(isDecimal)) {
				tokenType = NUM_DOUBLE;
				{
				switch ( LA(1)) {
				case '.':
				{
					match('.');
					{
					_loop199:
					do {
						if (((LA(1) >= '0' && LA(1) <= '9'))) {
							matchRange('0','9');
						}
						else {
							break _loop199;
						}
						
					} while (true);
					}
					{
					if ((LA(1)=='e')) {
						mEXPONENT(false);
					}
					else {
					}
					
					}
					{
					if ((LA(1)=='d'||LA(1)=='f')) {
						tokenType=mFLOAT_SUFFIX(false);
					}
					else {
					}
					
					}
					break;
				}
				case 'e':
				{
					mEXPONENT(false);
					{
					if ((LA(1)=='d'||LA(1)=='f')) {
						tokenType=mFLOAT_SUFFIX(false);
					}
					else {
					}
					
					}
					break;
				}
				case 'd':  case 'f':
				{
					tokenType=mFLOAT_SUFFIX(false);
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				_ttype = tokenType;
			}
			else {
			}
			
			}
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mEXPONENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EXPONENT;
		int _saveIndex;
		
		{
		match('e');
		}
		{
		switch ( LA(1)) {
		case '+':
		{
			match('+');
			break;
		}
		case '-':
		{
			match('-');
			break;
		}
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		int _cnt207=0;
		_loop207:
		do {
			if (((LA(1) >= '0' && LA(1) <= '9'))) {
				matchRange('0','9');
			}
			else {
				if ( _cnt207>=1 ) { break _loop207; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			
			_cnt207++;
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final int  mFLOAT_SUFFIX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int tokenType;
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = FLOAT_SUFFIX;
		int _saveIndex;
		tokenType = NUM_DOUBLE;
		
		switch ( LA(1)) {
		case 'f':
		{
			match('f');
			tokenType = NUM_FLOAT;
			break;
		}
		case 'd':
		{
			match('d');
			tokenType = NUM_DOUBLE;
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
		return tokenType;
	}
	
	public final void mEQUALS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EQUALS;
		int _saveIndex;
		
		match('=');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mGREATER_THAN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = GREATER_THAN;
		int _saveIndex;
		
		match('>');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mGREATER_THAN_EQUAL_TO(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = GREATER_THAN_EQUAL_TO;
		int _saveIndex;
		
		match(">=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLESS_THAN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LESS_THAN;
		int _saveIndex;
		
		match('<');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLESS_THAN_EQUAL_TO(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LESS_THAN_EQUAL_TO;
		int _saveIndex;
		
		match("<=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mNOT_EQUAL_TO(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NOT_EQUAL_TO;
		int _saveIndex;
		
		match("<>");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mMULTIPLY(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MULTIPLY;
		int _saveIndex;
		
		match("*");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDIVIDE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DIVIDE;
		int _saveIndex;
		
		match("/");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PLUS;
		int _saveIndex;
		
		match("+");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mMINUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MINUS;
		int _saveIndex;
		
		match("-");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mPOSITIONAL_PARAM(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = POSITIONAL_PARAM;
		int _saveIndex;
		
		match("?");
		{
		matchRange('1','9');
		}
		{
		_loop222:
		do {
			if (((LA(1) >= '0' && LA(1) <= '9'))) {
				matchRange('0','9');
			}
			else {
				break _loop222;
			}
			
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mNAMED_PARAM(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NAMED_PARAM;
		int _saveIndex;
		
		match(":");
		mTEXTCHAR(false);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSTRING_LITERAL_DOUBLE_QUOTED(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STRING_LITERAL_DOUBLE_QUOTED;
		int _saveIndex;
		
		match('"');
		{
		_loop227:
		do {
			if ((_tokenSet_3.member(LA(1)))) {
				{
				match(_tokenSet_3);
				}
			}
			else {
				break _loop227;
			}
			
		} while (true);
		}
		match('"');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSTRING_LITERAL_SINGLE_QUOTED(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STRING_LITERAL_SINGLE_QUOTED;
		int _saveIndex;
		
		match('\'');
		{
		_loop232:
		do {
			if ((LA(1)=='\'') && (LA(2)=='\'')) {
				{
				match("''");
				}
			}
			else if ((_tokenSet_4.member(LA(1)))) {
				{
				match(_tokenSet_4);
				}
			}
			else {
				break _loop232;
			}
			
		} while (true);
		}
		match('\'');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	
	private static final long[] mk_tokenSet_0() {
		long[] data = new long[3072];
		data[0]=68719476736L;
		data[1]=576460745860972544L;
		for (int i = 2; i<=1022; i++) { data[i]=-1L; }
		data[1023]=9223372036854775807L;
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = new long[1025];
		data[0]=287948901175001088L;
		data[1]=541165879296L;
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = new long[1025];
		data[0]=70368744177664L;
		data[1]=481036337152L;
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = new long[2048];
		data[0]=-17179869185L;
		for (int i = 1; i<=1022; i++) { data[i]=-1L; }
		data[1023]=9223372036854775807L;
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = new long[2048];
		data[0]=-549755813889L;
		for (int i = 1; i<=1022; i++) { data[i]=-1L; }
		data[1023]=9223372036854775807L;
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	
	}
