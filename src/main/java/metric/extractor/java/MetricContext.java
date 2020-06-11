package metric.extractor.java;

import depends.extractor.java.NewFileEvent;
import depends.extractor.java.NewLineEvent;

import java.util.*;

public class MetricContext implements Observer {
    Map<String,MetricData> dataList = new HashMap<>();
    MetricData data;
    public void newFile(String fileFullPath) {
        data = new MetricData();
        dataList.put(fileFullPath,data);
        data.setPath(fileFullPath);
        data.setLoc(0L);
    }

    public void addStmt() {
    }

    public void dump(){
        for (MetricData data:dataList.values()){
            System.out.println(data.getPath()+ ":" + data.getLoc());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof NewFileEvent){
            NewFileEvent newFileEvent = (NewFileEvent) arg;
            newFile(newFileEvent.getFileFullPath());
        }else if (arg instanceof NewLineEvent){
            NewLineEvent newLineEvent = (NewLineEvent)arg;
            if (!newLineEvent.getText().isEmpty()){
                System.out.println(newLineEvent.getText());
                data.incrLoc();
            }
        }
    }

    public MetricData getFile(String file) {
        return dataList.get(file);
    }
}
