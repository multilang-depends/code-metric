package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import metric.measure.NodeContext;
import ui.controllers.Controller;

import java.net.URL;

public class UIMain extends Application{

    private Controller controller;
    public static NodeContext nodeContext;
    public static void showGui(String[] args, NodeContext context) {
        nodeContext = context;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root, 1300, 275);
        this.controller = (Controller)loader.getController();
        controller.setNodeContext(nodeContext);
        primaryStage.setTitle("Code Metrics");
        primaryStage.setScene(scene);
        controller.loadTreeItems(nodeContext.getRoot());
        primaryStage.show();
    }


}