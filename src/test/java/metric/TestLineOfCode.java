package metric;
import depends.extractor.java.LexerEventCenter;
import metric.extractor.java.JavaProcessor;
import metric.extractor.java.MetricContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestLineOfCode {
    @Test
    public void test_normal_lines_should_compute_successfully(){
        String file = "./resources/java-code-examples/NoBlankLineNoCommentLine.java";
        new LangRegister();
        MetricContext context = new MetricContext();
        LexerEventCenter.getInstance().addObserver(context);
        JavaProcessor processor = new JavaProcessor();
        processor.process(file,context);
        assertEquals(new Long(9L),context.getFile(file).getLoc());
    }

    @Test
    public void test_normal_lines_no_trail_should_compute_successfully(){
        String file = "./resources/java-code-examples/NoBlankLineNoCommentLineNoTrailLine.java";
        new LangRegister();
        MetricContext context = new MetricContext();
        LexerEventCenter.getInstance().addObserver(context);
        JavaProcessor processor = new JavaProcessor();
        processor.process(file,context);
        assertEquals(new Long(9L),context.getFile(file).getLoc());
    }


}
