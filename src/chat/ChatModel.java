package chat;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatModel {

    Socket socket;
    PrintWriter writer;
    BufferedReader reader;
    ExecutorService threadPool;

    public ChatModel() {
        threadPool = Executors.newFixedThreadPool(2);

        //Connect to server
        try {
            socket = new Socket("82.196.127.10", 8000);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //Start listening for messages
            threadPool.submit(this::recieveMessages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recieveMessages() {
        while (true) {
            try {
                String message = reader.readLine();
                Platform.runLater(() ->
                        chatMessages.add(message)
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
        if (message.get().length() > 0) {
            //Send message to server
            final String mess = message.get();
            threadPool.submit(() -> writer.println(mess));
            message.setValue("");
        }
    }
}
