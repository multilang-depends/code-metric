package metric.measure;

import lombok.Data;

@Data
public class MetricData {
    String lang;
    String path;
    Long loc;

    public void incrLoc() {
        loc++;
    }
}
