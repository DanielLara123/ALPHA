package com.example.proyectoalpha.controller.Medico;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HistorialMedicoController {

    @FXML
    private ImageView LblTitulo;

    @FXML
    private ImageView GPS;

    @FXML
    private ImageView O2;

    @FXML
    private ImageView FC;

    @FXML
    private Label LblGpsData;

    @FXML
    private Label LblO2;

    @FXML
    private Label LblFC;

    @FXML
    private TextField BuscarTxt;

    @FXML
    private Button BtnMostrar;

    @FXML
    private Button BtnEmergencia;

    @FXML
    private Button BtnVolver;

    @FXML
    private void initialize(){
        BtnMostrar.setOnAction(event -> manejarMostrar());
        colocarImagenBotones();
        BtnVolver.setOnAction(e -> manejarVolver());

    }

    void manejarMostrar() {
        // Obtener el nombre del deportista ingresado en el TextField
        String nombre = BuscarTxt.getText().trim();

        // Cargar el JSON de datos
        String jsonData = cargarDatos();

        // Verificar si el JSON está vacío
        if (jsonData == null || jsonData.isEmpty()) {
            System.out.println("Error: El JSON no tiene contenido.");
            return;
        }

        // Buscar el deportista que coincide con el nombre
        String deportista = buscarDeportista(jsonData, nombre);

        // Mostrar los datos si se encuentra el deportista
        if (deportista != null) {
            String gps = obtenerDatoDeportista(deportista, "gps");
            String oxigenacion = obtenerDatoDeportista(deportista, "oxigenacion");
            String frecuenciaCardiaca = obtenerDatoDeportista(deportista, "frecuencia_cardiaca");

            LblGpsData.setText("GPS: " + gps);
            LblO2.setText("Oxigenación: " + oxigenacion + "%");
            LblFC.setText("Frecuencia Cardíaca: " + frecuenciaCardiaca + " bpm");
        } else {
            LblGpsData.setText("Deportista no encontrado.");
            LblO2.setText("");
            LblFC.setText("");
        }
    }

    // Método para cargar los datos desde el archivo JSON
    private String cargarDatos() {
        String jsonData = "";
        Path path = Paths.get("datosMedicos.json"); // Solo el nombre del archivo JSON
        if (!Files.exists(path)) {
            System.out.println("Error: El archivo JSON no existe en la ruta especificada.");
            return null; // Retornamos null si el archivo no existe
        }

        try {
            jsonData = new String(Files.readAllBytes(path)); // Leer archivo completo como texto
            System.out.println("Archivo JSON cargado con éxito.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
        return jsonData;
    }

    // Método para buscar el deportista por nombre
    private String buscarDeportista(String jsonData, String nombre) {
        // Buscar el inicio de la lista de deportistas
        int startIndex = jsonData.indexOf("\"datos\": [");
        if (startIndex == -1) {
            System.out.println("Error: No se pudo encontrar la lista de deportistas en el JSON.");
            return null;
        }

        // Extraer la parte del JSON que contiene los deportistas
        int endIndex = jsonData.indexOf("]", startIndex);
        String deportistasJson = jsonData.substring(startIndex + 9, endIndex);

        // Dividir por cada deportista usando "}," como delimitador
        String[] deportistas = deportistasJson.split("\\},\\{");

        // Buscar el deportista que coincida con el nombre
        for (String deportista : deportistas) {
            // Depuración: Imprimir el JSON del deportista
            System.out.println("Deportista encontrado: " + deportista);

            String nombreDeportista = obtenerDatoDeportista(deportista, "nombre_deportista");

            // Depuración: Mostrar los nombres que estamos comparando
            System.out.println("Buscando: '" + nombreDeportista + "' con '" + nombre + "'");

            // Comparar los nombres sin importar mayúsculas ni tildes
            if (nombreDeportista != null && compararNombres(nombreDeportista, nombre)) {
                return deportista;  // Deportista encontrado
            }
        }
        return null;  // No encontrado
    }

    // Método para obtener los datos de un deportista de la cadena JSON
    private String obtenerDatoDeportista(String deportista, String clave) {
        String key = "\"" + clave + "\":\"";
        int startIndex = deportista.indexOf(key);

        if (startIndex == -1) {
            System.out.println("Clave no encontrada: " + clave);  // Depuración para ver si encontramos la clave
            return null; // Si no encontramos la clave, devolvemos null
        }

        startIndex += key.length();
        int endIndex = deportista.indexOf("\"", startIndex);
        return deportista.substring(startIndex, endIndex);
    }

    // Método para comparar dos nombres ignorando mayúsculas y tildes
    private boolean compararNombres(String nombre1, String nombre2) {
        if (nombre1 == null || nombre2 == null) {
            return false; // Si alguno de los nombres es null, retornamos false
        }
        // Limpiar tildes y convertir a minúsculas
        nombre1 = nombre1.toLowerCase().replaceAll("[áéíóú]", "").trim();
        nombre2 = nombre2.toLowerCase().replaceAll("[áéíóú]", "").trim();

        return nombre1.equals(nombre2);
    }

    void manejarVolver(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Medico/MenuMedico.fxml"));
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
