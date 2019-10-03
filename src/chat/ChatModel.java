package chat;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class ChatModel {
    private ObservableList<String> chatMessages = FXCollections.observableArrayList();

    public ObservableList<String> getChatMessages() {
        return chatMessages;
    }

    StringProperty message = new SimpleStringProperty("");

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public void sendMessage() {
        if( message.get().length() > 0)
        chatMessages.add(message.get());
        message.setValue("");

       // Platform.runLater();
    }
}
