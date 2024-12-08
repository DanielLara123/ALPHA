package com.example.proyectoalpha.controller.Entrenador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class OpcionesRutinasController {

    @FXML
    private Button BtnVolver;

    @FXML
    private Button BtnListaRutinas;

    @FXML
    private Button BtnCrearRutinas;

    @FXML
    private void initialize(){
        BtnVolver.setOnAction(e -> manejarVolver());
        BtnCrearRutinas.setOnAction(e -> manejarCrearRutinas());
        BtnListaRutinas.setOnAction(e -> manejarListaRutinas());
        colocarImagenBotones();
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/MenuEntrenador.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void manejarCrearRutinas(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/CrearRutinas.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnCrearRutinas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void manejarListaRutinas(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/ListaRutinas.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnListaRutinas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void colocarImagenBotones(){
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));

    }
}
