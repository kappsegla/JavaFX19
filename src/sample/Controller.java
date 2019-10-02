package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML
    Circle circle;
    @FXML
    Slider slider;
    @FXML
    ListView<String> listView;


    Model model = new Model();

    public Controller() {
        //@FXML marked fields are still null do nothing here
    }

    public void initialize() {
        //Will run after all fields are set and view is ready
        //Next line replaces onAction="#button1Action" in fxml file
        //button1.addEventHandler(ActionEvent.ACTION,this::button1Action);
        textArea.textProperty().bindBidirectional(model.selectedItemProperty());
        model.textProperty().bindBidirectional(textField.textProperty());
//        checkBox1.selectedProperty().bindBidirectional(model.enabledProperty());
//        textField.disableProperty().bind(model.enabledProperty());
//        textArea.disableProperty().bind(model.enabledProperty());
        checkBox1.selectedProperty().bindBidirectional(textField.disableProperty());
        slider.valueProperty().bindBidirectional(circle.radiusProperty());

        listView.setItems(model.getItems());
        model.selectedItemProperty().bind(listView.getSelectionModel().selectedItemProperty());

    }

    public void button1Action(ActionEvent actionEvent) throws InterruptedException {
      //   model.setText("");
      //  model.setEnabled(false);
        // textArea.setText(model.getText());
        model.getItems().add(textField.getText());


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
