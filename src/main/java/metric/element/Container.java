package metric.element;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract  class Container {
    private int lineCount = 0;
    private int cognitiveComplexity = 1;
    private int stmtCount = 0;
    List<String> words = new ArrayList<>();

    public Container(int id, String project, String name){
        this.id = id;
        this.project = project;
        this.name = name;
    }
    private int id;
    private String project;
    private int parentId;
    private String name;
    private String shortName;
    List<Container> children = new ArrayList<>();

    @Override
    public String toString() {
        return longName();
    }

    public String shortName() {
        return this.getShortName();
    }

    public String longName() {
        return "Container{" +
                "id=" + id +
                ", project='" + project + '\'' +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                '}';
    }

    public void incrLoc() {
        this.lineCount ++;
    }

    public void incrStmt() {
        this.stmtCount ++;
    }

    public void addCognitiveComplexity(int flowAdding) {
        this.cognitiveComplexity +=flowAdding;
    }
}
