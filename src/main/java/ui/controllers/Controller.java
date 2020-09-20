package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import metric.Container;
import metric.measure.NodeContext;

public class Controller {
    @FXML
    TreeView tree;



    private NodeContext nodeContext;

    public void initialize(){
    }

    public void loadTreeItems(Container root) {
        TreeItem<String> rootNode  = new TreeItem<>(root.getName());
        rootNode.setExpanded(true);
        loadChildOf(rootNode,root);
        tree.setRoot(rootNode);
        tree.setStyle("-fx-font: 16 arial;");
    }

    private void loadChildOf(TreeItem<String> rootNode, Container root) {
        for (Container child:root.getChildren()){
            TreeItem<String> childNode = new TreeItem<>(child.getShortName());
            childNode.setExpanded(true);
            rootNode.getChildren().add(childNode);
            loadChildOf(childNode,child);
        }
    }

    public void setNodeContext(NodeContext nodeContext) {
        this.nodeContext = nodeContext;
    }
}
