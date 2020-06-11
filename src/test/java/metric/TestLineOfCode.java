package metric;
import lexer.event.LexerEventCenter;
import metric.parser.java.JavaProcessor;
import metric.measure.MetricContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestLineOfCode {
    @Test
    public void test_normal_lines_should_compute_successfully(){
        String file = "./src/test/resources/java-code-examples/NoBlankLineNoCommentLine.java";
        new LangRegister();
        MetricContext context = new MetricContext();
        LexerEventCenter.getInstance().addObserver(context);
        JavaProcessor processor = new JavaProcessor();
        processor.process(file,context);
        assertEquals(new Long(9L),context.getFile(file).getLoc());
    }

    @Test
    public void test_normal_lines_no_trail_should_compute_successfully(){
        String file = "./src/test/resources/java-code-examples/NoBlankLineNoCommentLineNoTrailLine.java";
        new LangRegister();
        MetricContext context = new MetricContext();
        LexerEventCenter.getInstance().addObserver(context);
        JavaProcessor processor = new JavaProcessor();
        processor.process(file,context);
        assertEquals(new Long(9L),context.getFile(file).getLoc());
    }

    @Test
    public void test_comment_line_should_compute_successfully(){
        String file = "./src/test/resources/java-code-examples/WithCommentLine.java";
        new LangRegister();
        MetricContext context = new MetricContext();
        LexerEventCenter.getInstance().addObserver(context);
        JavaProcessor processor = new JavaProcessor();
        processor.process(file,context);
        assertEquals(new Long(1L),context.getFile(file).getLoc());
    }

    @Test
    public void test_mixed_comment_line_should_compute_successfully(){
        String file = "./src/test/resources/java-code-examples/MixedCommentLine.java";
        new LangRegister();
        MetricContext context = new MetricContext();
        LexerEventCenter.getInstance().addObserver(context);
        JavaProcessor processor = new JavaProcessor();
        processor.process(file,context);
        assertEquals(new Long(5L),context.getFile(file).getLoc());
    }
}
