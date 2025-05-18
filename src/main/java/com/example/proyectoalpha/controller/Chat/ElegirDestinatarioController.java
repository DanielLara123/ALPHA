package com.example.proyectoalpha.controller.Chat;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.Administrador.MenuAdminController;
import com.example.proyectoalpha.controller.Atleta.MenuAtletaController;
import com.example.proyectoalpha.controller.Entrenador.MenuEntrenadorController;
import com.example.proyectoalpha.controller.Medico.MenuMedicoController;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ElegirDestinatarioController {

    @FXML
    private Button BtnAbrir;

    @FXML
    private Button BtnVolver;

    @FXML
    private TextField BuscarUsuario;

    @FXML
    private ListView<String> UsuariosRecientes;

    private Usuario usuario;

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
        cargarUsuariosRecientes();
    }

    private void cargarUsuariosRecientes() {
        if (usuario != null) {
            MariaDBController mariaDBController = new MariaDBController();
            List<String> correos = mariaDBController.obtenerUsuariosRecientes(usuario.getID());
            UsuariosRecientes.getItems().setAll(correos);
        }
    }

    @FXML
    private void initialize() {
        BtnAbrir.setOnAction(event -> manejarAbrir());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    private void manejarAbrir() {
        String correoSeleccionado = UsuariosRecientes.getSelectionModel().getSelectedItem();
        if (correoSeleccionado == null || correoSeleccionado.isEmpty()) {
            correoSeleccionado = BuscarUsuario.getText();
        }

        if (correoSeleccionado != null && !correoSeleccionado.isEmpty()) {
            MariaDBController mariaDBController = new MariaDBController();
            Usuario usuarioDestino = mariaDBController.obtenerUsuarioPorCorreo(correoSeleccionado);

            if (usuarioDestino != null) {
                abrirChat(usuarioDestino);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Usuario no encontrado.");
                alert.showAndWait();
            }
        }
    }

    private void abrirChat(Usuario usuarioDestino) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Chat/Chat.fxml"));
            Parent root = loader.load();

            ClienteController controller = loader.getController();
            controller.setUsuario(usuario, usuarioDestino);
            controller.inicializarCliente("localhost", ServidorController.getPuerto());

            Stage stage = (Stage) BtnAbrir.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarVolver() {
        try {
            if (usuario.getTipoUsuario().equalsIgnoreCase("Atleta")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MenuAtleta.fxml"));
                Parent root = loader.load();

                MenuAtletaController controller = loader.getController();
                controller.setDatosUsuario(usuario);

                Stage stage = (Stage) BtnVolver.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else if (usuario.getTipoUsuario().equalsIgnoreCase("Entrenador")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/MenuEntrenador.fxml"));
                Parent root = loader.load();

                MenuEntrenadorController controller = loader.getController();
                controller.setDatosUsuario(usuario);

                Stage stage = (Stage) BtnVolver.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else if (usuario.getTipoUsuario().equalsIgnoreCase("Administrador")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/MenuAdministrador.fxml"));
                Parent root = loader.load();

                MenuAdminController controller = loader.getController();
                controller.setDatosUsuario(usuario);

                Stage stage = (Stage) BtnVolver.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Medico/MenuMedico.fxml"));
                Parent root = loader.load();

                MenuMedicoController controller = loader.getController();
                controller.setDatosUsuario(usuario);

                Stage stage = (Stage) BtnVolver.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }

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