package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;

public class Controller {

    @FXML
    Canvas canvas;
    @FXML
    Button openFileButton;
    @FXML
    ColorPicker colorPicker;
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

    @FXML
    Spinner<Integer> spinner;

    Model model;
    Stage stage;
    GraphicsContext graphicsContext;

    public Controller(Model model) {
        //@FXML marked fields are still null do nothing here
        this.model = model;
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

        colorPicker.setValue((Color) circle.getFill());
        slider.valueProperty().unbind();
        circle.fillProperty().bind(colorPicker.valueProperty());
        model.sizeProperty().bind(spinner.valueProperty());

        setSpinnerFormat();

        //Get a reference to graphics Context for canvas and store it for later use.
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.fillOval(50,50,25,25);
    }

    private void setSpinnerFormat() {
        //The following code adds some validation to our spinner so you can't enter other than number from keyboard
        // get a localized format for parsing
        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                // NumberFormat evaluates the beginning of the text
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0 ||
                        parsePosition.getIndex() < c.getControlNewText().length()) {
                    // reject parsing the complete text failed
                    return null;
                }
            }
            return c;
        };
        TextFormatter<Integer> priceFormatter = new TextFormatter<Integer>(
                new IntegerStringConverter(), 1, filter);
        spinner.getEditor().setTextFormatter(priceFormatter);
    }

    public void button1Action(ActionEvent actionEvent) throws InterruptedException {
        //Load an image with background set to true. Will run in it's own thread
        Image image = new Image("https://f.nordiskemedier.dk/2x0hf50xws60pfsb.jpg", true);
        imageView.setImage(image);
        //Load an image in a thread and use Platform.runLater to change the image when it's available
//         new Thread(() -> {
//          Image image = new Image("https://f.nordiskemedier.dk/2x0hf50xws60pfsb.jpg", false);
//            Platform.runLater(() ->
//                imageView.setImage(image)
//            );
//        }).start();
    }

    public void circleClicked(MouseEvent mouseEvent) {
        Circle circle = (Circle) mouseEvent.getSource();
        circle.setRadius(40.0);
    }

    public void openFileDialog(ActionEvent actionEvent) {
        //Show a file dialog that returns a selected file for opening or null if no file was selected.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Öppna fil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));

        File path = fileChooser.showOpenDialog(stage);

        //Path can be null if abort was selected
        if (path != null) {
            //We have a valid File object. Use with FileReader or FileWriter
            System.out.println(path.getAbsolutePath());
        } else {
            //No file selected
            System.out.println("no file");
        }
    }
}
