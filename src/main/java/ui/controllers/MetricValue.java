package ui.controllers;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public  class MetricValue {
    private final SimpleStringProperty name;
    private final SimpleLongProperty lineCount;
    private final SimpleLongProperty stmtCount;
    private final SimpleLongProperty cognitiveComplexity;
    public MetricValue(String name, Long lineCount,Long stmtCount, Long cognitiveComplexity) {
            this.name = new SimpleStringProperty(name);
            this.lineCount = new SimpleLongProperty(lineCount);
            this.stmtCount = new SimpleLongProperty(stmtCount);
            this.cognitiveComplexity = new SimpleLongProperty(cognitiveComplexity);
        }
        public String getName() {
            return name.get();
        }
        public Long getLineCount() {
            return lineCount.get();
        }
        public Long getCognitiveComplexity (){
            return cognitiveComplexity.get();
        }
        public Long getStmtCount (){
            return stmtCount.get();
        }
    }