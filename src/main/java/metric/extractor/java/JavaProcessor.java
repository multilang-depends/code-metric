package metric.extractor.java;

import metric.extractor.AbstractLangProcessor;
import metric.extractor.FileParser;

public class JavaProcessor extends AbstractLangProcessor {
    @Override
    protected String fileSuffixes() {
        return null;
    }

    @Override
    protected FileParser createFileParser(String fileFullPath) {
        return null;
    }

    @Override
    public String supportedLanguage() {
        return null;
    }
}
