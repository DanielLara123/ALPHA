package com.example.proyectoalpha.controller.Medico;

import com.example.proyectoalpha.clases.Atleta.FrecuenciaCardiaca;
import com.example.proyectoalpha.clases.Atleta.GPS;
import com.example.proyectoalpha.clases.Atleta.Oxigenacion;
import com.example.proyectoalpha.servicios.servicioUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class HistorialMedicoController {

    @FXML
    private Button BtnEmergencia;

    @FXML
    private Button BtnMostrar;

    @FXML
    private Button BtnVolver;

    @FXML
    private TextField BuscarTxt;

    @FXML
    private Label LblFC;

    @FXML
    private Label LblGpsData;

    @FXML
    private Label LblO2;

    private servicioUsuario servicioUsuario;

    @FXML
    private void initialize() {
        servicioUsuario = new servicioUsuario();
        BtnMostrar.setOnAction(event -> mostrarDatosMedicos());
        BtnVolver.setOnAction(event -> volver());
        BtnEmergencia.setOnAction(event -> emergencia());
        colocarImagenBotones();
    }

    private void emergencia() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Emergencia");
        alert.setHeaderText(null);
        alert.setContentText("LLAMANDO A 112");
        alert.showAndWait();
    }

    private void volver() {
        try {
            URL url = getClass().getResource("/com/example/proyectoalpha/Medico/MenuMedico.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            loader.load();

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(loader.getRoot()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarDatosMedicos() {
        String correo = BuscarTxt.getText();
        if (correo == null || correo.isEmpty()) {
            showAlert("Por favor, introduce un correo.");
            return;
        }

        // Verificar si el usuario existe
        if (!servicioUsuario.emailEstaRegistrado(correo)) {
            showAlert("El usuario con el correo " + correo + " no existe.");
            return;
        }

        String fileName = correo + "_datosMedicos.json";
        File file = new File(fileName);
        ObjectMapper mapper = new ObjectMapper();

        try {
            if (!file.exists()) {
                crearDatosMedicos(file, mapper);
            }

            DatosMedicos datosMedicos = mapper.readValue(file, DatosMedicos.class);
            LblFC.setText("Frecuencia Cardiaca: " + datosMedicos.getFrecuenciaCardiaca().getFrecuencia());
            LblGpsData.setText("GPS: " + datosMedicos.getGps().getUbicacion());
            LblO2.setText("Oxigenación: " + datosMedicos.getOxigenacion().getNivelOxigeno());
        } catch (IOException e) {
            showAlert("Error al leer los datos médicos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void crearDatosMedicos(File file, ObjectMapper mapper) throws IOException {
        DatosMedicos datosMedicos = new DatosMedicos(
                new FrecuenciaCardiaca(70),
                new GPS("Ubicación de ejemplo"),
                new Oxigenacion(98)
        );
        mapper.writeValue(file, datosMedicos);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class DatosMedicos {
        private FrecuenciaCardiaca frecuenciaCardiaca;
        private GPS gps;
        private Oxigenacion oxigenacion;

        public DatosMedicos() {}

        public DatosMedicos(FrecuenciaCardiaca frecuenciaCardiaca, GPS gps, Oxigenacion oxigenacion) {
            this.frecuenciaCardiaca = frecuenciaCardiaca;
            this.gps = gps;
            this.oxigenacion = oxigenacion;
        }

        public FrecuenciaCardiaca getFrecuenciaCardiaca() {
            return frecuenciaCardiaca;
        }

        public void setFrecuenciaCardiaca(FrecuenciaCardiaca frecuenciaCardiaca) {
            this.frecuenciaCardiaca = frecuenciaCardiaca;
        }

        public GPS getGps() {
            return gps;
        }

        public void setGps(GPS gps) {
            this.gps = gps;
        }

        public Oxigenacion getOxigenacion() {
            return oxigenacion;
        }

        public void setOxigenacion(Oxigenacion oxigenacion) {
            this.oxigenacion = oxigenacion;
        }
    }

    private void colocarImagenBotones(){
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }
}