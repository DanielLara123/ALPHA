package com.example.proyectoalpha.controller.Atleta;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Stack;

public class MostrarLogrosController {

    @FXML
    private Button BtnVolver;

    @FXML
    private ListView<String> ListViewLogros;

    @FXML
    private Label LblMensaje;

    private String dniUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;
    private String ejercicio;

    @FXML
    private void initialize() {
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
        cargarLogros();
    }

    private void cargarLogros() {
        String fileName = correoUsuario + "_historial.json";
        File file = new File(fileName);
        if (!file.exists()) {
            LblMensaje.setText("El archivo de historial no se encuentra.");
        } else {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode rootNode = objectMapper.readTree(file);
                if (rootNode.isArray()) {
                    boolean found = false;
                    for (JsonNode exerciseNode : rootNode) {
                        JsonNode nombreEjercicioNode = exerciseNode.get("nombreEjercicio");
                        JsonNode historialEjercicios = exerciseNode.get("historialEjercicios");

                        if (nombreEjercicioNode != null && historialEjercicios != null) {
                            String nombreEjercicio = nombreEjercicioNode.asText();
                            if (nombreEjercicio.equals(ejercicio)) {
                                found = true;
                                Stack<String> stack = new Stack<>();
                                Iterator<JsonNode> elements = historialEjercicios.elements();

                                while (elements.hasNext()) {
                                    JsonNode node = elements.next();
                                    String date = new java.util.Date(node.get("fecha").asLong()).toString();
                                    int reps = node.get("repeticiones").asInt();
                                    double weight = node.get("peso").asDouble();
                                    stack.push("Fecha: " + date + ", Repeticiones: " + reps + ", Peso: " + weight + " kg");
                                }

                                while (!stack.isEmpty()) {
                                    ListViewLogros.getItems().add(stack.pop());
                                }
                            }
                        }
                    }
                    if (!found) {
                        LblMensaje.setText("No se encontraron registros para el ejercicio seleccionado.");
                    }
                } else {
                    LblMensaje.setText("El archivo de historial no contiene los datos esperados.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                LblMensaje.setText("Error al leer el archivo de historial.");
            }
        }
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