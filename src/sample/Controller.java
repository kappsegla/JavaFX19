package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class Controller {

    @FXML
    Button button1;
    @FXML
    TextField textField;
    @FXML
    TextArea textArea;
    @FXML
    CheckBox checkBox1;

    Model model = new Model();

    public Controller(){
        //@FXML marked fields are still null do nothing here
    }

    public void initialize(){
        //Will run after all fields are set and view is ready
        //Next line replaces onAction="#button1Action" in fxml file
        //button1.addEventHandler(ActionEvent.ACTION,this::button1Action);
        textArea.textProperty().bindBidirectional( model.textProperty());
        model.textProperty().bindBidirectional(textField.textProperty());
        checkBox1.selectedProperty().bindBidirectional(model.enabledProperty());
        textField.disableProperty().bind(model.enabledProperty());
        textArea.disableProperty().bind(model.enabledProperty());




    }

    public void button1Action(ActionEvent actionEvent) throws InterruptedException {
        model.setText("");
       // textArea.setText(model.getText());

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
