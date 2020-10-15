package metric.parser.java;

import metric.extractor.java.JavaParser;
import metric.extractor.java.JavaParserBaseListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.HashSet;
import java.util.Set;

public class ControlFlowCount {
    Set<String> controlFlowToken = new HashSet<>();

    public class ExpressionListener extends JavaParserBaseListener{
        @Override
        public void enterExpression(JavaParser.ExpressionContext ctx) {
            if (ctx.bop!=null && (ctx.bop.getText().equals("&&")||ctx.bop.getText().equals("||"))){
                controlFlowToken.add(ctx.bop.getText());
            }
            super.enterExpression(ctx);
        }
    }
    public ControlFlowCount(JavaParser.ExpressionContext ctx) {
        ExpressionListener listener = new ExpressionListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener,ctx);
    }

    public int count() {
        return  controlFlowToken.size();
    }
}
