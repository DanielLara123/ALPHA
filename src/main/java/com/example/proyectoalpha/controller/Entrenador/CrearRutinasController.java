package com.example.proyectoalpha.controller.Entrenador;

import com.example.proyectoalpha.clases.Ejercicio;
import com.example.proyectoalpha.clases.Rutina;
import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.Atleta.RutinasUsuarioController;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrearRutinasController {

    @FXML
    private TextField nombreRutinaField;

    @FXML
    private Button BtnVolver;

    @FXML
    private ListView<HBox> listViewEjercicios;

    @FXML
    private Button BtnAñadirEjercicio;

    @FXML
    private Button BtnCrearRutina;

    @FXML
    private Button BtnBuscarAtleta;

    @FXML
    private TextField TextFieldCorreoAtleta;

    private Usuario atleta;
    private MariaDBController mariaDBController = new MariaDBController();

    @FXML
    private void initialize() {
        colocarImagenBotones();
        BtnVolver.setOnAction(e -> manejarVolver());
        BtnAñadirEjercicio.setOnAction(e -> onAnadirEjercicio());
        BtnCrearRutina.setOnAction(e -> onCrearRutina());
        BtnBuscarAtleta.setOnAction(e -> buscarAtleta());
    }

    private void buscarAtleta() {
        String correoAtleta = TextFieldCorreoAtleta.getText();
        atleta = mariaDBController.obtenerUsuarioPorCorreo(correoAtleta);
        if (atleta == null) {
            showAlert(Alert.AlertType.ERROR, "No se encontró un atleta con el correo proporcionado.");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Atleta encontrado: " + atleta.getNombre());
        }
    }

    private void onAnadirEjercicio() {
        Dialog<ButtonType> dialogoEjercicio = new Dialog<>();
        dialogoEjercicio.setTitle("Añadir Ejercicio");

        VBox contenido = new VBox();
        contenido.setSpacing(10);
        contenido.setStyle("-fx-padding: 10;");

        Map<String, List<Ejercicio>> ejerciciosMap = mariaDBController.obtenerMapaEjercicios();
        if (ejerciciosMap == null || ejerciciosMap.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "No hay datos de ejercicios disponibles.");
            return;
        }

        List<String> gruposMusculares = new ArrayList<>(ejerciciosMap.keySet());

        ChoiceBox<String> choiceBoxGruposMusculares = new ChoiceBox<>();
        choiceBoxGruposMusculares.getItems().add("Selecciona un grupo muscular");
        choiceBoxGruposMusculares.getItems().addAll(gruposMusculares);
        choiceBoxGruposMusculares.getSelectionModel().selectFirst();

        ChoiceBox<Ejercicio> choiceBoxEjercicios = new ChoiceBox<>();
        choiceBoxEjercicios.getItems().add(new Ejercicio("Selecciona un ejercicio", "placeholder", 0, 0, 0, 0));
        choiceBoxEjercicios.getSelectionModel().selectFirst();

        choiceBoxGruposMusculares.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!"Selecciona un grupo muscular".equals(newValue)) {
                choiceBoxEjercicios.getItems().clear();
                List<Ejercicio> ejercicios = ejerciciosMap.get(newValue);
                if (ejercicios != null && !ejercicios.isEmpty()) {
                    choiceBoxEjercicios.getItems().addAll(ejercicios);
                } else {
                    choiceBoxEjercicios.getItems().add(new Ejercicio("No hay ejercicios disponibles", "placeholder", 0, 0, 0, 0));
                }
            }
        });

        TextField repeticionesField = new TextField();
        repeticionesField.setPromptText("Repeticiones");

        TextField seriesField = new TextField();
        seriesField.setPromptText("Series");

        TextField descansoField = new TextField();
        descansoField.setPromptText("Descanso (segundos)");

        contenido.getChildren().addAll(
                new Label("Selecciona un grupo muscular:"), choiceBoxGruposMusculares,
                new Label("Selecciona un ejercicio:"), choiceBoxEjercicios,
                new Label("Repeticiones:"), repeticionesField,
                new Label("Series:"), seriesField,
                new Label("Descanso (segundos):"), descansoField
        );

        dialogoEjercicio.getDialogPane().setContent(contenido);
        dialogoEjercicio.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialogoEjercicio.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Ejercicio ejercicioSeleccionado = choiceBoxEjercicios.getValue();
                if (ejercicioSeleccionado != null && !"Selecciona un ejercicio".equals(ejercicioSeleccionado.getNombre())) {
                    HBox nuevoEjercicio = new HBox();
                    nuevoEjercicio.setSpacing(10);
                    nuevoEjercicio.setAlignment(Pos.CENTER_LEFT);
                    nuevoEjercicio.setStyle("-fx-background-color: black; -fx-padding: 10;");

                    Label ejercicioLabel = new Label(ejercicioSeleccionado.getNombre() + " - Repeticiones: " + repeticionesField.getText() + ", Series: " + seriesField.getText() + ", Descanso: " + descansoField.getText() + " seg");
                    ejercicioLabel.setStyle("-fx-text-fill: gold; -fx-font-size: 12px;");

                    nuevoEjercicio.getChildren().add(ejercicioLabel);

                    listViewEjercicios.getItems().add(nuevoEjercicio);
                } else {
                    showAlert(Alert.AlertType.WARNING, "Por favor, selecciona un ejercicio válido.");
                }
            }
        });
    }

    private void onCrearRutina() {
        if (atleta == null) {
            showAlert(Alert.AlertType.ERROR, "Por favor, busque y seleccione un atleta antes de crear una rutina.");
            return;
        }

        String nombreRutina = nombreRutinaField.getText();

        if (nombreRutina.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Por favor, introduce un nombre para la rutina.");
            return;
        }

        List<Ejercicio> ejerciciosList = new ArrayList<>();
        for (HBox dia : listViewEjercicios.getItems()) {
            Label ejercicioLabel = (Label) dia.getChildren().get(0);
            String[] parts = ejercicioLabel.getText().split(" - ");
            String nombreEjercicio = parts[0];
            String[] detalles = parts[1].split(", ");
            int repeticiones = Integer.parseInt(detalles[0].split(": ")[1]);
            int series = Integer.parseInt(detalles[1].split(": ")[1]);
            int descanso = Integer.parseInt(detalles[2].split(": ")[1].split(" ")[0]);

            // Retrieve the existing Ejercicio from the database
            Ejercicio ejercicioData = mariaDBController.obtenerEjercicioPorNombre(nombreEjercicio);
            if (ejercicioData != null) {
                ejercicioData.setSeries(series);
                ejercicioData.setRepeticiones(repeticiones);
                ejercicioData.setDescanso(descanso);
                ejerciciosList.add(ejercicioData);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error al obtener el ejercicio: " + nombreEjercicio);
                return;
            }
        }

        Rutina nuevaRutina = new Rutina(nombreRutina, 0); // Assuming 'dias' is not used here

        boolean success = mariaDBController.guardarRutina(nuevaRutina, atleta.getID(), ejerciciosList);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Rutina guardada exitosamente.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error al guardar la rutina.");
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/OpcionesRutinas.fxml"));
            Parent root = loader.load();

            OpcionesRutinasController controller = loader.getController();
            controller.setDatosUsuario(atleta);

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
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/ModeloAlpha.css").toExternalForm());
        alert.show();
    }

    public void setDatosUsuario(Usuario usuario) {
        this.atleta = usuario;
    }
}