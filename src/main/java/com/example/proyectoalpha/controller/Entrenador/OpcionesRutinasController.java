package com.example.proyectoalpha.controller.Entrenador;

import com.example.proyectoalpha.controller.Atleta.MenuAtletaController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class OpcionesRutinasController {

    @FXML
    private Button BtnCrearEjercicio;

    @FXML
    private Button BtnCrearRutinas;

    @FXML
    private Button BtnVolver;

    private String correoEntrenador;

    public void setCorreoEntrenador(String correoEntrenador){
        this.correoEntrenador = correoEntrenador;
    }

    @FXML
    private void initialize() {
        colocarImagenBotones();
        BtnCrearEjercicio.setOnAction(event -> crearEjercicio());
        BtnCrearRutinas.setOnAction(event -> crearRutina());
        BtnVolver.setOnAction(event -> volver());
    }

    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/MenuEntrenador.fxml"));
            Parent root = loader.load();

            MenuEntrenadorController controller = loader.getController();
            controller.setCorreoEntrenador(correoEntrenador);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crearRutina() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/CrearRutinas.fxml"));
            Parent root = loader.load();

            CrearRutinasController controller = loader.getController();
            controller.setCorreoEntrenador(correoEntrenador);

            Stage stage = (Stage) BtnCrearRutinas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void crearEjercicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/CrearEjercicios.fxml"));
            Parent root = loader.load();

            CrearEjerciciosController controller = loader.getController();
            controller.setCorreoEntrenador(correoEntrenador);

            Stage stage = (Stage) BtnCrearEjercicio.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void colocarImagenBotones(){
        URL volver = getClass().getResource("/images/VolverAtras.png");
        URL crearRutina = getClass().getResource("/images/FotoAñadirUsuario.png");
        URL crearEjercicio = getClass().getResource("/images/CambiarDatosPersonales.png");

        javafx.scene.image.Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);
        javafx.scene.image.Image imagenCrearRutina = new Image(String.valueOf(crearRutina), 200, 200, false, true);
        javafx.scene.image.Image imagenCrearEjercicio = new Image(String.valueOf(crearEjercicio), 200, 200, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
        BtnCrearRutinas.setGraphic(new ImageView(imagenCrearRutina));
        BtnCrearEjercicio.setGraphic(new ImageView(imagenCrearEjercicio));
    }
}






