package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Atleta.Ejercicio;
import com.example.proyectoalpha.clases.Atleta.Rutina;
import com.example.proyectoalpha.servicios.ServicioEjercicios;
import com.example.proyectoalpha.servicios.ServicioRutinas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

    private String correoUsuario;
    private String contrasenaUsuario;
    private String dniUsuario;

    private final ServicioRutinas servicioRutinas = new ServicioRutinas(); // Servicio para rutinas
    private final ServicioEjercicios servicioEjercicios = new ServicioEjercicios(); // Servicio para ejercicios

    @FXML
    private void initialize(){
        colocarImagenBotones();
        BtnVolver.setOnAction(e -> manejarVolver());
        BtnAñadirEjercicio.setOnAction(e -> onAnadirEjercicio());
        BtnCrearRutina.setOnAction(e -> onCrearRutina());
    }

    private void onAnadirEjercicio() {
        Dialog<ButtonType> dialogoEjercicio = new Dialog<>();
        dialogoEjercicio.setTitle("Añadir Ejercicio");

        VBox contenido = new VBox();
        contenido.setSpacing(10);
        contenido.setStyle("-fx-padding: 10;");

        // Obtener los grupos musculares del mapa `ejercicios`
        Map<String, List<Ejercicio>> ejerciciosMap = servicioEjercicios.obtenerMapaEjercicios();
        if (ejerciciosMap == null || ejerciciosMap.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "No hay datos de ejercicios disponibles.");
            return;
        }

        List<String> gruposMusculares = new ArrayList<>(ejerciciosMap.keySet());

        // Menú desplegable para seleccionar grupo muscular
        ChoiceBox<String> choiceBoxGruposMusculares = new ChoiceBox<>();
        choiceBoxGruposMusculares.getItems().add("Selecciona un grupo muscular"); // Placeholder
        choiceBoxGruposMusculares.getItems().addAll(gruposMusculares);
        choiceBoxGruposMusculares.getSelectionModel().selectFirst(); // Seleccionar el placeholder al inicio

        // Menú desplegable para seleccionar ejercicio
        ChoiceBox<Ejercicio> choiceBoxEjercicios = new ChoiceBox<>();
        choiceBoxEjercicios.getItems().add(
                new Ejercicio("Selecciona un ejercicio", "placeholder", 0, 0, 0)
        ); // Placeholder
        choiceBoxEjercicios.getSelectionModel().selectFirst(); // Seleccionar el placeholder al inicio

        // Actualizar la lista de ejercicios al seleccionar un grupo muscular
        choiceBoxGruposMusculares.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!"Selecciona un grupo muscular".equals(newValue)) {
                choiceBoxEjercicios.getItems().clear();
                List<Ejercicio> ejercicios = ejerciciosMap.get(newValue);
                if (ejercicios != null && !ejercicios.isEmpty()) {
                    choiceBoxEjercicios.getItems().addAll(ejercicios);
                } else {
                    choiceBoxEjercicios.getItems().add(
                            new Ejercicio("No hay ejercicios disponibles", "placeholder", 0, 0, 0)
                    );
                }
            }
        });

        // Campos de entrada para repeticiones, series y descanso
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

        // Apply CSS to the dialog
        dialogoEjercicio.getDialogPane().getStylesheets().add(getClass().getResource("/css/ModeloAlpha.css").toExternalForm());

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

            Ejercicio ejercicioData = new Ejercicio(nombreEjercicio, "", descanso, series, repeticiones, 0);
            ejerciciosList.add(ejercicioData);
        }

        Rutina nuevaRutina = new Rutina(nombreRutina, ejerciciosList, correoUsuario);

        try {
            List<Rutina> rutinas = servicioRutinas.loadRutinas(correoUsuario);
            rutinas.add(nuevaRutina);
            servicioRutinas.saveRutinas(correoUsuario, rutinas);

            showAlert(Alert.AlertType.INFORMATION, "Rutina guardada exitosamente en " + correoUsuario + "_rutinas.json.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al guardar la rutina.");
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/RutinasUsuario.fxml"));
            Parent root = loader.load();

            RutinasUsuarioController controller = loader.getController();
            controller.setDatosUsuario(dniUsuario, correoUsuario, contrasenaUsuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void colocarImagenBotones(){
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

    public void setDatosUsuario(String dniUsuario, String correoUsuario, String contrasenaUsuario) {
        this.dniUsuario = dniUsuario;
        this.correoUsuario = correoUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
    }
}