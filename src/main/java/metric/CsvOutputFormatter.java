package metric;

import metric.measure.MetricContext;
import metric.measure.MetricData;
import metric.output.OutputFormatter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

public class CsvOutputFormatter extends OutputFormatter {

    public CsvOutputFormatter(MetricContext context, String outputDir, String outputName) {
        super(context,outputDir,outputName);
    }

    @Override
    public void output() {
        try {
            PrintWriter writer = new PrintWriter(composeFilename()+".csv");
            for (MetricData data:context.getAllEntries()){
                writer.println(data.getPath() + ", " + data.getLoc());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
