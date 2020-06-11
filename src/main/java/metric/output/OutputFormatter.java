package metric.output;

import metric.measure.MetricContext;
import multilang.depends.util.file.path.FilenameWritter;
import multilang.depends.util.file.strip.ILeadingNameStrippper;

import java.io.File;

public abstract  class OutputFormatter {
    protected final MetricContext context;
    private String outputDir;
    private String outputName;

    public OutputFormatter(MetricContext context, String outputDir, String outputName) {
        this.context = context;
        this.outputDir = outputDir;
        this.outputName = outputName;
    }
    protected String composeFilename() {
        return outputDir+ File.separator+outputName;
    }
    public abstract void output(FilenameWritter filenameWritter, ILeadingNameStrippper strippper);
}
