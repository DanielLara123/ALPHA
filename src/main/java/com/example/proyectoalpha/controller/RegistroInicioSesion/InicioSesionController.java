package com.example.proyectoalpha.controller.RegistroInicioSesion;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.servicios.MariaDBController;
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

public class InicioSesionController {

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnNoTienesCuenta;

    @FXML
    private PasswordField LblContrasena;

    @FXML
    private TextField LblCorreo;

    @FXML
    private Label LblMessage;

    @FXML
    private Button BtnVolver;

    private MariaDBController mariaDBController;
    private String tipoUsuario;

    @FXML
    void initialize() {
        mariaDBController = new MariaDBController();

        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnNoTienesCuenta.setOnAction(event -> manejarNoTienesCuenta());
        BtnVolver.setOnAction(event -> manejarVolver());
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    private void manejarContinuar() {
        String correo = LblCorreo.getText();
        String contrasena = LblContrasena.getText();

        Usuario usuario = mariaDBController.loginUsuario(correo, contrasena);
        if (usuario != null) {
            if (usuario.getTipoUsuario().equalsIgnoreCase(tipoUsuario)) {
                switch (tipoUsuario.toLowerCase()) {
                    case "atleta":
                        MenuAtleta(usuario);
                        break;
                    case "medico":
                        MenuMedico(usuario);
                        break;
                    case "administrador":
                        MenuAdministrador(usuario);
                        break;
                    default:
                        MenuEntrenador(usuario);
                        break;
                }
            } else {
                LblMessage.setText("Tipo de usuario incorrecto");
            }
        } else {
            LblMessage.setText("Correo o contraseña incorrectos");
        }
    }

    private void manejarNoTienesCuenta() {
        if (!"Atleta".equalsIgnoreCase(tipoUsuario)) {
            LblMessage.setText("Solo los atletas pueden registrarse");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/RegistroInicioSesion/Registro.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) BtnNoTienesCuenta.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    private void MenuAtleta(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MenuAtleta.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) BtnContinuar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void MenuMedico(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Medico/MenuMedico.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) BtnContinuar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void MenuAdministrador(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/MenuAdministrador.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) BtnContinuar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void MenuEntrenador(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/MenuEntrenador.fxml"));
            Parent root = loader.load();



            Stage stage = (Stage) BtnContinuar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}