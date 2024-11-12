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

public class InicioSesionController {

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnNoTienesCuenta;

    @FXML
    private PasswordField LblContrasena;

    @FXML
    private TextField LblCorreo;

    private servicioUsuario servicioUsuario;
    private String tipoUsuario;

    @FXML
    void initialize() {
        servicioUsuario = new servicioUsuario();

        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnNoTienesCuenta.setOnAction(event -> manejarNoTienesCuenta());
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    private void manejarContinuar() {
        String correo = LblCorreo.getText();
        String contrasena = LblContrasena.getText();

        Usuario usuario = servicioUsuario.loginUsuario(correo, contrasena);
        if (usuario != null) {
            System.out.println("Login successful");
            // Proceed to the next view
        } else {
            System.out.println("Invalid email or password");
        }
    }


    private void manejarNoTienesCuenta() {

        if (!"Atleta".equals(tipoUsuario)) {
            System.out.println("Only Atleta type users can register");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Registro.fxml"));
            Parent root = loader.load();

            RegistroController registroController = loader.getController();
            registroController.setTipoUsuario(tipoUsuario);

            Stage stage = (Stage) BtnNoTienesCuenta.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void menuAtleta(Usuario usuario) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/MenuAtleta.fxml"));
            Parent root = loader.load();

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

}