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