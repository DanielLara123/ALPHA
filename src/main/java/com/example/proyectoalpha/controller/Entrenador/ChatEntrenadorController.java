package com.example.proyectoalpha.controller.Entrenador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class ChatEntrenadorController {

    @FXML
    private Button BtnVolver;

    @FXML
    void initialize(){
        colocarImagenBotones();
    }

    private void colocarImagenBotones(){
        URL volver = getClass().getResource("/images/VolverAtras.png");



        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);


        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }
}
