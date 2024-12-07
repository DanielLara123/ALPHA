package com.example.proyectoalpha.controller.Administrador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.example.proyectoalpha.controller.ConfirmacionController;
import com.example.proyectoalpha.servicios.servicioUsuario;

public class NuevosDatosUsuarioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnVolver;

    @FXML
    private TextField FieldContrasena;

    @FXML
    private TextField FieldCorreo;

    @FXML
    private TextField FieldDni;

    @FXML
    private ChoiceBox<String> FieldRol;

    @FXML
    private Label LblMensaje;

    private boolean datosConfirmados = false;
    private String correo;
    private servicioUsuario servicioUsuario;

    @FXML
    void initialize() {
        servicioUsuario = new servicioUsuario();

        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isDatosConfirmados() {
        return datosConfirmados;
    }

    private void manejarContinuar() {
        String nuevoCorreo = FieldCorreo.getText();
        String nuevaContrasena = FieldContrasena.getText();

        if (nuevoCorreo.isEmpty() || nuevaContrasena.isEmpty()) {
            LblMensaje.setText("Los campos correo y contraseña son obligatorios");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Confirmacion.fxml"));
            Parent root = loader.load();
            ConfirmacionController confirmacionController = loader.getController();
            confirmacionController.setMensaje("¿Está seguro de que quiere actualizar el usuario?");
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (confirmacionController.estaConfirmado()) {
                servicioUsuario.actualizarUsuario(correo, nuevoCorreo, nuevaContrasena);
                datosConfirmados = true;
                LblMensaje.setText("Usuario actualizado correctamente");
            } else {
                datosConfirmados = false;
                LblMensaje.setText("Actualización de usuario cancelada");
            }
            closeWindow();
        } catch (IOException e) {
            e.printStackTrace();
            LblMensaje.setText("Error al mostrar la confirmación");
        }
    }

    private void manejarVolver() {
        datosConfirmados = false;
        closeWindow();
    }

    private void closeWindow() {
        BtnVolver.getScene().getWindow().hide();
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }
}