package com.example.proyectoalpha.controller.Chat;

import com.example.proyectoalpha.clases.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ElegirDestinatarioController {

    @FXML
    private Button BtnVolver;

    @FXML
    private Button BtnAbrir;

    @FXML
    private ChoiceBox<?> ChoiceDestinatario;

    private Usuario usuario;

    private void inicializarChat() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Chat/Chat.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del chat
            ClienteController chatController = loader.getController();
            chatController.inicializarCliente("localhost", 12345); // Conectar cliente al servidor
//          chatController.setUsuario(usuario1, usuario2);
            Stage stage = (Stage) BtnAbrir.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
