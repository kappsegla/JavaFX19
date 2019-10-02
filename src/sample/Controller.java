package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class Controller {

    @FXML
    Button button1;

    @FXML
    TextField textField;

    public Controller(){
        //button1 still null
    }

    public void initialize(){
        //Will run after all fields are set and view is ready


    }

    public void button1Action(ActionEvent actionEvent) {
        button1.setText("New Text");
        button1.setDisable(true);
    }

    public void circleClicked(MouseEvent mouseEvent) {
        Circle circle = (Circle) mouseEvent.getSource();
        circle.setRadius(40.0);
        button1.setDisable(false);
    }
}
