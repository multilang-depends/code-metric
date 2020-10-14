package metric.measure;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public  class MetricValue {
    private final SimpleStringProperty name;
    private final SimpleLongProperty lineCount;
    public MetricValue(String name, Long lineCount) {
            this.name = new SimpleStringProperty(name);
            this.lineCount = new SimpleLongProperty(lineCount);
        }
        public String getName() {
            return name.get();
        }
        public Long getLineCount() {
            return lineCount.get();
        }
    }