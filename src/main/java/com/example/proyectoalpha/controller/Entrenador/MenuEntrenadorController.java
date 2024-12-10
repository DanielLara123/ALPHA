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

public class MenuEntrenadorController {

    @FXML
    private Button BtnAlertas;

    @FXML
    private Button BtnCerrarSesion;

    @FXML
    private Button BtnMonitoreo;

    @FXML
    private Button BtnPlanes;

    @FXML
    void initialize() {
        BtnPlanes.setOnAction(event -> manejarPlanes());
        BtnCerrarSesion.setOnAction(event -> manejarCerrarSesion());
        colocarImagenBotones();
    }

    void manejarPlanes(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/OpcionesRutinas.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnPlanes.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void manejarCerrarSesion(){
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

    private void colocarImagenBotones(){
        URL PlanesDeEntrenamiento = getClass().getResource("/images/PlanesDeEntrenamiento.png");
        URL MonitoreoDeClientes = getClass().getResource("/images/MonitoreoDeClientes.png");
        URL AlertasDeSalud = getClass().getResource("/images/AlertaDeSalud.png");



        Image imagenPlanesDeEntrenamiento = new Image(String.valueOf(PlanesDeEntrenamiento), 200, 200, false, true);
        Image imagenMonitoreoDeClientes = new Image(String.valueOf(MonitoreoDeClientes), 200, 200, false, true);
        Image imagenAlertaDeSalud = new Image(String.valueOf(AlertasDeSalud), 200, 200, false, true);


        BtnPlanes.setGraphic(new ImageView(imagenPlanesDeEntrenamiento));
        BtnMonitoreo.setGraphic(new ImageView(imagenMonitoreoDeClientes));
        BtnAlertas.setGraphic(new ImageView(imagenAlertaDeSalud));

    }
}
