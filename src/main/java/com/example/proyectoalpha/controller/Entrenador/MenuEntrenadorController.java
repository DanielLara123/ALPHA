package com.example.proyectoalpha.controller.Entrenador;


import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.Chat.ElegirDestinatarioController;
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

    private Usuario usuario;

    @FXML
    void initialize() {
        BtnPlanes.setOnAction(event -> manejarPlanes());
        BtnCerrarSesion.setOnAction(event -> manejarCerrarSesion());
        colocarImagenBotones();
        BtnChat.setOnAction(event -> manejarChat());
    }

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    void manejarPlanes(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/OpcionesRutinas.fxml"));
            Parent root = loader.load();

            OpcionesRutinasController controller = loader.getController();
            controller.setDatosUsuario(usuario);


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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Chat/ElegirDestinatario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnChat.getScene().getWindow();

            ElegirDestinatarioController controller = loader.getController();
            controller.setDatosUsuario(usuario);

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



}

