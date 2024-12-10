package com.example.proyectoalpha.controller.Administrador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ControlAfluenciaController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnVolver;

    @FXML
    private Label LblAforoActual;

    @FXML
    private Label LblHora;

    @FXML
    private Label LblPorcentajeAforo;

    @FXML
    void initialize() {
        BtnVolver.setOnAction(event -> manejarVolver());
        actualizarAforo();
        colocarImagenBotones();
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/MenuAdministrador.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void actualizarAforo() {
        // Lógica para obtener el aforo actual y la capacidad máxima
        int aforoActual = obtenerAforoActual();
        int capacidadMaxima = obtenerCapacidadMaxima();
        int porcentajeAforo = (aforoActual * 100) / capacidadMaxima;

        LblAforoActual.setText("Aforo Actual: " + aforoActual);
        LblPorcentajeAforo.setText("Aforo máximo = " + obtenerCapacidadMaxima() + ". Porcentaje de Aforo: " + porcentajeAforo + "%");
        LblHora.setText("Hora Actual: " + LocalTime.now().format((DateTimeFormatter.ofPattern("HH:mm:ss"))));
    }

    private int obtenerAforoActual() {
        // De momento, devolvemos un valor fijo
        return 50;
    }

    private int obtenerCapacidadMaxima() {
        // De momento, devolvemos un valor fijo
        return 100;
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        if (volver != null) {
            Image imagenVolver = new Image(volver.toString(), 50, 50, false, true);
            BtnVolver.setGraphic(new ImageView(imagenVolver));
        } else {
            System.out.println("No se encontró la imagen");
        }
    }
}