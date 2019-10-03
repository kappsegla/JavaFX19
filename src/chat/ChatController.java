package chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
        System.out.println("test");
        model.sendMessage();
    }
}
