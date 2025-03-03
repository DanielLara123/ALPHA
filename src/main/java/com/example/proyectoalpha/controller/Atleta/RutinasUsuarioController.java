package com.example.proyectoalpha.controller.Atleta;
/*
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
/*
public class RutinasUsuarioController {

    @FXML
    private Button BtnCrearRutina;

    @FXML
    private Button BtnMisRutinas;

    @FXML
    private Button BtnVolver;

    private String dniUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;

    @FXML
    public void initialize() {
        BtnMisRutinas.setOnAction(event -> mostrarRutinas());
        BtnCrearRutina.setOnAction(event -> crearRutina());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    public void setDatosUsuario(String dni, String correo, String contrasena) {
        dniUsuario = dni;
        correoUsuario = correo;
        contrasenaUsuario = contrasena;
    }

    private void mostrarRutinas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MostrarRutinas.fxml"));
            Parent root = loader.load();

            MostrarRutinasController controller = loader.getController();
            controller.setDatosUsuario(dniUsuario, correoUsuario, contrasenaUsuario);

            Stage stage = (Stage) BtnMisRutinas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crearRutina() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/CrearRutinas.fxml"));
            Parent root = loader.load();

            CrearRutinasController controller = loader.getController();
            controller.setDatosUsuario(dniUsuario, correoUsuario, contrasenaUsuario);

            Stage stage = (Stage) BtnCrearRutina.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MenuAtleta.fxml"));
            Parent root = loader.load();

            MenuAtletaController controller = loader.getController();
            controller.setDatosUsuario(dniUsuario, correoUsuario, contrasenaUsuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void colocarImagenBotones(){
        URL crearRutina = getClass().getResource("/images/FotoAñadirUsuario.png");
        URL misRutinas = getClass().getResource("/images/PlanesDeEntrenamiento.png");
        URL volver = getClass().getResource("/images/VolverAtras.png");


        Image imagenCrear = new Image(String.valueOf(crearRutina), 200, 200, false, true);
        Image imagenRutinas = new Image(String.valueOf(misRutinas), 200, 200, false, true);
        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnCrearRutina.setGraphic(new ImageView(imagenCrear));
        BtnMisRutinas.setGraphic(new ImageView(imagenRutinas));
        BtnVolver.setGraphic(new ImageView(imagenVolver));

    }
}

 */