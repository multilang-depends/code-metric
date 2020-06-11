package metric.extractor.java;

import lombok.Data;

@Data
public class MetricData {
    String path;
    Long loc;

    public void incrLoc() {
        loc++;
    }
}
