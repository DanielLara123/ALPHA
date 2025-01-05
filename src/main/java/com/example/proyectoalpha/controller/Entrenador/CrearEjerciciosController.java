package com.example.proyectoalpha.controller.Entrenador;

import com.example.proyectoalpha.clases.Atleta.Ejercicio;
import com.example.proyectoalpha.servicios.ServicioEjercicios;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CrearEjerciciosController {

    @FXML
    private Button BtnCrearEjercicio;

    @FXML
    private Button BtnVolver;

    @FXML
    private ChoiceBox<String> ChoiceBoxGrupoMuscular;

    @FXML
    private TextField TextFieldEjercicio;

    private String correoEntrenador;

    private ServicioEjercicios servicioEjercicios = new ServicioEjercicios();

    public void setCorreoEntrenador(String correoEntrenador) {
        this.correoEntrenador = correoEntrenador;
    }

    @FXML
    private void initialize() {
        colocarImagenBotones();
        BtnVolver.setOnAction(event -> volver());
        BtnCrearEjercicio.setOnAction(event -> crearEjercicio());
        ChoiceBoxGrupoMuscular.getItems().addAll(servicioEjercicios.obtenerGruposMusculares());
    }

    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/proyectoalpha/Entrenador/OpcionesRutinas.fxml"));
            Parent root = loader.load();

            OpcionesRutinasController controller = loader.getController();
            controller.setCorreoEntrenador(correoEntrenador);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crearEjercicio() {
        String grupoMuscular = ChoiceBoxGrupoMuscular.getValue();
        String nombreEjercicio = TextFieldEjercicio.getText();

        if (grupoMuscular == null || grupoMuscular.isEmpty()) {
            showAlert("Por favor, selecciona un grupo muscular.");
            return;
        }

        if (nombreEjercicio == null || nombreEjercicio.isEmpty()) {
            showAlert("Por favor, introduce el nombre del ejercicio.");
            return;
        }

        try {
            Ejercicio nuevoEjercicio = new Ejercicio(nombreEjercicio, grupoMuscular, 0, 0, 0, 0.0);
            servicioEjercicios.agregarEjercicio(grupoMuscular, nuevoEjercicio);
            showAlert("Ejercicio creado exitosamente.");
        } catch (Exception e) {
            showAlert("Error al crear el ejercicio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }
}