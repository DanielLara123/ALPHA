package com.example.proyectoalpha.controller.Administrador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MenuAdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnCerrarSesion;

    @FXML
    private Button BtnControlAfluencia;

    @FXML
    private Button BtnGestionUsuarios;

    @FXML
    void initialize() {

        BtnCerrarSesion.setOnAction(event -> manejarCerrarSesion());
        BtnGestionUsuarios.setOnAction(event -> manejarGestionUsuarios());
        BtnControlAfluencia.setOnAction(event -> manejarControlAfluencia());

        colocarImagenBotones();
    }


    private void manejarCerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/TipoUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnCerrarSesion.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarGestionUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/GestionUsuarios.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnGestionUsuarios.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void manejarControlAfluencia() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/ControlAfluencia.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnControlAfluencia.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void colocarImagenBotones(){
        URL gestionUsuarios = getClass().getResource("/images/FotoGestionUsuarios.png");
        URL controlAfluencia = getClass().getResource("/images/FotoControlAfluencia.png");

        Image imagenGestionUsuarios = new Image(String.valueOf(gestionUsuarios), 200, 200, false, true);
        Image imagenControlAfluencia = new Image(String.valueOf(controlAfluencia), 200, 200, false, true);

        BtnControlAfluencia.setGraphic(new ImageView(imagenControlAfluencia));
        BtnGestionUsuarios.setGraphic(new ImageView(imagenGestionUsuarios));
    }
}
