package depends.extractor.java;
import depends.extractor.LexerBase;
import lexer.event.LexerEventCenter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.Interval;

public abstract class JavaLexerBase extends LexerBase {

    public JavaLexerBase(CharStream input) {
        super(input);
    }
}
