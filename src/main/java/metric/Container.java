package metric;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Container {
    public Container(int id, String project, String name, String type){
        this.id = id;
        this.project = project;
        this.name = name;
        this.type = type;
    }
    private int id;
    private String project;
    private int parentId;
    private String name;
    private String type; //directory, file, class, method, etc
    List<Container> children = new ArrayList<>();

    @Override
    public String toString() {
        return "Container{" +
                "id=" + id +
                ", project='" + project + '\'' +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
