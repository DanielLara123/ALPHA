package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Atleta.Ejercicio;
import com.example.proyectoalpha.servicios.ServicioEjercicios;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

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

    private ServicioEjercicios servicioEjercicios;

    @FXML
    void initialize(){
        servicioEjercicios = new ServicioEjercicios();
        BtnBuscar.setOnAction(event -> manejarBuscar());
        BtnVolver.setOnAction(event -> manejarVolver());

        colocarImagenBotones();
    }

    void manejarBuscar(){
        String categoria = TxtBusqueda.getText().toLowerCase(); // Convertir a minúsculas para evitar problemas
        List<Ejercicio> ejercicios = servicioEjercicios.obtenerEjercicios(categoria);

        if (!ejercicios.isEmpty()) {
            // Convertir la lista de Ejercicio a una lista de String
            List<String> nombresEjercicios = ejercicios.stream()
                    .map(Ejercicio::getNombre)
                    .toList();
            // Actualizar los labels con los ejercicios encontrados
            actualizarLabels(nombresEjercicios);
        } else {
            // Mostrar mensaje si no se encuentran ejercicios
            actualizarLabels(List.of("No se encontraron ejercicios para esta categoría"));
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MenuAtleta.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void actualizarLabels(List<String> ejercicios) {
        // Vaciar los labels primero
        LblEjercicio1.setText("");
        LblEjercicio2.setText("");
        LblEjercicio3.setText("");
        LblEjercicio4.setText("");
        LblEjercicio5.setText("");

        // Verificar si hay ejercicios para mostrar
        if (ejercicios.isEmpty()) {
            LblEjercicio1.setText("No se encontraron ejercicios para esta categoría");
        }
        else{
            // Asignar los ejercicios a los labels
            if (ejercicios.size() > 0) LblEjercicio1.setText(ejercicios.get(0));
            if (ejercicios.size() > 1) LblEjercicio2.setText(ejercicios.get(1));
            if (ejercicios.size() > 2) LblEjercicio3.setText(ejercicios.get(2));
            if (ejercicios.size() > 3) LblEjercicio4.setText(ejercicios.get(3));
            if (ejercicios.size() > 4) LblEjercicio5.setText(ejercicios.get(4));
        }


    }

    void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }
}
