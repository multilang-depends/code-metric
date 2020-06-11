package metric.python;

import lexer.event.LexerEventCenter;
import metric.LangRegister;
import metric.measure.MetricContext;
import metric.parser.python.PythonProcessor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestLineOfCode {


    @Test
    public void test_mixed_comment_line_should_compute_successfully(){
//        String file = "./src/test/resources/python-code-examples/MixedCommentLineOfPython.py";
        String file = "./resources/python-code-examples/MixedCommentLineOfPython.py";
        new LangRegister();
        MetricContext context = new MetricContext();
        LexerEventCenter.getInstance().addObserver(context);
        PythonProcessor processor = new PythonProcessor();
        processor.process(file,context);
        assertEquals(new Long(5L),context.getFile(file).getLoc());
    }
}
