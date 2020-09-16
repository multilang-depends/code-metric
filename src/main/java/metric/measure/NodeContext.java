package metric.measure;

import lexer.event.NewFileEvent;
import metric.Container;
import multilang.depends.util.file.path.DotPathFilenameWritter;
import multilang.depends.util.file.strip.LeadingNameStripper;

import java.util.*;

public class NodeContext implements Observer {
    HashMap<String,Container> containerMap;
    LeadingNameStripper stripper;
    String projectName;
    DotPathFilenameWritter writer = new DotPathFilenameWritter();
    int nextId = 0;
    Container currentContainer= null;
    public NodeContext(String projectName,String leadingPath){
        containerMap = new HashMap<>();
        this.projectName = projectName;
        stripper = new LeadingNameStripper(true,leadingPath,new String[]{});
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof NewFileEvent){
            NewFileEvent newFileEvent = (NewFileEvent) arg;
            String fileName = newFileEvent.getFileFullPath();
            String lang = newFileEvent.getLang();
            addToContainer(fileName);
        }
    }

    private void addToContainer(String fileName) {
        fileName = stripper.stripFilename(fileName);
        String dottedName = writer.reWrite(fileName);
        dottedName = projectName + "."+dottedName;

        String[] paths = dottedName.split("\\.");
        for (int i=0;i<paths.length;i++){
            String type = (i==paths.length-1)?"FILE":"DIRECTORY";
            String path = buildFullPath(paths, i);
            if (!containerMap.containsKey(path)){
                Container container = new Container(nextId++,this.projectName,path,type);
                int parentId = (i==0)?-1:containerMap.get(buildFullPath(paths,i-1)).getId();
                if (parentId>=0){
                    containerMap.get(buildFullPath(paths,i-1)).getChildren().add(container);
                }
                container.setParentId(parentId);
                containerMap.put(path, container);
            }
        }

        currentContainer = containerMap.get(dottedName);
    }

    private String buildFullPath(String[] paths, int lastIndex) {
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<=lastIndex;i++){
            sb.append(paths[i]);
            if (i!=lastIndex)
                sb.append(".");
        }
        return sb.toString();
    }
}

