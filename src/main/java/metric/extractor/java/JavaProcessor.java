package metric.extractor.java;

import metric.extractor.AbstractLangProcessor;
import metric.extractor.FileParser;

import java.util.Arrays;
import java.util.List;

public class JavaProcessor extends AbstractLangProcessor {
    @Override
    protected List<String> fileSuffixes() {
        return Arrays.asList("java");
    }

    @Override
    public String supportedLanguage() {
        return "java";
    }

    @lombok.SneakyThrows
    @Override
    public void process(String fileFullPath, MetricContext context) {
        JavaFileParser parser = new JavaFileParser(fileFullPath,context);
        parser.parse();
    }
}
