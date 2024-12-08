package com.example.proyectoalpha.controller.Entrenador;

import com.example.proyectoalpha.clases.Atleta.Ejercicio;
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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrearRutinasController {

    @FXML
    private TextField nombreRutinaField;

    @FXML
    private Button BtnVolver;

    @FXML
    private ListView<HBox> listViewDias;

    private final ServicioRutinas servicioRutinas = new ServicioRutinas(); // Servicio para rutinas
    private final ServicioEjercicios servicioEjercicios = new ServicioEjercicios(); // Servicio para ejercicios
    private int contadorDias = 1; // Contador para los días

    @FXML
    private void initialize(){
        colocarImagenBotones();
        BtnVolver.setOnAction(e -> manejarVolver());
    }

    @FXML
    private void onAnadirDia() {
        TextInputDialog dialogoDia = new TextInputDialog("Día " + contadorDias);
        dialogoDia.setTitle("Añadir Día");
        dialogoDia.setHeaderText("Escribe el nombre del día:");
        dialogoDia.setContentText("Nombre del día:");

        dialogoDia.showAndWait().ifPresent(nombreDia -> {
            HBox nuevoDia = new HBox();
            nuevoDia.setSpacing(10);
            nuevoDia.setAlignment(Pos.CENTER_LEFT);
            nuevoDia.setStyle("-fx-background-color: black; -fx-padding: 10;");

            Label diaLabel = new Label(nombreDia);
            diaLabel.setStyle("-fx-text-fill: gold; -fx-font-size: 14px;");

            Button anadirEjercicioButton = new Button("+ Añadir ejercicio");
            anadirEjercicioButton.setStyle("-fx-background-color: transparent; -fx-border-color: gold; -fx-text-fill: gold;");
            anadirEjercicioButton.setOnAction(e -> onAnadirEjercicio(nuevoDia));

            VBox ejerciciosContainer = new VBox();
            ejerciciosContainer.setSpacing(5);
            ejerciciosContainer.setStyle("-fx-padding: 10;");

            nuevoDia.getChildren().addAll(diaLabel, anadirEjercicioButton, ejerciciosContainer);

            listViewDias.getItems().add(nuevoDia);
            contadorDias++;
        });
    }

    private void onAnadirEjercicio(HBox diaContainer) {
        Dialog<ButtonType> dialogoEjercicio = new Dialog<>();
        dialogoEjercicio.setTitle("Añadir Ejercicio");

        VBox contenido = new VBox();
        contenido.setSpacing(10);
        contenido.setStyle("-fx-padding: 10;");

        // Obtener los grupos musculares del mapa `ejercicios`
        Map<String, List<Ejercicio>> ejerciciosMap = servicioEjercicios.obtenerMapaEjercicios();
        if (ejerciciosMap == null || ejerciciosMap.isEmpty()) {
            Alert alerta = new Alert(AlertType.ERROR, "No hay datos de ejercicios disponibles.");
            alerta.show();
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

        contenido.getChildren().addAll(
                new Label("Selecciona un grupo muscular:"), choiceBoxGruposMusculares,
                new Label("Selecciona un ejercicio:"), choiceBoxEjercicios
        );

        dialogoEjercicio.getDialogPane().setContent(contenido);
        dialogoEjercicio.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialogoEjercicio.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Ejercicio ejercicioSeleccionado = choiceBoxEjercicios.getValue();
                if (ejercicioSeleccionado != null && !"Selecciona un ejercicio".equals(ejercicioSeleccionado.getNombre())) {
                    VBox ejerciciosContainer = (VBox) diaContainer.getChildren().get(2);

                    Label nuevoEjercicio = new Label(ejercicioSeleccionado.getNombre());
                    nuevoEjercicio.setStyle("-fx-text-fill: gold; -fx-font-size: 12px;");

                    ejerciciosContainer.getChildren().add(nuevoEjercicio);
                } else {
                    Alert alerta = new Alert(AlertType.WARNING, "Por favor, selecciona un ejercicio válido.");
                    alerta.show();
                }
            }
        });
    }

    @FXML
    private void onCrearRutina() {
        String nombreRutina = nombreRutinaField.getText();

        if (nombreRutina.isEmpty()) {
            Alert alerta = new Alert(AlertType.WARNING, "Por favor, introduce un nombre para la rutina.");
            alerta.show();
            return;
        }

        HashMap<String, Object> rutinaData = new HashMap<>();
        rutinaData.put("nombre", nombreRutina);

        List<HashMap<String, Object>> dias = new ArrayList<>();
        for (HBox dia : listViewDias.getItems()) {
            Label diaLabel = (Label) dia.getChildren().get(0);
            String nombreDia = diaLabel.getText();

            VBox ejerciciosContainer = (VBox) dia.getChildren().get(2);
            List<String> ejercicios = new ArrayList<>();
            for (javafx.scene.Node ejercicio : ejerciciosContainer.getChildren()) {
                if (ejercicio instanceof Label) {
                    ejercicios.add(((Label) ejercicio).getText());
                }
            }

            HashMap<String, Object> diaData = new HashMap<>();
            diaData.put("nombre", nombreDia);
            diaData.put("ejercicios", ejercicios);

            dias.add(diaData);
        }
        rutinaData.put("dias", dias);

        // Guardar la rutina utilizando ServicioRutinas
        servicioRutinas.agregarRutina(nombreRutina, rutinaData);

        Alert alerta = new Alert(AlertType.INFORMATION, "Rutina guardada exitosamente en rutinas.json.");
        alerta.show();
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/OpcionesRutinas.fxml"));
            Parent root = loader.load();
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
}
