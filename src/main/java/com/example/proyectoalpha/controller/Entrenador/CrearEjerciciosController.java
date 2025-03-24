package com.example.proyectoalpha.controller.Entrenador;

import com.example.proyectoalpha.clases.Ejercicio;
import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.servicios.MariaDBController;
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
import java.util.List;

public class CrearEjerciciosController {

    @FXML
    private Button BtnCrearEjercicio;

    @FXML
    private Button BtnVolver;

    @FXML
    private ChoiceBox<String> ChoiceBoxGrupoMuscular;

    @FXML
    private TextField TextFieldEjercicio;
    private Usuario usuario;

    private MariaDBController mariaDBController = new MariaDBController();

    @FXML
    private void initialize() {
        cargarGruposMusculares();
        BtnCrearEjercicio.setOnAction(e -> crearEjercicio());
        BtnVolver.setOnAction(e -> manejarVolver());
        colocarImagenBotones();
    }

    private void cargarGruposMusculares() {
        List<String> gruposMusculares = mariaDBController.obtenerGruposMusculares();
        if (gruposMusculares != null && !gruposMusculares.isEmpty()) {
            ChoiceBoxGrupoMuscular.getItems().addAll(gruposMusculares);
        } else {
            showAlert(Alert.AlertType.ERROR, "No hay datos de grupos musculares disponibles.");
        }
    }

    private void crearEjercicio() {
        String grupoMuscular = ChoiceBoxGrupoMuscular.getValue();
        String nombreEjercicio = TextFieldEjercicio.getText();

        if (grupoMuscular == null || grupoMuscular.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Por favor, selecciona un grupo muscular.");
            return;
        }

        if (nombreEjercicio == null || nombreEjercicio.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Por favor, introduce un nombre para el ejercicio.");
            return;
        }

        Ejercicio nuevoEjercicio = new Ejercicio();
        nuevoEjercicio.setGrupoMuscular(grupoMuscular);
        nuevoEjercicio.setNombre(nombreEjercicio);

        boolean success = mariaDBController.guardarEjercicio(nuevoEjercicio);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Ejercicio guardado exitosamente.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error al guardar el ejercicio.");
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/MenuEntrenador.fxml"));
            Parent root = loader.load();

            MenuEntrenadorController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.show();
    }

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}