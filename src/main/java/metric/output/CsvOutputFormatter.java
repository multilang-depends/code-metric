package metric.output;

import metric.measure.MetricContext;
import metric.measure.MetricData;
import multilang.depends.util.file.path.FilenameWritter;
import multilang.depends.util.file.strip.ILeadingNameStrippper;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CsvOutputFormatter extends OutputFormatter {

    public CsvOutputFormatter(MetricContext context, String outputDir, String outputName) {
        super(context,outputDir,outputName);
    }

    @Override
    public void output(FilenameWritter filenameWritter, ILeadingNameStrippper strippper) {
        try {
            PrintWriter writer = new PrintWriter(composeFilename()+".csv");
            for (MetricData data:context.getAllEntries()){
                writer.println(filenameWritter.reWrite(strippper.stripFilename(data.getPath())) + ", " + data.getLoc());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
