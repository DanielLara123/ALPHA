package com.example.proyectoalpha.controller.Administrador;

import com.example.proyectoalpha.clases.Usuario;
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
import java.util.Optional;

public class EliminarUsuarioController {

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnVolver;

    @FXML
    private ChoiceBox<String> ChoiceBoxCorreoAtleta;

    @FXML
    private Label LblMensaje;

    private Usuario usuario;
    MariaDBController mariaDBController = new MariaDBController();

    public void setUsuario(Usuario usuario) {
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

    @FXML
    public void initialize() {
        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/GestionUsuarios.fxml"));
            Parent root = loader.load();

            GestionUsuariosController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarContinuar() {
        String correoSeleccionado = ChoiceBoxCorreoAtleta.getValue();
        if (correoSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Estás seguro de que deseas eliminar al usuario con correo: " + correoSeleccionado + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean exito = mariaDBController.eliminarUsuarioPorCorreo(correoSeleccionado);
                if (exito) {
                    Alert exitoAlert = new Alert(Alert.AlertType.INFORMATION);
                    exitoAlert.setTitle("Éxito");
                    exitoAlert.setHeaderText(null);
                    exitoAlert.setContentText("Usuario eliminado correctamente");
                    exitoAlert.showAndWait();
                    cargarUsuarios(); // Recargar la lista de usuarios
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Error al eliminar el usuario");
                    errorAlert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecciona un usuario para eliminar.");
            alert.showAndWait();
        }
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }

}
