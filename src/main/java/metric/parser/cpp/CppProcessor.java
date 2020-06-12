package metric.parser.cpp;

import metric.measure.MetricContext;
import metric.parser.AbstractLangProcessor;

import java.util.Arrays;
import java.util.List;

public class CppProcessor extends AbstractLangProcessor {
    @Override
    protected List<String> fileSuffixes() {
        return Arrays.asList("cpp","c","h","cc","hh","hpp","cxx","c++","hxx");
    }

    @Override
    public String supportedLanguage() {
        return "cpp";
    }

    @lombok.SneakyThrows
    @Override
    public void process(String fileFullPath, MetricContext context) {
        CppFileParser parser = new CppFileParser(fileFullPath,context);
        parser.parse();
    }
}
