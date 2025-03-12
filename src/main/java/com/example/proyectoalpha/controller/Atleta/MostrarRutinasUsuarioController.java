package com.example.proyectoalpha.controller.Atleta;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostrarRutinasUsuarioController {

    @FXML
    private ListView<String> listViewRutinas;

    @FXML
    private Button btnMostrar;

    @FXML
    private Button BtnVolver;

    private Map<String, Map<String, Object>> rutinas;

    @FXML
    public void initialize() {
        // Inicializar datos
        BtnVolver.setOnAction(event -> manejarVolver());
        rutinas = new HashMap<>();

        listViewRutinas.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle(""); // Resetear estilo si la celda está vacía
                } else {
                    setText(item);
                    setTextAlignment(TextAlignment.CENTER); // Centrar texto
                    setStyle("-fx-alignment: CENTER; -fx-text-fill: gold; -fx-font-size: 16px; -fx-background-color: #000000;");

                    // Cambiar estilo si está seleccionada
                    if (isSelected()) {
                        setStyle("-fx-alignment: CENTER; -fx-text-fill: black; -fx-font-size: 16px; -fx-background-color: gold;");
                    }

                    // Escuchar cambios en la selección
                    listViewRutinas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                        if (item.equals(newVal)) {
                            setStyle("-fx-alignment: CENTER; -fx-text-fill: black; -fx-font-size: 16px; -fx-background-color: gold;");
                        } else {
                            setStyle("-fx-alignment: CENTER; -fx-text-fill: gold; -fx-font-size: 16px; -fx-background-color: #000000;");
                        }
                    });
                }
            }
        });

        // Configurar eventos
        btnMostrar.setOnAction(event -> mostrarRutinas());
    }

    private void mostrarRutinas() {
        String rutinaSeleccionada = listViewRutinas.getSelectionModel().getSelectedItem();

        if (rutinaSeleccionada == null) {
            mostrarError("Por favor, selecciona una rutina de la lista.");
            return;
        }

        Map<String, Object> detallesRutina = rutinas.get(rutinaSeleccionada);

        if (detallesRutina == null) {
            mostrarError("No se encontraron detalles para la rutina seleccionada.");
            return;
        }

        StringBuilder detalles = new StringBuilder();
        detalles.append("Nombre de la rutina: ").append(detallesRutina.get("nombre")).append("\n\n");

        Object diasObj = detallesRutina.get("dias");

        if (diasObj instanceof String[]) {
            // Manejo de formato con un array de Strings
            String[] dias = (String[]) diasObj;
            detalles.append("Nombre del día 1:\n");
            for (String dia : dias) {
                detalles.append(" - ").append(dia).append("\n");
            }
        } else if (diasObj instanceof java.util.List) {
            // Manejo de formato con una lista estructurada
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> dias = (List<Map<String, Object>>) diasObj;

            for (int i = 0; i < dias.size(); i++) {
                Map<String, Object> dia = dias.get(i);
                String nombreDia = dia.get("nombre") != null ? dia.get("nombre").toString() : "Día " + (i + 1);
                @SuppressWarnings("unchecked")
                List<String> ejercicios = (List<String>) dia.get("ejercicios");

                detalles.append("Nombre del día ").append(i + 1).append(": ").append(nombreDia).append("\n");
                if (ejercicios != null) {
                    for (String ejercicio : ejercicios) {
                        detalles.append(" - ").append(ejercicio).append("\n");
                    }
                } else {
                    detalles.append(" - Sin ejercicios asignados.\n");
                }
                detalles.append("\n");
            }
        } else {
            mostrarError("El formato de los días no es compatible.");
            return;
        }

        // Mostrar los detalles en un cuadro de diálogo
        mostrarMensaje(detalles.toString());
    }


    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/RutinasUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}


