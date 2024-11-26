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
        BtnConfiguracion.setOnAction(event -> manejarConfiguracion());
        BtnVolver.setOnAction(event -> manejarCerrarSesion());
        BtnNotificaciones.setOnAction(event -> manejarNotificaciones());
        BtnEjercicios.setOnAction(event -> manejarEjercicios());
        BtnLogros.setOnAction(event -> manejarLogros());
        BtnRutinas.setOnAction(event -> manejarRutinas());
        BtnMisDatos.setOnAction(event -> manejarMisDatos());
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

    private void manejarConfiguracion(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/ConfiguracionUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnConfiguracion.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarRutinas(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/RutinasUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnRutinas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarNotificaciones(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/NotificacionesUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnNotificaciones.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarMisDatos(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MisDatosUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnMisDatos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarLogros(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/LogrosUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnLogros.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarEjercicios(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/EjerciciosUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnEjercicios.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarCerrarSesion(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/TipoUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
