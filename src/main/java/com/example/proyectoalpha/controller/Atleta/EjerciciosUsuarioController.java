package com.example.proyectoalpha.controller.Atleta;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class EjerciciosUsuarioController {

    @FXML
    private TextField TxtBusqueda;

    @FXML
    private Button BtnVolver;

    @FXML
    private ImageView ImgAlpha;

    @FXML
    private Label LblEjercicio2;

    @FXML
    private Label LblEjercicio5;

    @FXML
    private Label LblEjercicio3;

    @FXML
    private Label LblEjercicio4;

    @FXML
    private Label LblEjercicio1;

    @FXML
    private Button BtnBuscar;

    @FXML
    void initialize(){
        colocarImagenBotones();
    }

    void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }
}
