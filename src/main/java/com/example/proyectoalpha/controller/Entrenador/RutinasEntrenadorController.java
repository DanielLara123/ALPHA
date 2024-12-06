package com.example.proyectoalpha.controller.Entrenador;

import com.example.proyectoalpha.servicios.ServicioRutinas;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RutinasEntrenadorController {

    @FXML
    private TextField nombreRutinaField;

    @FXML
    private ListView<HBox> listViewDias;

    private final ServicioRutinas servicioRutinas = new ServicioRutinas(); // Instancia del servicio
    private int contadorDias = 1; // Contador para los días

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
        TextInputDialog dialogoEjercicio = new TextInputDialog("Nuevo ejercicio");
        dialogoEjercicio.setTitle("Añadir Ejercicio");
        dialogoEjercicio.setHeaderText("Escribe el nombre del ejercicio:");
        dialogoEjercicio.setContentText("Nombre del ejercicio:");

        dialogoEjercicio.showAndWait().ifPresent(nombreEjercicio -> {
            VBox ejerciciosContainer = (VBox) diaContainer.getChildren().get(2);

            Label nuevoEjercicio = new Label(nombreEjercicio);
            nuevoEjercicio.setStyle("-fx-text-fill: gold; -fx-font-size: 12px;");

            ejerciciosContainer.getChildren().add(nuevoEjercicio);
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
}
