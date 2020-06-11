package metric.parser.python;

import metric.measure.MetricContext;
import metric.parser.AbstractLangProcessor;

import java.util.Arrays;
import java.util.List;

public class PythonProcessor extends AbstractLangProcessor {
    @Override
    protected List<String> fileSuffixes() {
        return Arrays.asList("py");
    }

    @Override
    public String supportedLanguage() {
        return "python";
    }

    @lombok.SneakyThrows
    @Override
    public void process(String fileFullPath, MetricContext context) {
        PythonFileParser parser = new PythonFileParser(fileFullPath,context);
        parser.parse();
    }
}
