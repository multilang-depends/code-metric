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
import metric.measure.MetricValue;

public class Controller {
    @FXML
    TreeView tree;

    @FXML
    TableView table;

    private NodeContext nodeContext;
    private final ObservableList<MetricValue> data =
            FXCollections.observableArrayList();
    public void initialize(){
    }

    public void loadTreeItems(Container root) {
        TreeItem<Container> rootNode  = new TreeItem<>(root);
        rootNode.setExpanded(true);
        loadChildOf(rootNode,root);
        tree.setRoot(rootNode);
        tree.setStyle("-fx-font: 16 arial;");

        registerEvent();
        registerCellFactory();

        ((TableColumn)(table.getColumns().get(0))).setCellValueFactory(new PropertyValueFactory<>("name"));
        ((TableColumn)(table.getColumns().get(1))).setCellValueFactory(new PropertyValueFactory<>("lineCount"));
        table.setItems(data);
    }

    private void registerCellFactory() {
        tree.setCellFactory(new Callback<TreeView<Container>, TreeCell<Container>>() {
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
        tree.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                Node node = event.getPickResult().getIntersectedNode();

                if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
                    Container name = (Container) ((TreeItem)tree.getSelectionModel().getSelectedItem()).getValue();
                    data.clear();
                    for (Container child:name.getChildren()){
                        System.out.println(child);
                        data.add(new MetricValue(child.getName(),new Long(child.getLineCount())));
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
