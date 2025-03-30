package com.example.proyectoalpha.controller.Administrador;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.Medico.MenuMedicoController;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ActualizarUsuariosController {

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnVolver;

    @FXML
    private ChoiceBox<String> ChoiceBoxCorreoAtleta;

    @FXML
    private Label LblMensaje;

    private Usuario usuario;
    private MariaDBController mariaDBController = new MariaDBController();

    @FXML
    public void initialize() {
        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }


    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        if (usuario != null) {
            List<Usuario> usuarios = mariaDBController.obtenerUsuariosPorGimnasio(usuario.getGimnasio());
            for (Usuario usuario : usuarios) {
                ChoiceBoxCorreoAtleta.getItems().add(usuario.getCorreo());
            }
        }
    }

    private void manejarContinuar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/NuevosDatosUsuario.fxml"));
            Parent root = loader.load();

            NuevosDatosUsuarioController controller = loader.getController();
            controller.setDatosUsuario(usuario);
            controller.setCorreoUsuarioAActualizar(ChoiceBoxCorreoAtleta.getValue());

            Stage stage = (Stage) BtnContinuar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/GestionUsuarios.fxml"));
            Parent root = loader.load();

            GestionUsuariosController controller = loader.getController();
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