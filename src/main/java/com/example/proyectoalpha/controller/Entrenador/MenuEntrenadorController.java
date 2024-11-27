package com.example.proyectoalpha.controller.Entrenador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        colocarImagenBotones();
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
