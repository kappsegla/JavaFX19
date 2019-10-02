package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class Controller {

    @FXML
    TextArea textArea;
    @FXML
    Button button1;
    @FXML
    TextField textField;
    @FXML
    TextArea textArea;

    Model model = new Model();

    Model model = new Model();

    public Controller(){
        //@FXML marked fields are still null do nothing here
    }

    public void initialize(){
        //Will run after all fields are set and view is ready
        //Next line replaces onAction="#button1Action" in fxml file
        //button1.addEventHandler(ActionEvent.ACTION,this::button1Action);

    }

    public void button1Action(ActionEvent actionEvent) throws InterruptedException {
        model.setText(textField.getText());
        textArea.setText(model.getText());

    }

    public void circleClicked(MouseEvent mouseEvent) {
        Circle circle = (Circle) mouseEvent.getSource();
        circle.setRadius(40.0);
        button1.setDisable(false);
    }

    public void keyPressed(KeyEvent keyEvent) {
        button1.setText(keyEvent.getText());
    }
}
