package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //System.out.println(Thread.currentThread().getName());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        //Create our model instance
        Model model = new Model();
        //We can't use defualt noargs constructor on Controller.
        //Tell loader how to create our controller with the constructor that takes a model
        loader.setControllerFactory(param -> new Controller(model));
        Parent root = loader.load();

        Controller controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        controller.init(primaryStage.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        //System.out.println(Thread.currentThread().getName());
        launch(args);
    }
}
