package com.example.proyectoalpha.controller.RegistroInicioSesion;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroController {

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnVolver;

    @FXML
    private Button BtnYaTienesCuenta;

    @FXML
    private TextField TextFieldApellidos;

    @FXML
    private PasswordField TextFieldContrasena;

    @FXML
    private TextField TextFieldCorreo;

    @FXML
    private TextField TextFieldDNI;

    @FXML
    private TextField TextFieldGimnasio;

    @FXML
    private TextField TextFieldNombre;

    @FXML
    private PasswordField TextFieldRepiteContrasena;

    @FXML
    private Label LblMessage;

    private MariaDBController mariaDBController;
    private String tipoUsuario;

    @FXML
    void initialize() {
        mariaDBController = new MariaDBController();

        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnYaTienesCuenta.setOnAction(event -> manejarYaTienesCuenta());
        BtnVolver.setOnAction(event -> manejarVolver());
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    private void manejarContinuar() {
        String nombre = TextFieldNombre.getText();
        String apellidos = TextFieldApellidos.getText();
        String dni = TextFieldDNI.getText();
        String correo = TextFieldCorreo.getText();
        String contrasena = TextFieldContrasena.getText();
        String repiteContrasena = TextFieldRepiteContrasena.getText();
        String gimnasio = TextFieldGimnasio.getText();

        if (nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || repiteContrasena.isEmpty() || gimnasio.isEmpty()) {
            LblMessage.setText("Necesitas llenar todos los campos");
            return;
        }

        if (!correo.endsWith("@gmail.com")) {
            LblMessage.setText("El correo debe terminar en @gmail.com");
            return;
        }

        if (mariaDBController.emailEstaRegistrado(correo)) {
            LblMessage.setText("El correo ya está registrado");
            return;
        }

        if (!"Atleta".equalsIgnoreCase(tipoUsuario)) {
            LblMessage.setText("Solo los atletas pueden registrarse");
            return;
        }

        if (contrasena.equals(repiteContrasena)) {
            Usuario usuario = new Usuario(nombre, apellidos, contrasena, dni, correo, tipoUsuario, gimnasio);
            boolean registrado = mariaDBController.registrarUsuario(usuario);
            if (registrado) {
                LblMessage.setText("Usuario registrado correctamente");
                // Show success alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro Exitoso");
                alert.setHeaderText(null);
                alert.setContentText("Usuario registrado correctamente");
                alert.showAndWait();
            } else {
                LblMessage.setText("Error al registrar el usuario");
                // Show error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de Registro");
                alert.setHeaderText(null);
                alert.setContentText("Error al registrar el usuario");
                alert.showAndWait();
            }
        } else {
            LblMessage.setText("Las contraseñas no coinciden");
        }
    }

    private void manejarYaTienesCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/RegistroInicioSesion/InicioSesion.fxml"));
            Parent root = loader.load();
            InicioSesionController inicioSesionController = loader.getController();
            inicioSesionController.setTipoUsuario(tipoUsuario);
            Stage stage = (Stage) BtnYaTienesCuenta.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/TipoUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}