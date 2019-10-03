package chat;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import javax.management.monitor.StringMonitor;
import java.util.Optional;

public class ChatController {
    @FXML
    TextField textField;
    @FXML
    Button sendButton;
    @FXML
    ListView<String> listView;

    ChatModel model = new ChatModel();

    public void initialize() {
        textField.textProperty().bindBidirectional(model.messageProperty());
        listView.setItems(model.getChatMessages());
    }

    public void sendAction(ActionEvent actionEvent) {
        if (model.isConnected())
            model.sendMessage();
        else {
            Optional<Pair<String, Integer>> result = showDialog();
            result.ifPresent(hostPort -> {
                //Run in threadpool?
                model.connect(hostPort.getKey(), hostPort.getValue());
                //Check that we are connected now?
                model.sendMessage();
            });
        }
    }

    public Optional<Pair<String, Integer>> showDialog() {
        // Create the custom dialog.
        Dialog<Pair<String, Integer>> dialog = new Dialog<>();
        dialog.setTitle("Connect to");
        dialog.setHeaderText("Please enter server ip and port");
        dialog.initStyle(StageStyle.UTILITY);

// Set the button types.
        ButtonType connectButtonType = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(connectButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField hostName = new TextField();
        hostName.setPromptText("hostname");
        hostName.setText("82.196.127.10");
        TextField port = new TextField();
        port.setPromptText("port number");
        port.setText("8000");

        grid.add(new Label("Host:"), 0, 0);
        grid.add(hostName, 1, 0);
        grid.add(new Label("Port:"), 0, 1);
        grid.add(port, 1, 1);

        Node connectButton = dialog.getDialogPane().lookupButton(connectButtonType);
        connectButton.setDisable(false);

        // Do some validation (using the Java 8 lambda syntax).
//        hostName.textProperty().addListener((observable, oldValue, newValue) -> {
//            connectButton.setDisable(newValue.trim().isEmpty());
//        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(hostName::requestFocus);

// Convert the result to a hostname-port-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == connectButtonType) {
                return new Pair<>(hostName.getText(), Integer.parseInt(port.getText()));
            }
            return null;
        });

        return dialog.showAndWait();
    }
}
