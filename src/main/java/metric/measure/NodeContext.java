package metric.measure;

import com.github.gangz.source.text.IdentifierSplitter;
import lexer.event.*;
import metric.element.*;
import multilang.depends.util.file.path.DotPathFilenameWritter;
import multilang.depends.util.file.strip.LeadingNameStripper;

import java.util.*;

public class NodeContext implements Observer {
    public static final String TYPE_CLASS = "CLASS";
    public static final String TYPE_METHOD = "METHOD";
    public static final String TYPE_GENERIC_CONTAINER = "CONTAINER";
    public static final String TYPE_FILE = "FILE";
    public static final String TYPE_DIRECTORY = "DIRECTORY";
    public static final String IGNORE = "0-Ignore";
    public static final String BLANK = "Blank";
    public static final String CODE = "Code";
    public static final String COMMENT = "Comment";
    HashMap<String,Container> containerMap;
    LeadingNameStripper stripper;
    String projectName;
    DotPathFilenameWritter writer = new DotPathFilenameWritter();
    int nextId = 0;
    IdentifierSplitter splitter = new IdentifierSplitter();

    Stack<Container> containers = new Stack<>();
    public NodeContext(String projectName,String leadingPath){
        containerMap = new HashMap<>();
        this.projectName = projectName;
        stripper = new LeadingNameStripper(true,leadingPath,new String[]{});
    }

    @Override
    public void update(Observable o, Object event) {
        countLineNumberInLexerContext(event);

        if (event instanceof NewFileEvent){
            NewFileEvent newFileEvent = (NewFileEvent) event;
            String fileName = newFileEvent.getFileFullPath();
            Container file = addToContainer(fileName);
            containers.push(file);
        }
        if (event instanceof ContainerElementEvent){
            ContainerElementEvent containerElementEvent = (ContainerElementEvent) event;
            String type = computeTypeOf(containerElementEvent);
            Container container = null;
            if (type.equals(TYPE_CLASS))
                container = new ClassContainer(nextId++,this.projectName,currentContainer().getName()+"."+containerElementEvent.getName());
            else if (type.equals(TYPE_METHOD))
                container = new MethodContainer(nextId++,this.projectName,currentContainer().getName()+"."+containerElementEvent.getName());
            container.setParentId(currentContainer().getId());
            container.setShortName(containerElementEvent.getName());
            currentContainer().getChildren().add(container);
            containerMap.put(container.getName(), container);
            container.getWords().addAll(splitter.split(containerElementEvent.getName()));
            containers.push(container);
            container.setLineCount(computeLines(((ContainerElementEvent) event).getStartLine(),((ContainerElementEvent) event).getEndLine()));
        }

        if (event instanceof LeaveLastContainerEvent){
            containers.pop();
        }
        if (event instanceof DoneFileEvent){
            containers.get(0).setLineCount(computeLines(0,lineMarks.size()));
            containers.clear();
        }

        computeCodeCognitiveComplexity(event);
    }

    private void computeCodeCognitiveComplexity(Object event) {
        if (event instanceof ControlFlowAddEvent){
            ControlFlowAddEvent controlFlowAddEvent = (ControlFlowAddEvent)event;
            containers.peek().addCognitiveComplexity(controlFlowAddEvent.getFlowAdding());
        }
        if (event instanceof ExpressionConditionalEvent){
//
        }
    }

    private int computeLines(int startLine, int endLine) {
        int count = 0;
        for (int i=startLine;i<endLine;i++){
            if (lineMarks.get(i).equals(CODE))
                count++;
        }
        return  count;
    }

    ArrayList<String> lineMarks = new ArrayList<>();
    private void countLineNumberInLexerContext(Object arg) {
        if (arg instanceof  NewFileEvent){
            lineMarks = new ArrayList<>();
            lineMarks.add(IGNORE);
        }
        if (arg instanceof NewLineEvent) {
            NewLineEvent newLineEvent = (NewLineEvent) arg;
            if (newLineEvent.getText() == null) return;
            int currentNumber = newLineEvent.getLineNumber();
            while (lineMarks.size()<=currentNumber){
                if (lineMarks.size()==currentNumber){
                    lineMarks.add(newLineEvent.getText().isEmpty()? BLANK : CODE);
                }else{
                    lineMarks.add(COMMENT);
                }
            }
        }
    }


    private String computeTypeOf(ContainerElementEvent event) {
        if (event instanceof ClassOrIntefaceDeclareEvent) return TYPE_CLASS;
        if (event instanceof MethodDeclareEvent) return TYPE_METHOD;
        return TYPE_GENERIC_CONTAINER;
    }

    private Container currentContainer() {
        return containers.peek();
    }

    private Container addToContainer(String fileName) {
        fileName = stripper.stripFilename(fileName);
        String dottedName = writer.reWrite(fileName);
        dottedName = projectName + "."+dottedName;

        String[] paths = dottedName.split("\\.");
        for (int i=0;i<paths.length;i++){
            String type = (i==paths.length-1)? TYPE_FILE : TYPE_DIRECTORY;
            String path = buildFullPath(paths, i);
            if (!containerMap.containsKey(path)){
                Container container ;
                if (type.equals(TYPE_FILE))
                    container = new FileContainer(nextId++,this.projectName,path);
                else
                    container = new DirectoryContainer(nextId++,this.projectName,path);                container.setShortName(paths[i]);
                int parentId = (i==0)?-1:containerMap.get(buildFullPath(paths,i-1)).getId();
                if (parentId>=0){
                    containerMap.get(buildFullPath(paths,i-1)).getChildren().add(container);
                }
                container.setParentId(parentId);
                containerMap.put(path, container);
            }
        }
        return containerMap.get(dottedName);
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

