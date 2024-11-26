package com.example.proyectoalpha.controller.Atleta;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class MenuAtletaController {

    @FXML
    private Button BtnMisDatos;

    @FXML
    private Button BtnNotificaciones;

    @FXML
    private Button BtnEjercicios;

    @FXML
    private Button BtnLogros;

    @FXML
    private Button BtnRutinas;

    @FXML
    private Button BtnConfiguracion;

    @FXML
    private Button BtnVolver;

    @FXML
    private Label LblMisDatos;

    @FXML
    private Label LblNotificaciones;

    @FXML
    private Label LblConfiguracion;

    @FXML
    private Label LblEjercicios;

    @FXML
    private Label LblLogros;

    @FXML
    private Label LblRutinas;

@FXML
    private void initialize(){
        colocarImagenBotones();
    }

    private void colocarImagenBotones(){
        URL misDatos = getClass().getResource("/images/MisDatosAtleta.png");
        URL configuracion = getClass().getResource("/images/ConfiguracionAtleta.png");
        URL ejercicios = getClass().getResource("/images/EjerciciosAtleta.png");
        URL logros = getClass().getResource("/images/LogrosAtleta.png");
        URL rutinas = getClass().getResource("/images/RutinasAtleta.png");
        URL notificaciones = getClass().getResource("/images/NotificacionesAtleta.png");
        URL volver = getClass().getResource("/images/VolverAtras.png");


        Image imagenDatos = new Image(String.valueOf(misDatos), 200, 200, false, true);
        Image imagenConfiguracion = new Image(String.valueOf(configuracion), 200, 200, false, true);
        Image imagenEjercicios = new Image(String.valueOf(ejercicios), 200, 200, false, true);
        Image imagenLogros = new Image(String.valueOf(logros), 200, 200, false, true);
        Image imagenRutinas = new Image(String.valueOf(rutinas), 200, 200, false, true);
        Image imagenNotificaciones = new Image(String.valueOf(notificaciones), 200, 200, false, true);
        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnMisDatos.setGraphic(new ImageView(imagenDatos));
        BtnConfiguracion.setGraphic(new ImageView(imagenConfiguracion));
        BtnEjercicios.setGraphic(new ImageView(imagenEjercicios));
        BtnLogros.setGraphic(new ImageView(imagenLogros));
        BtnRutinas.setGraphic(new ImageView(imagenRutinas));
        BtnNotificaciones.setGraphic(new ImageView(imagenNotificaciones));
        BtnVolver.setGraphic(new ImageView(imagenVolver));

    }
}
