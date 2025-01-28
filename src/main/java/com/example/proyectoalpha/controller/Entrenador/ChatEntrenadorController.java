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

public class ChatEntrenadorController {

    @FXML
    private Button BtnVolver;

    private String correoEntrenador;

    @FXML
    void initialize(){
        colocarImagenBotones();
        BtnVolver.setOnAction(e -> manejarVolver());
    }

    void manejarVolver(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/MenuEntrenador.fxml"));
            Parent root = loader.load();

            MenuEntrenadorController controller = loader.getController();
            controller.setCorreoEntrenador(correoEntrenador);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setCorreoEntrenador(String correoEntrenador){
        this.correoEntrenador = correoEntrenador;
    }

    private void colocarImagenBotones(){
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);


        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }
}