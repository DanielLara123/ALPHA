package com.example.proyectoalpha.controller.Administrador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.example.proyectoalpha.controller.ConfirmacionController;
import com.example.proyectoalpha.servicios.servicioUsuario;
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

public class NuevosDatosUsuarioController {

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

    // Por implementar
    @FXML
    private ChoiceBox<?> ChoiceBoxGimnasio;

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
        FieldCorreo.setText(correo);
    }

    public boolean isDatosConfirmados() {
        return datosConfirmados;
    }

    public String getNuevoCorreo() {
        return FieldCorreo.getText().trim();
    }

    private void manejarContinuar() {
        String nuevoCorreo = FieldCorreo.getText().trim();
        String nuevaContrasena = FieldContrasena.getText().trim();
        String nuevoDni = FieldDni.getText().trim();

        if (nuevoCorreo.isEmpty() || nuevaContrasena.isEmpty() || nuevoDni.isEmpty()) {
            LblMensaje.setText("Los campos correo, contraseña y DNI son obligatorios");
        } else if (servicioUsuario.emailEstaRegistrado(nuevoCorreo) && !nuevoCorreo.equals(correo)) {
            LblMensaje.setText("El correo ya está registrado");
        } else if (!nuevoCorreo.endsWith("@gmail.com")) {
            LblMensaje.setText("El correo debe terminar en @gmail.com");
        } else if (!validarFormatoDni(nuevoDni)) {
            LblMensaje.setText("El formato del DNI es incorrecto");
        } else if (servicioUsuario.dniEstaRegistrado(nuevoDni)) {
            LblMensaje.setText("El DNI ya está registrado");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Confirmacion.fxml"));
                Parent root = loader.load();
                ConfirmacionController confirmacionController = loader.getController();
                confirmacionController.setMensaje("¿Está seguro de que quiere actualizar el usuario?");
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();

                if (confirmacionController.estaConfirmado()) {
                    servicioUsuario.actualizarUsuario(correo, nuevoCorreo, nuevaContrasena, nuevoDni);
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
    }

    private boolean validarFormatoDni(String dni) {
        // Pattern for DNI: 8 digits followed by an uppercase letter
        String dniPattern = "^[0-9]{8}[A-Z]$";
        return Pattern.matches(dniPattern, dni);
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

        if (volver != null) {
            Image imagenVolver = new Image(volver.toString(), 50, 50, false, true);
            BtnVolver.setGraphic(new ImageView(imagenVolver));
        } else {
            LblMensaje.setText("Error al cargar la imagen de volver");
        }
    }
}