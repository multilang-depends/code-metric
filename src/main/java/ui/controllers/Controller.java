package ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import metric.element.Container;
import metric.measure.NodeContext;

public class Controller {
    @FXML
    TreeView nodeTree;

    @FXML
    TableView detailTable;

    private NodeContext nodeContext;
    private final ObservableList<MetricValue> data =
            FXCollections.observableArrayList();
    public void initialize(){
    }

    public void loadTreeItems(Container root) {
        TreeItem<Container> rootNode  = new TreeItem<>(root);
        rootNode.setExpanded(true);
        loadChildOf(rootNode,root);
        nodeTree.setRoot(rootNode);
        nodeTree.setStyle("-fx-font: 16 arial;");

        registerEvent();
        registerCellFactory();

        ((TableColumn)(detailTable.getColumns().get(0))).setCellValueFactory(new PropertyValueFactory<>("name"));
        ((TableColumn)(detailTable.getColumns().get(1))).setCellValueFactory(new PropertyValueFactory<>("lineCount"));
        ((TableColumn)(detailTable.getColumns().get(2))).setCellValueFactory(new PropertyValueFactory<>("stmtCount"));
        ((TableColumn)(detailTable.getColumns().get(3))).setCellValueFactory(new PropertyValueFactory<>("cognitiveComplexity"));
        detailTable.setItems(data);
    }

    private void registerCellFactory() {
        nodeTree.setCellFactory(new Callback<TreeView<Container>, TreeCell<Container>>() {
            public TreeCell<Container> call(TreeView<Container> tv) {
                return new TreeCell<Container>() {
                    @Override
                    protected void updateItem(Container item, boolean empty) {
                        super.updateItem(item, empty);
                        setText((empty || item == null) ? "" : item.getShortName());
                    }
                };
            }
        });
    }

    private void registerEvent() {
        nodeTree.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                Node node = event.getPickResult().getIntersectedNode();

                if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
                    Container name = (Container) ((TreeItem) nodeTree.getSelectionModel().getSelectedItem()).getValue();
                    data.clear();
                    data.add(new MetricValue("TOTAL",new Long(name.getLineCount()),new Long(name.getStmtCount()),new Long(name.getCognitiveComplexity())));
                    for (Container child:name.getChildren()){
                        data.add(new MetricValue(child.getName(),new Long(child.getLineCount()),new Long(child.getStmtCount()),new Long(child.getCognitiveComplexity())));
                    }
                }
            }
        });
    }

    private void loadChildOf(TreeItem<Container> rootNode, Container root) {
        for (Container child:root.getChildren()){
            TreeItem<Container> childNode = new TreeItem<>(child);
            childNode.setExpanded(true);
            rootNode.getChildren().add(childNode);
            loadChildOf(childNode,child);
        }
    }

    public void setNodeContext(NodeContext nodeContext) {
        this.nodeContext = nodeContext;
    }
}
