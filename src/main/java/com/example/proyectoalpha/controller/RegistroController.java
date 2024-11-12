package com.example.proyectoalpha.controller;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.servicios.servicioUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    private servicioUsuario servicioUsuario;
    private String tipoUsuario;

    @FXML
    void initialize() {
        servicioUsuario = new servicioUsuario();

        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnYaTienesCuenta.setOnAction(event -> manejarYaTienesCuenta());
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    private void manejarContinuar() {
        String correo = LblCorreo.getText();
        String contrasena = LblContrasena.getText();
        String repiteContrasena = LblRepiteContrasena.getText();

        if (!"Atleta".equals(tipoUsuario)) {
            System.out.println("Only Atleta type users can register");
            return;
        }

        if (contrasena.equals(repiteContrasena)) {
            Usuario usuario = new Usuario();
            usuario.setCorreo(correo);
            usuario.setContrasena(contrasena);
            usuario.setTipoUsuario(tipoUsuario);
            servicioUsuario.registrarUsuario(usuario);
            System.out.println("User registered successfully");
        } else {
            System.out.println("Passwords do not match");
        }
    }

    private void manejarYaTienesCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/InicioSesion.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnYaTienesCuenta.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}