package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Ejercicio;
import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

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

    private Usuario usuario;
    private MariaDBController mariaDBController = new MariaDBController();

    @FXML
    public void initialize() {
        BtnVolver.setOnAction(event -> manejarVolver());
        BtnBuscar.setOnAction(event -> manejarBuscar());
        ChoiceGrupoMuscular.setOnAction(event -> actualizarEjercicios());
        llenarChoiceBoxes();
        colocarImagenBotones();
    }

    private void llenarChoiceBoxes() {
        List<String> gruposMusculares = mariaDBController.obtenerGruposMusculares();
        ChoiceGrupoMuscular.getItems().addAll(gruposMusculares);
    }

    private void actualizarEjercicios() {
        String selectedGrupoMuscular = ChoiceGrupoMuscular.getValue();
        if (selectedGrupoMuscular != null) {
            List<Ejercicio> ejercicios = mariaDBController.obtenerEjerciciosPorGrupoMuscular(selectedGrupoMuscular);
            ChoiceEjercicio.getItems().clear();
            for (Ejercicio ejercicio : ejercicios) {
                ChoiceEjercicio.getItems().add(ejercicio.getNombre());
            }
        }
    }

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    private void manejarBuscar() {
        String selectedEjercicio = ChoiceEjercicio.getValue();
        String selectedGrupoMuscular = ChoiceGrupoMuscular.getValue();

        if (selectedEjercicio != null && selectedGrupoMuscular != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MostrarLogros.fxml"));
                Parent root = loader.load();
                MostrarLogrosController controller = loader.getController();
                controller.setDatos(selectedEjercicio, selectedGrupoMuscular, usuario);

                Stage stage = (Stage) BtnBuscar.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LblMensaje.setText("Seleccione un ejercicio y un grupo muscular.");
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MenuAtleta.fxml"));
            Parent root = loader.load();

            MenuAtletaController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }
}