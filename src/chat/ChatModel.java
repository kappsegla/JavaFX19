package chat;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

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
    }

    //<editor-fold desc="chatMessages">
    private ObservableList<String> chatMessages = FXCollections.observableArrayList();

    public ObservableList<String> getChatMessages() {
        return chatMessages;
    }
    //</editor-fold>

    //<editor-fold desc="connectedProperty">
    private SimpleBooleanProperty connected = new SimpleBooleanProperty(false);

    public boolean isConnected() {
        return connected.get();
    }

    public SimpleBooleanProperty connectedProperty() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected.set(connected);
    }
    //</editor-fold>

    //<editor-fold desc="message">
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
    //</editor-fold>

    public void connect(String host, Integer port) {

        Task<Socket> task = new Task<Socket>() {
            @Override
            protected Socket call() throws Exception {
                return new Socket(host, port);
            }
        };
        task.setOnRunning(event -> chatMessages.add("Connecting..."));
        task.setOnFailed(event -> chatMessages.add("Error connecting"));
        task.setOnSucceeded(event -> chatMessages.add("Connected"));

        task.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                socket = newValue;
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                setConnected(true);
                threadPool.submit(this::receiveMessages);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        threadPool.submit(task);



        //Connect to server
//        try {
//            writer = new PrintWriter(socket.getOutputStream(), true);
//            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            setConnected(true);
//            //Start listening for messages
//            threadPool.submit(this::receiveMessages);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void sendMessage() {
        if (message.get().length() > 0) {
            //Send message to server
            final String mess = message.get();
            threadPool.submit(() -> writer.println(mess));
            message.setValue("");
        }
    }

    private void receiveMessages() {
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
}
