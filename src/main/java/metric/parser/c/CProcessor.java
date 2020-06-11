package metric.parser.c;

import metric.measure.MetricContext;
import metric.parser.AbstractLangProcessor;

import java.util.Arrays;
import java.util.List;

public class CProcessor extends AbstractLangProcessor {
    @Override
    protected List<String> fileSuffixes() {
        return Arrays.asList("c");
    }

    @Override
    public String supportedLanguage() {
        return "c";
    }

    @lombok.SneakyThrows
    @Override
    public void process(String fileFullPath, MetricContext context) {
        CFileParser parser = new CFileParser(fileFullPath,context);
        parser.parse();
    }
}
