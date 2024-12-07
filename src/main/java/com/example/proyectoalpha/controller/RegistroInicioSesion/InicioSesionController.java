package com.example.proyectoalpha.controller.RegistroInicioSesion;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.Administrador.MenuAdminController;
import com.example.proyectoalpha.controller.Atleta.MenuAtletaController;
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

    private servicioUsuario servicioUsuario;
    private String tipoUsuario;

    @FXML
    void initialize() {
        servicioUsuario = new servicioUsuario();

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


        Usuario usuario = servicioUsuario.loginUsuario(correo, contrasena);
        if (usuario != null) {
            if (usuario.getTipoUsuario().equalsIgnoreCase(tipoUsuario)) {
                if (tipoUsuario.equalsIgnoreCase("atleta")) {
                    MenuAtleta(usuario);
                } else if (tipoUsuario.equalsIgnoreCase("medico")) {
                    MenuMedico();
                } else if (tipoUsuario.equalsIgnoreCase("administrador")) {
                    MenuAdministrador();
                } else {
                    MenuEntrenador();
                }
            } else {
                LblMessage.setText("Tipo de usuario incorrecto");
            }
        } else {
            LblMessage.setText("Correo o contraseña incorrectos");
        }
    }


    private void manejarNoTienesCuenta() {

        if (!"Atleta".equals(tipoUsuario)) {
            LblMessage.setText("Solo los atletas pueden registrarse");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/RegistroInicioSesion/Registro.fxml"));
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

    private void MenuAtleta(Usuario usuario){
        try {
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MenuAtleta.fxml"));
            Parent root = loader2.load();

            MenuAtletaController controller = loader2.getController();
            controller.setDatosUsuario(usuario.getDni(), usuario.getCorreo(), usuario.getContrasena());

            Stage stage = (Stage) BtnContinuar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void MenuMedico(){
        try {
            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Medico/MenuMedico.fxml"));
            Parent root2 = loader3.load();

            Stage stage = (Stage) BtnContinuar.getScene().getWindow();
            stage.setScene(new Scene(root2));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void MenuAdministrador(){
        try {
            FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/MenuAdministrador.fxml"));
            Parent root3 = loader4.load();

            Stage stage = (Stage) BtnContinuar.getScene().getWindow();
            stage.setScene(new Scene(root3));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void MenuEntrenador(){
        try {
            FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/MenuEntrenador.fxml"));
            Parent root3 = loader4.load();

            Stage stage = (Stage) BtnContinuar.getScene().getWindow();
            stage.setScene(new Scene(root3));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}