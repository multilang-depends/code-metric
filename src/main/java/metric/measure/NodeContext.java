package metric.measure;

import com.github.gangz.source.text.IdentifierSplitter;
import lexer.event.ClassOrIntefaceDeclareEvent;
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
    IdentifierSplitter splitter = new IdentifierSplitter();
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
        if (arg instanceof ClassOrIntefaceDeclareEvent){
            ClassOrIntefaceDeclareEvent event = (ClassOrIntefaceDeclareEvent) arg;
            Container container = new Container(nextId++,this.projectName,currentContainer.getName()+"."+event.getName(),"CLASS");
            container.setParentId(currentContainer.getId());
            container.setShortName(event.getName());
            currentContainer.getChildren().add(container);
            containerMap.put(container.getName(), container);
            container.getWords().addAll(splitter.split(event.getName()));
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
                container.setShortName(paths[i]);
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

    public void dump() {
        this.containerMap.values().forEach(v->{
            System.out.println(v);
        });
    }

    public Container getRoot() {
        return containerMap.get(projectName);
    }
}

