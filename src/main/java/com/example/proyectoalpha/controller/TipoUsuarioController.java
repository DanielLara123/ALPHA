package com.example.proyectoalpha.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.proyectoalpha.controller.RegistroInicioSesion.InicioSesionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TipoUsuarioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnAdministrador;

    @FXML
    private Button BtnAtleta;

    @FXML
    private Button BtnEntrenador;

    @FXML
    private Button BtnMedico;

    @FXML
    void initialize() {
        BtnAdministrador.setOnAction(event -> loadInicioSesion("Administrador"));
        BtnAtleta.setOnAction(event -> loadInicioSesion("Atleta"));
        BtnEntrenador.setOnAction(event -> loadInicioSesion("Entrenador"));
        BtnMedico.setOnAction(event -> loadInicioSesion("Medico"));
    }

    private void loadInicioSesion(String tipoUsuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/RegistroInicioSesion/InicioSesion.fxml"));
            Parent root = loader.load();

            InicioSesionController inicioSesionController = loader.getController();
            inicioSesionController.setTipoUsuario(tipoUsuario);

            Stage stage = (Stage) BtnAdministrador.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void colocarImagenBotones(){
        URL linkNuevo = getClass().getResource("/com/example/proyectoalpha/ImagenBotones.fxml");
    }
}