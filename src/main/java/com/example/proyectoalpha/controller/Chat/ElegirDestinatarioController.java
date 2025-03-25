package com.example.proyectoalpha.controller.Chat;

import com.example.proyectoalpha.servicios.*;
import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.Atleta.MenuAtletaController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ElegirDestinatarioController {

    @FXML
    private Button BtnVolver;

    @FXML
    private Button BtnAbrir;

    @FXML
    private ChoiceBox<Usuario> ChoiceDestinatario;

    private Usuario usuario;
    private MariaDBController maria;

    public ElegirDestinatarioController() {
        this.maria = new MariaDBController(); // Initialize the MariaDBController
    }

    @FXML
    private void initialize() {
        BtnVolver.setOnAction(e -> manejarVolver());
        BtnAbrir.setOnAction(e -> inicializarChat());
        colocarImagenBotones();
        cargarChoiceBox();
    }

    private void cargarChoiceBox() {
        List<Usuario> usuarios = maria.obtenerUsuarios();
        ChoiceDestinatario.getItems().addAll(usuarios);
    }

    private void inicializarChat() {
        Usuario usuario2 = ChoiceDestinatario.getValue();
        if (usuario2 == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Chat/Chat.fxml"));
            Parent root = loader.load();

            ClienteController chatController = loader.getController();
            chatController.setUsuario(usuario, usuario2); // Initialize usuario and usuario2
            chatController.inicializarCliente("localhost", 12345);

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

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MenuAtleta.fxml"));
            Parent root = loader.load();

            MenuAtletaController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");
        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);
        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }
}
