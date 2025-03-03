package com.example.proyectoalpha.controller.Entrenador;

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

public class MenuEntrenadorController {

    @FXML
    private Button BtnCerrarSesion;

    @FXML
    private Button BtnChat;

    @FXML
    private Button BtnPlanes;

    private String correoEntrenador;

    @FXML
    void initialize() {
        BtnPlanes.setOnAction(event -> manejarPlanes());
        BtnCerrarSesion.setOnAction(event -> manejarCerrarSesion());
        colocarImagenBotones();
        BtnChat.setOnAction(event -> manejarChat());
    }

    void manejarPlanes(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/OpcionesRutinas.fxml"));
            Parent root = loader.load();

            OpcionesRutinasController controller = loader.getController();
            controller.setCorreoEntrenador(correoEntrenador);

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

    void manejarChat(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/ChatEntrenador.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnChat.getScene().getWindow();

            ChatEntrenadorController controller = loader.getController();
            controller.setCorreoEntrenador(correoEntrenador);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void colocarImagenBotones(){
        URL PlanesDeEntrenamiento = getClass().getResource("/images/PlanesDeEntrenamiento.png");
        URL Chat = getClass().getResource("/images/FotoChat.png");



        Image imagenPlanesDeEntrenamiento = new Image(String.valueOf(PlanesDeEntrenamiento), 200, 200, false, true);
        Image imagenChat = new Image(String.valueOf(Chat), 200, 200, false, true);


        BtnPlanes.setGraphic(new ImageView(imagenPlanesDeEntrenamiento));
        BtnChat.setGraphic(new ImageView(imagenChat));
    }

    public void setCorreoEntrenador(String text) {
        this.correoEntrenador = text;
    }
}

 */