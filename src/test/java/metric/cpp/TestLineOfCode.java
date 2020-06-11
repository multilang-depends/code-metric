package metric.cpp;

import lexer.event.LexerEventCenter;
import metric.LangRegister;
import metric.measure.MetricContext;
import metric.parser.c.CProcessor;
import metric.parser.python.PythonProcessor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestLineOfCode {


    @Test
    public void test_mixed_comment_line_should_compute_successfully(){
        String file = "./src/test/resources/cpp-code-examples/grayscale_to_rgb.c";
        new LangRegister();
        MetricContext context = new MetricContext();
        LexerEventCenter.getInstance().addObserver(context);
        CProcessor processor = new CProcessor();
        processor.process(file,context);
        assertEquals(new Long(55L),context.getFile(file).getLoc());
    }
}
