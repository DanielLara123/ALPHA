package com.example.proyectoalpha.controller.Chat;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class ClienteController extends Application {
    @FXML private VBox messageContainer;
    @FXML private TextField messageField;
    @FXML private Button sendButton;
    @FXML private ScrollPane scrollPane;
    @FXML private Button BtnVolver;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private String chatPartner;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        loader.setController(this);
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Chat");
        primaryStage.show();

        requestUserInfo(); // Pedir nombre de usuario
    }

    private void requestUserInfo() {
        Platform.runLater(() -> {
            username = "Usuario1"; // Simulación de ingreso de usuario
            chatPartner = "Usuario2"; // Simulación del otro usuario
            connectToServer();
        });
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 12345);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println(username);
                out.println(chatPartner);

                String message;
                while ((message = in.readLine()) != null) {
                    addMessage(message, false); // Mensaje recibido
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            out.println(message);
            addMessage(username + ": " + message, true); // Mensaje enviado
            messageField.clear();
        }
    }

    private void addMessage(String message, boolean isSent) {
        Platform.runLater(() -> {
            HBox messageBox = new HBox();
            TextFlow textFlow = new TextFlow(new Text(message));
            textFlow.setStyle("-fx-background-color: " + (isSent ? "#0078D7" : "#E5E5E5") + "; -fx-padding: 10px; -fx-background-radius: 10px;");

            if (isSent) {
                messageBox.setStyle("-fx-alignment: center-right; -fx-padding: 5px;");
            } else {
                messageBox.setStyle("-fx-alignment: center-left; -fx-padding: 5px;");
            }

            messageBox.getChildren().add(textFlow);
            messageContainer.getChildren().add(messageBox);

            // Auto-scroll hacia abajo
            scrollPane.vvalueProperty().bind(messageContainer.heightProperty());
        });
    }
}