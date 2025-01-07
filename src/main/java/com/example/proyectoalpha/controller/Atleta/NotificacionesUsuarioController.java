package com.example.proyectoalpha.controller.Atleta;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;

public class NotificacionesUsuarioController {

    @FXML
    private Button BtnVolver;

    @FXML
    private Label LblMensaje;

    private String dniUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;

    @FXML
    private void initialize() {
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    public void setDatosUsuario(String dni, String correo, String contrasena) {
        dniUsuario = dni;
        correoUsuario = correo;
        contrasenaUsuario = contrasena;
        cargarNotificaciones();
    }

    private void cargarNotificaciones() {
        String fileName = correoUsuario + "_historial.json";
        File file = new File(fileName);
        if (!file.exists()) {
            LblMensaje.setText("El archivo de historial no se encuentra.");
        } else {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode rootNode = objectMapper.readTree(file);
                long lastDate = 0;

                if (rootNode.isArray()) {
                    for (JsonNode exerciseNode : rootNode) {
                        JsonNode historialEjercicios = exerciseNode.get("historialEjercicios");

                        if (historialEjercicios != null) {
                            Iterator<JsonNode> elements = historialEjercicios.elements();

                            while (elements.hasNext()) {
                                JsonNode node = elements.next();
                                long date = node.get("fecha").asLong();
                                if (date > lastDate) {
                                    lastDate = date;
                                }
                            }
                        }
                    }
                }

                if (lastDate > 0) {
                    long currentTime = System.currentTimeMillis();
                    long timePassed = currentTime - lastDate;
                    long daysPassed = timePassed / (1000 * 60 * 60 * 24);
                    LblMensaje.setText("Han pasado " + daysPassed + " días desde tu último ejercicio.");
                } else {
                    LblMensaje.setText("No se encontraron registros de ejercicios.");
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

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        javafx.scene.image.Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }
}