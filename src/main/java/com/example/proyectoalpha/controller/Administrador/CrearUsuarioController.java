package com.example.proyectoalpha.controller.Administrador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.ConfirmacionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.example.proyectoalpha.servicios.servicioUsuario;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CrearUsuarioController {

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

    // Por implementar
    @FXML
    private ChoiceBox<?> ChoiceBoxGimnasio;

    @FXML
    private Label LblMensaje;

    private servicioUsuario servicioUsuario;

    @FXML
    void initialize() {
        servicioUsuario = new servicioUsuario();

        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    private void manejarContinuar() {
        String correo = FieldCorreo.getText();
        String contrasena = FieldContrasena.getText();
        String dni = FieldDni.getText();
        String tipoUsuario = FieldRol.getValue();

        if (correo.isEmpty() || contrasena.isEmpty() || tipoUsuario.isEmpty()) {
            LblMensaje.setText("Rellena todos los campos antes de continuar");
        } else if (servicioUsuario.emailEstaRegistrado(correo)) {
            LblMensaje.setText("El correo ya está registrado");
        } else if (!correo.endsWith("@gmail.com")) {
            LblMensaje.setText("El correo debe terminar en @gmail.com");
        } else if (!validarFormatoDni(dni)) {
            LblMensaje.setText("El formato del DNI es incorrecto");
        } else if (servicioUsuario.dniEstaRegistrado(dni)) {
            LblMensaje.setText("El DNI ya está registrado");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Confirmacion.fxml"));
                Parent root = loader.load();
                ConfirmacionController confirmacionController = loader.getController();
                confirmacionController.setMensaje("¿Estás seguro de que desea crear el usuario?");
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();

                if (confirmacionController.estaConfirmado()) {
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setCorreo(correo);
                    nuevoUsuario.setContrasena(contrasena);
                    nuevoUsuario.setDni(dni);
                    nuevoUsuario.setTipoUsuario(tipoUsuario);

                    servicioUsuario.registrarUsuario(nuevoUsuario);
                    LblMensaje.setText("Usuario creado correctamente");
                } else {
                    LblMensaje.setText("Creación de usuario cancelada");
                }
            } catch (IOException e) {
                e.printStackTrace();
                LblMensaje.setText("Error al mostrar la confirmación");
            }
        }
    }

    private boolean validarFormatoDni(String dni) {
        // Ejemplo de DNI: 12345678Z
        String dniPattern = "^[0-9]{8}[A-Z]$";
        return Pattern.matches(dniPattern, dni);
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/GestionUsuarios.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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