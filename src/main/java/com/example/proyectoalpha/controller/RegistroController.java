package com.example.proyectoalpha.controller;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.servicios.servicioUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroController {

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnYaTienesCuenta;

    @FXML
    private PasswordField LblContrasena;

    @FXML
    private TextField LblCorreo;

    @FXML
    private PasswordField LblRepiteContrasena;

    @FXML
    private Label LblMessage;

    @FXML
    private Button BtnVolver;

    private servicioUsuario servicioUsuario;
    private String tipoUsuario;

    @FXML
    void initialize() {
        servicioUsuario = new servicioUsuario();

        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnYaTienesCuenta.setOnAction(event -> manejarYaTienesCuenta());
        BtnVolver.setOnAction(event -> manejarVolver());
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    private void manejarContinuar() {
        String correo = LblCorreo.getText();
        String contrasena = LblContrasena.getText();
        String repiteContrasena = LblRepiteContrasena.getText();

        if (correo.isEmpty() || contrasena.isEmpty() || repiteContrasena.isEmpty()) {
            LblMessage.setText("Necesitas llenar todos los campos");
            return;
        }

        if (!correo.endsWith("@gmail.com")) {
            LblMessage.setText("El correo debe terminar en @gmail.com");
            return;
        }

        if (servicioUsuario.emailEstaRegistrado(correo)) {
            LblMessage.setText("El correo ya está registrado");
            return;
        }

        if (!"Atleta".equals(tipoUsuario)) {
            LblMessage.setText("Solo los atletas pueden registrarse");
            return;
        }

        if (contrasena.equals(repiteContrasena)) {
            Usuario usuario = new Usuario();
            usuario.setCorreo(correo);
            usuario.setContrasena(contrasena);
            usuario.setTipoUsuario(tipoUsuario);
            servicioUsuario.registrarUsuario(usuario);
            LblMessage.setText("Usuario registrado correctamente");

            // Load MostrarTexto.fxml and set the message
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/MostrarTexto.fxml"));
                Parent root = loader.load();

                // Get the controller and set the message
                MostrarTextoController mostrarTextoController = loader.getController();
                mostrarTextoController.setMessage("Usuario registrado correctamente");
                mostrarTextoController.setTipoUsuario(tipoUsuario);

                // Show the new scene
                Stage stage = (Stage) BtnContinuar.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LblMessage.setText("Las contraseñas no coinciden");
        }
    }

    private void manejarYaTienesCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/InicioSesion.fxml"));
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
            TipoUsuarioController tipoUsuarioController = loader.getController();
            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}