package com.example.proyectoalpha.controller.Entrenador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.TextAlignment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ListaRutinasController {

    @FXML
    private ListView<String> listViewRutinas;

    @FXML
    private Button btnEliminar;

    private Map<String, Map<String, Object>> rutinas;
    private static final String JSON_PATH = "rutinas.json";

    @FXML
    public void initialize() {
        // Inicializar datos
        rutinas = new HashMap<>();
        cargarRutinas();

        // Cargar los nombres de rutinas en la lista
        ObservableList<String> rutinaNombres = FXCollections.observableArrayList(rutinas.keySet());
        listViewRutinas.setItems(rutinaNombres);

        // Personalizar las celdas del ListView
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
        btnEliminar.setOnAction(event -> eliminarRutina());
    }

    private void cargarRutinas() {
        try {
            // Leer el archivo JSON
            String content = new String(Files.readAllBytes(Paths.get(JSON_PATH)));

            // Parsear el contenido manualmente
            String[] bloques = content.split("\"rutinas\":\\s*\\{")[1].split("}\\s*}\\s*")[0].split("\\},");
            for (String bloque : bloques) {
                bloque = bloque.trim().endsWith("}") ? bloque : bloque + "}";
                String nombre = bloque.split(":")[0].replace("\"", "").trim();
                String diasString = bloque.split("\"dias\":\\s*\\[")[1].split("]")[0];
                String[] dias = diasString.replace("\"", "").split(",");

                Map<String, Object> rutina = new HashMap<>();
                rutina.put("nombre", nombre);
                rutina.put("dias", dias);

                rutinas.put(nombre, rutina);
            }
        } catch (Exception e) {
            mostrarError("Error al cargar las rutinas: " + e.getMessage());
        }
    }

    private void eliminarRutina() {
        String selectedRutina = listViewRutinas.getSelectionModel().getSelectedItem();
        if (selectedRutina != null) {
            // Eliminar la rutina del Map
            rutinas.remove(selectedRutina);

            // Eliminar del archivo JSON
            guardarRutinasEnJson();

            // Actualizar la lista
            listViewRutinas.getItems().remove(selectedRutina);

            mostrarMensaje("Rutina eliminada correctamente.");
        } else {
            mostrarError("Por favor, selecciona una rutina.");
        }
    }

    private void guardarRutinasEnJson() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_PATH))) {
            writer.write("{\n  \"rutinas\": {\n");

            int count = 0;
            for (String key : rutinas.keySet()) {
                Map<String, Object> rutina = rutinas.get(key);

                writer.write("    \"" + key + "\": {\n");
                writer.write("      \"nombre\": \"" + rutina.get("nombre") + "\",\n");

                // Guardar días
                String[] dias = (String[]) rutina.get("dias");
                writer.write("      \"dias\": [");
                for (int i = 0; i < dias.length; i++) {
                    writer.write("\"" + dias[i] + "\"");
                    if (i < dias.length - 1) {
                        writer.write(", ");
                    }
                }
                writer.write("]\n");

                writer.write("    }");
                if (++count < rutinas.size()) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("  }\n}");
        } catch (Exception e) {
            mostrarError("Error al guardar las rutinas: " + e.getMessage());
        }
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
}
