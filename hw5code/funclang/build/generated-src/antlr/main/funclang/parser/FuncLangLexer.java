// Generated from funclang\parser\FuncLang.g by ANTLR 4.5
package funclang.parser; import static funclang.AST.*;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FuncLangLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		Define=10, Let=11, Letrec=12, Lambda=13, If=14, Car=15, Cdr=16, Cons=17, 
		List=18, Null=19, Less=20, Equal=21, Greater=22, TrueLiteral=23, FalseLiteral=24, 
		Ref=25, Deref=26, Assign=27, Free=28, Fork=29, Lock=30, UnLock=31, Process=32, 
		Send=33, Stop=34, Self=35, Dot=36, Number=37, Identifier=38, Letter=39, 
		LetterOrDigit=40, StrLiteral=41, AT=42, ELLIPSIS=43, WS=44, Comment=45, 
		Line_Comment=46;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"Define", "Let", "Letrec", "Lambda", "If", "Car", "Cdr", "Cons", "List", 
		"Null", "Less", "Equal", "Greater", "TrueLiteral", "FalseLiteral", "Ref", 
		"Deref", "Assign", "Free", "Fork", "Lock", "UnLock", "Process", "Send", 
		"Stop", "Self", "Dot", "Number", "Identifier", "Letter", "LetterOrDigit", 
		"DIGIT", "ESCQUOTE", "StrLiteral", "AT", "ELLIPSIS", "WS", "Comment", 
		"Line_Comment"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'(switch'", "'case'", "'default'", "'-'", "'+'", 
		"'*'", "'/'", "'define'", "'let'", "'letrec'", "'lambda'", "'if'", "'car'", 
		"'cdr'", "'cons'", "'list'", "'null?'", "'<'", "'='", "'>'", "'#t'", "'#f'", 
		"'ref'", "'deref'", "'set!'", "'free'", "'fork'", "'lock'", "'unlock'", 
		"'process'", "'send'", "'stop'", "'self'", "'.'", null, null, null, null, 
		null, "'@'", "'...'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, "Define", 
		"Let", "Letrec", "Lambda", "If", "Car", "Cdr", "Cons", "List", "Null", 
		"Less", "Equal", "Greater", "TrueLiteral", "FalseLiteral", "Ref", "Deref", 
		"Assign", "Free", "Fork", "Lock", "UnLock", "Process", "Send", "Stop", 
		"Self", "Dot", "Number", "Identifier", "Letter", "LetterOrDigit", "StrLiteral", 
		"AT", "ELLIPSIS", "WS", "Comment", "Line_Comment"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public FuncLangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FuncLang.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 38:
			return Letter_sempred((RuleContext)_localctx, predIndex);
		case 39:
			return LetterOrDigit_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean Letter_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return Character.isJavaIdentifierStart(_input.LA(-1));
		case 1:
			return Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)));
		}
		return true;
	}
	private boolean LetterOrDigit_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return Character.isJavaIdentifierPart(_input.LA(-1));
		case 3:
			return Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)));
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\60\u0153\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\3\2\3\2\3\3\3\3\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3"+
		"\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3"+
		"\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3"+
		"\34\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3"+
		"\37\3\37\3 \3 \3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\""+
		"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3%\3%\3&\6&\u0104\n&\r&\16&\u0105\3"+
		"\'\3\'\7\'\u010a\n\'\f\'\16\'\u010d\13\'\3(\3(\3(\3(\3(\3(\5(\u0115\n"+
		"(\3)\3)\3)\3)\3)\3)\5)\u011d\n)\3*\3*\3+\3+\3+\3,\3,\3,\7,\u0127\n,\f"+
		",\16,\u012a\13,\3,\3,\3-\3-\3.\3.\3.\3.\3/\6/\u0135\n/\r/\16/\u0136\3"+
		"/\3/\3\60\3\60\3\60\3\60\7\60\u013f\n\60\f\60\16\60\u0142\13\60\3\60\3"+
		"\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\7\61\u014d\n\61\f\61\16\61\u0150"+
		"\13\61\3\61\3\61\4\u0128\u0140\2\62\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n"+
		"\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30"+
		"/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S\2U\2W+Y"+
		",[-]._/a\60\3\2\t\6\2&&C\\aac|\4\2\2\u0101\ud802\udc01\3\2\ud802\udc01"+
		"\3\2\udc02\ue001\7\2&&\62;C\\aac|\4\2\f\f\17\17\5\2\13\f\16\17\"\"\u015b"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2W\3\2\2\2\2"+
		"Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\3c\3\2\2\2\5e\3"+
		"\2\2\2\7g\3\2\2\2\to\3\2\2\2\13t\3\2\2\2\r|\3\2\2\2\17~\3\2\2\2\21\u0080"+
		"\3\2\2\2\23\u0082\3\2\2\2\25\u0084\3\2\2\2\27\u008b\3\2\2\2\31\u008f\3"+
		"\2\2\2\33\u0096\3\2\2\2\35\u009d\3\2\2\2\37\u00a0\3\2\2\2!\u00a4\3\2\2"+
		"\2#\u00a8\3\2\2\2%\u00ad\3\2\2\2\'\u00b2\3\2\2\2)\u00b8\3\2\2\2+\u00ba"+
		"\3\2\2\2-\u00bc\3\2\2\2/\u00be\3\2\2\2\61\u00c1\3\2\2\2\63\u00c4\3\2\2"+
		"\2\65\u00c8\3\2\2\2\67\u00ce\3\2\2\29\u00d3\3\2\2\2;\u00d8\3\2\2\2=\u00dd"+
		"\3\2\2\2?\u00e2\3\2\2\2A\u00e9\3\2\2\2C\u00f1\3\2\2\2E\u00f6\3\2\2\2G"+
		"\u00fb\3\2\2\2I\u0100\3\2\2\2K\u0103\3\2\2\2M\u0107\3\2\2\2O\u0114\3\2"+
		"\2\2Q\u011c\3\2\2\2S\u011e\3\2\2\2U\u0120\3\2\2\2W\u0123\3\2\2\2Y\u012d"+
		"\3\2\2\2[\u012f\3\2\2\2]\u0134\3\2\2\2_\u013a\3\2\2\2a\u0148\3\2\2\2c"+
		"d\7*\2\2d\4\3\2\2\2ef\7+\2\2f\6\3\2\2\2gh\7*\2\2hi\7u\2\2ij\7y\2\2jk\7"+
		"k\2\2kl\7v\2\2lm\7e\2\2mn\7j\2\2n\b\3\2\2\2op\7e\2\2pq\7c\2\2qr\7u\2\2"+
		"rs\7g\2\2s\n\3\2\2\2tu\7f\2\2uv\7g\2\2vw\7h\2\2wx\7c\2\2xy\7w\2\2yz\7"+
		"n\2\2z{\7v\2\2{\f\3\2\2\2|}\7/\2\2}\16\3\2\2\2~\177\7-\2\2\177\20\3\2"+
		"\2\2\u0080\u0081\7,\2\2\u0081\22\3\2\2\2\u0082\u0083\7\61\2\2\u0083\24"+
		"\3\2\2\2\u0084\u0085\7f\2\2\u0085\u0086\7g\2\2\u0086\u0087\7h\2\2\u0087"+
		"\u0088\7k\2\2\u0088\u0089\7p\2\2\u0089\u008a\7g\2\2\u008a\26\3\2\2\2\u008b"+
		"\u008c\7n\2\2\u008c\u008d\7g\2\2\u008d\u008e\7v\2\2\u008e\30\3\2\2\2\u008f"+
		"\u0090\7n\2\2\u0090\u0091\7g\2\2\u0091\u0092\7v\2\2\u0092\u0093\7t\2\2"+
		"\u0093\u0094\7g\2\2\u0094\u0095\7e\2\2\u0095\32\3\2\2\2\u0096\u0097\7"+
		"n\2\2\u0097\u0098\7c\2\2\u0098\u0099\7o\2\2\u0099\u009a\7d\2\2\u009a\u009b"+
		"\7f\2\2\u009b\u009c\7c\2\2\u009c\34\3\2\2\2\u009d\u009e\7k\2\2\u009e\u009f"+
		"\7h\2\2\u009f\36\3\2\2\2\u00a0\u00a1\7e\2\2\u00a1\u00a2\7c\2\2\u00a2\u00a3"+
		"\7t\2\2\u00a3 \3\2\2\2\u00a4\u00a5\7e\2\2\u00a5\u00a6\7f\2\2\u00a6\u00a7"+
		"\7t\2\2\u00a7\"\3\2\2\2\u00a8\u00a9\7e\2\2\u00a9\u00aa\7q\2\2\u00aa\u00ab"+
		"\7p\2\2\u00ab\u00ac\7u\2\2\u00ac$\3\2\2\2\u00ad\u00ae\7n\2\2\u00ae\u00af"+
		"\7k\2\2\u00af\u00b0\7u\2\2\u00b0\u00b1\7v\2\2\u00b1&\3\2\2\2\u00b2\u00b3"+
		"\7p\2\2\u00b3\u00b4\7w\2\2\u00b4\u00b5\7n\2\2\u00b5\u00b6\7n\2\2\u00b6"+
		"\u00b7\7A\2\2\u00b7(\3\2\2\2\u00b8\u00b9\7>\2\2\u00b9*\3\2\2\2\u00ba\u00bb"+
		"\7?\2\2\u00bb,\3\2\2\2\u00bc\u00bd\7@\2\2\u00bd.\3\2\2\2\u00be\u00bf\7"+
		"%\2\2\u00bf\u00c0\7v\2\2\u00c0\60\3\2\2\2\u00c1\u00c2\7%\2\2\u00c2\u00c3"+
		"\7h\2\2\u00c3\62\3\2\2\2\u00c4\u00c5\7t\2\2\u00c5\u00c6\7g\2\2\u00c6\u00c7"+
		"\7h\2\2\u00c7\64\3\2\2\2\u00c8\u00c9\7f\2\2\u00c9\u00ca\7g\2\2\u00ca\u00cb"+
		"\7t\2\2\u00cb\u00cc\7g\2\2\u00cc\u00cd\7h\2\2\u00cd\66\3\2\2\2\u00ce\u00cf"+
		"\7u\2\2\u00cf\u00d0\7g\2\2\u00d0\u00d1\7v\2\2\u00d1\u00d2\7#\2\2\u00d2"+
		"8\3\2\2\2\u00d3\u00d4\7h\2\2\u00d4\u00d5\7t\2\2\u00d5\u00d6\7g\2\2\u00d6"+
		"\u00d7\7g\2\2\u00d7:\3\2\2\2\u00d8\u00d9\7h\2\2\u00d9\u00da\7q\2\2\u00da"+
		"\u00db\7t\2\2\u00db\u00dc\7m\2\2\u00dc<\3\2\2\2\u00dd\u00de\7n\2\2\u00de"+
		"\u00df\7q\2\2\u00df\u00e0\7e\2\2\u00e0\u00e1\7m\2\2\u00e1>\3\2\2\2\u00e2"+
		"\u00e3\7w\2\2\u00e3\u00e4\7p\2\2\u00e4\u00e5\7n\2\2\u00e5\u00e6\7q\2\2"+
		"\u00e6\u00e7\7e\2\2\u00e7\u00e8\7m\2\2\u00e8@\3\2\2\2\u00e9\u00ea\7r\2"+
		"\2\u00ea\u00eb\7t\2\2\u00eb\u00ec\7q\2\2\u00ec\u00ed\7e\2\2\u00ed\u00ee"+
		"\7g\2\2\u00ee\u00ef\7u\2\2\u00ef\u00f0\7u\2\2\u00f0B\3\2\2\2\u00f1\u00f2"+
		"\7u\2\2\u00f2\u00f3\7g\2\2\u00f3\u00f4\7p\2\2\u00f4\u00f5\7f\2\2\u00f5"+
		"D\3\2\2\2\u00f6\u00f7\7u\2\2\u00f7\u00f8\7v\2\2\u00f8\u00f9\7q\2\2\u00f9"+
		"\u00fa\7r\2\2\u00faF\3\2\2\2\u00fb\u00fc\7u\2\2\u00fc\u00fd\7g\2\2\u00fd"+
		"\u00fe\7n\2\2\u00fe\u00ff\7h\2\2\u00ffH\3\2\2\2\u0100\u0101\7\60\2\2\u0101"+
		"J\3\2\2\2\u0102\u0104\5S*\2\u0103\u0102\3\2\2\2\u0104\u0105\3\2\2\2\u0105"+
		"\u0103\3\2\2\2\u0105\u0106\3\2\2\2\u0106L\3\2\2\2\u0107\u010b\5O(\2\u0108"+
		"\u010a\5Q)\2\u0109\u0108\3\2\2\2\u010a\u010d\3\2\2\2\u010b\u0109\3\2\2"+
		"\2\u010b\u010c\3\2\2\2\u010cN\3\2\2\2\u010d\u010b\3\2\2\2\u010e\u0115"+
		"\t\2\2\2\u010f\u0110\n\3\2\2\u0110\u0115\6(\2\2\u0111\u0112\t\4\2\2\u0112"+
		"\u0113\t\5\2\2\u0113\u0115\6(\3\2\u0114\u010e\3\2\2\2\u0114\u010f\3\2"+
		"\2\2\u0114\u0111\3\2\2\2\u0115P\3\2\2\2\u0116\u011d\t\6\2\2\u0117\u0118"+
		"\n\3\2\2\u0118\u011d\6)\4\2\u0119\u011a\t\4\2\2\u011a\u011b\t\5\2\2\u011b"+
		"\u011d\6)\5\2\u011c\u0116\3\2\2\2\u011c\u0117\3\2\2\2\u011c\u0119\3\2"+
		"\2\2\u011dR\3\2\2\2\u011e\u011f\4\62;\2\u011fT\3\2\2\2\u0120\u0121\7^"+
		"\2\2\u0121\u0122\7$\2\2\u0122V\3\2\2\2\u0123\u0128\7$\2\2\u0124\u0127"+
		"\5U+\2\u0125\u0127\n\7\2\2\u0126\u0124\3\2\2\2\u0126\u0125\3\2\2\2\u0127"+
		"\u012a\3\2\2\2\u0128\u0129\3\2\2\2\u0128\u0126\3\2\2\2\u0129\u012b\3\2"+
		"\2\2\u012a\u0128\3\2\2\2\u012b\u012c\7$\2\2\u012cX\3\2\2\2\u012d\u012e"+
		"\7B\2\2\u012eZ\3\2\2\2\u012f\u0130\7\60\2\2\u0130\u0131\7\60\2\2\u0131"+
		"\u0132\7\60\2\2\u0132\\\3\2\2\2\u0133\u0135\t\b\2\2\u0134\u0133\3\2\2"+
		"\2\u0135\u0136\3\2\2\2\u0136\u0134\3\2\2\2\u0136\u0137\3\2\2\2\u0137\u0138"+
		"\3\2\2\2\u0138\u0139\b/\2\2\u0139^\3\2\2\2\u013a\u013b\7\61\2\2\u013b"+
		"\u013c\7,\2\2\u013c\u0140\3\2\2\2\u013d\u013f\13\2\2\2\u013e\u013d\3\2"+
		"\2\2\u013f\u0142\3\2\2\2\u0140\u0141\3\2\2\2\u0140\u013e\3\2\2\2\u0141"+
		"\u0143\3\2\2\2\u0142\u0140\3\2\2\2\u0143\u0144\7,\2\2\u0144\u0145\7\61"+
		"\2\2\u0145\u0146\3\2\2\2\u0146\u0147\b\60\2\2\u0147`\3\2\2\2\u0148\u0149"+
		"\7\61\2\2\u0149\u014a\7\61\2\2\u014a\u014e\3\2\2\2\u014b\u014d\n\7\2\2"+
		"\u014c\u014b\3\2\2\2\u014d\u0150\3\2\2\2\u014e\u014c\3\2\2\2\u014e\u014f"+
		"\3\2\2\2\u014f\u0151\3\2\2\2\u0150\u014e\3\2\2\2\u0151\u0152\b\61\2\2"+
		"\u0152b\3\2\2\2\f\2\u0105\u010b\u0114\u011c\u0126\u0128\u0136\u0140\u014e"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}