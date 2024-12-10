package com.example.proyectoalpha.controller.Atleta;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ConfiguracionUsuarioController {

    @FXML
    private Button BtnCambiarDatosPersonales;

    @FXML
    private Button BtnDarseDeBaja;

    @FXML
    private Button BtnVolver;

    @FXML
    private Label LblCambiarDatosPersonales;

    @FXML
    private Label LblDarseDeBaja;

    @FXML
    private void initialize(){
        BtnCambiarDatosPersonales.setOnAction(event -> manejarCambiarDatosPersonales());
        BtnVolver.setOnAction(event -> manejarCerrarSesion());
        BtnDarseDeBaja.setOnAction(event -> manejarDarseDeBaja());
        colocarImagenBotones();
    }

    private void colocarImagenBotones(){
        URL cambiardatospersonales = getClass().getResource("/images/CambiarDatosPersonales.png");
        URL darsedebaja = getClass().getResource("/images/DarseDeBaja.png");
        URL volver = getClass().getResource("/images/VolverAtras.png");


        Image imagenCambiarDatosPersonales = new Image(String.valueOf(cambiardatospersonales), 300, 50, false, true);
        Image imagenDarseDeBaja = new Image(String.valueOf(darsedebaja), 200, 50, false, true);
        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnCambiarDatosPersonales.setGraphic(new ImageView(imagenCambiarDatosPersonales));
        BtnDarseDeBaja.setGraphic(new ImageView(imagenDarseDeBaja));
        BtnVolver.setGraphic(new ImageView(imagenVolver));

    }

    private void manejarCambiarDatosPersonales(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/CambiarDatosPersonales.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnCambiarDatosPersonales.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarCerrarSesion(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarDarseDeBaja(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/DarseDeBaja.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnDarseDeBaja.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
