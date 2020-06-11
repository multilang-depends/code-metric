package depends.extractor.java;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.Interval;

public abstract class JavaLexerBase extends Lexer {
    private final CharStream input;
    int start = 0;
    int lineNumber = 0;
    public  JavaLexerBase(CharStream input){
        super(input);
        this.input = input;
    }
    public void foundNewLine(){
        String text = input.getText(new Interval(start, this.getCharIndex() - 1));
        text = text.replace("\n","").replace("\r","")
                .replace("\t","").trim();
        lineNumber = this.getLine()-1;
        start = this.getCharIndex()-1;
        LexerEventCenter.getInstance().notifyNewLine(lineNumber,text);
    }

    public void foundBlockComment(){

    }
    public void foundLineComment(){
    }
}
