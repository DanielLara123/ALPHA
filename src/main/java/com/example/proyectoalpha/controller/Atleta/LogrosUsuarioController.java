package com.example.proyectoalpha.controller.Atleta;
/*
import com.example.proyectoalpha.clases.Ejercicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
/*
public class LogrosUsuarioController {

    @FXML
    private Button BtnBuscar;

    @FXML
    private Button BtnVolver;

    @FXML
    private ChoiceBox<String> ChoiceEjercicio;

    @FXML
    private ChoiceBox<String> ChoiceGrupoMuscular;

    @FXML
    private Label LblMensaje;

    private String dniUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;

    private ServicioEjercicios servicioEjercicios;

    @FXML
    private void initialize() {
        servicioEjercicios = new ServicioEjercicios();
        Set<String> gruposMusculares = servicioEjercicios.obtenerGruposMusculares();
        ChoiceGrupoMuscular.getItems().addAll(gruposMusculares);

        ChoiceGrupoMuscular.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ObservableList<String> ejercicios = FXCollections.observableArrayList(servicioEjercicios.obtenerEjercicios(newValue).stream().map(Ejercicio::getNombre).toList());
                ChoiceEjercicio.setItems(ejercicios);
            }
        });

        BtnBuscar.setOnAction(event -> manejarContinuar());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
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

    private void manejarContinuar() {
        if (verificarSeleccion()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MostrarLogros.fxml"));
                Parent root = loader.load();

                MostrarLogrosController controller = loader.getController();
                controller.setDatosUsuario(dniUsuario, correoUsuario, contrasenaUsuario);
                controller.setEjercicio(ChoiceEjercicio.getValue());

                Stage stage = (Stage) BtnBuscar.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LblMensaje.setText("Seleccione un grupo muscular y un ejercicio.");
        }
    }

    private boolean verificarSeleccion() {
        return ChoiceGrupoMuscular.getValue() != null && ChoiceEjercicio.getValue() != null;
    }

    public void setDatosUsuario(String dniUsuario, String correoUsuario, String contrasenaUsuario) {
        this.dniUsuario = dniUsuario;
        this.correoUsuario = correoUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }
}

 */