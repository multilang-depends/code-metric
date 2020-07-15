package metric.extractor.java;
import metric.extractor.LexerBase;
import org.antlr.v4.runtime.CharStream;

public abstract class JavaLexerBase extends LexerBase {

    public JavaLexerBase(CharStream input) {
        super(input);
    }
}
