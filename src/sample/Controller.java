package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

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
    @FXML
    ImageView imageView;

    Model model = new Model();

    Stage stage;

    public Controller() {
        //@FXML marked fields are still null do nothing here
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        //Will run after all fields are set and view is ready
        //Next line replaces onAction="#button1Action" in fxml file
        //button1.addEventHandler(ActionEvent.ACTION,this::button1Action);
        textArea.textProperty().bind(model.selectedItemProperty());
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
        //model.getItems().add(textField.getText());

        Image image = new Image("https://f.nordiskemedier.dk/2x0hf50xws60pfsb.jpg", true);
        imageView.setImage(image);

//         new Thread(() -> {
//          Image image = new Image("https://f.nordiskemedier.dk/2x0hf50xws60pfsb.jpg", false);
//            Platform.runLater(() ->
//                imageView.setImage(image)
//            );
//        }).start();


    }

    public void circleClicked(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Öppna fil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));

        File path = fileChooser.showOpenDialog(stage);

        if( path != null){
            System.out.println(path.getAbsolutePath());
        }
        else
        {
            System.out.println("no file");
        }




        //        Circle circle = (Circle) mouseEvent.getSource();
//        circle.setRadius(40.0);
//        button1.setDisable(false);
    }

    public void keyPressed(KeyEvent keyEvent) {
        button1.setText(keyEvent.getText());
    }
}
