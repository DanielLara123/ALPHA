package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Atleta.Ejercicio;
import com.example.proyectoalpha.clases.Atleta.HistorialEjercicio;
import com.example.proyectoalpha.clases.Atleta.HistorialesEjercicios;
import com.example.proyectoalpha.clases.Atleta.Rutina;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class VerRutinasController {

    @FXML
    private Button BtnVolver;

    @FXML
    private Button btnAgregarSerie;

    @FXML
    private TableColumn<Ejercicio, String> columnaEjercicio;

    @FXML
    private TableColumn<Ejercicio, Integer> columnaRepeticiones;

    @FXML
    private TableColumn<Ejercicio, Integer> columnaSeries;

    @FXML
    private TableColumn<Ejercicio, Integer> columnaDescanso;

    @FXML
    private TableView<Ejercicio> tableViewEjercicios;

    private Rutina rutina;
    private ObservableList<Ejercicio> ejercicios;

    private String dniUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;



    public void setRutina(Rutina selectedRutina) {
        this.rutina = selectedRutina;
        loadEjercicios();
    }

    private void loadEjercicios() {
        ejercicios = FXCollections.observableArrayList(rutina.getEjercicios());
        tableViewEjercicios.setItems(ejercicios);
    }

    @FXML
    private void initialize() {
        columnaEjercicio.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaSeries.setCellValueFactory(new PropertyValueFactory<>("series"));
        columnaRepeticiones.setCellValueFactory(new PropertyValueFactory<>("repeticiones"));
        columnaDescanso.setCellValueFactory(new PropertyValueFactory<>("descanso"));

        // Set style classes for the columns
        columnaEjercicio.getStyleClass().add("columna-ejercicio");
        columnaSeries.getStyleClass().add("columna-series");
        columnaRepeticiones.getStyleClass().add("columna-repeticiones");
        columnaDescanso.getStyleClass().add("columna-descanso");

        btnAgregarSerie.setOnAction(event -> agregarSerie());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    private void agregarSerie() {
        Ejercicio selectedEjercicio = tableViewEjercicios.getSelectionModel().getSelectedItem();
        if (selectedEjercicio != null) {
            TextInputDialog repeticionesDialog = new TextInputDialog();
            repeticionesDialog.setTitle("Agregar Serie");
            repeticionesDialog.setHeaderText("Agregar Serie para " + selectedEjercicio.getNombre());
            repeticionesDialog.setContentText("Ingrese repeticiones:");
            repeticionesDialog.getDialogPane().getStylesheets().add(getClass().getResource("/css/ModeloAlpha.css").toExternalForm());

            Optional<String> repeticionesResult = repeticionesDialog.showAndWait();
            repeticionesResult.ifPresent(repeticionesInput -> {
                try {
                    int repeticiones = Integer.parseInt(repeticionesInput.trim());

                    TextInputDialog pesoDialog = new TextInputDialog();
                    pesoDialog.setTitle("Agregar Serie");
                    pesoDialog.setHeaderText("Agregar Serie para " + selectedEjercicio.getNombre());
                    pesoDialog.setContentText("Ingrese peso:");
                    pesoDialog.getDialogPane().getStylesheets().add(getClass().getResource("/css/ModeloAlpha.css").toExternalForm());

                    Optional<String> pesoResult = pesoDialog.showAndWait();
                    pesoResult.ifPresent(pesoInput -> {
                        try {
                            double peso = Double.parseDouble(pesoInput.trim());
                            HistorialEjercicio historialEjercicio = new HistorialEjercicio(new Date(), peso, repeticiones);
                            guardarHistorial(selectedEjercicio.getNombre(), historialEjercicio);
                        } catch (NumberFormatException e) {
                            showAlert("Error", "Formato incorrecto. Use números para peso.");
                        }
                    });
                } catch (NumberFormatException e) {
                    showAlert("Error", "Formato incorrecto. Use números para repeticiones.");
                }
            });
        } else {
            showAlert("Error", "Seleccione un ejercicio.");
        }
    }

    private void guardarHistorial(String nombreEjercicio, HistorialEjercicio historialEjercicio) {
        String email = correoUsuario;
        String fileName = email + "_historial.json";
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(fileName);
        HistorialesEjercicios historialesEjercicios;

        try {
            if (file.exists() && file.length() > 0) {
                try {
                    historialesEjercicios = mapper.readValue(file, HistorialesEjercicios.class);
                } catch (IOException e) {
                    historialesEjercicios = new HistorialesEjercicios(email, nombreEjercicio, new ArrayList<>());
                }
            } else {
                historialesEjercicios = new HistorialesEjercicios(email, nombreEjercicio, new ArrayList<>());
            }

            historialesEjercicios.getHistorialEjercicios().add(historialEjercicio);
            mapper.writeValue(file, historialesEjercicios);
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace for debugging
            showAlert("Error", "No se pudo guardar el historial. Detalles: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MostrarRutinas.fxml"));
            Parent root = loader.load();

            MostrarRutinasController controller = loader.getController();
            controller.setDatosUsuario(dniUsuario, correoUsuario, contrasenaUsuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void colocarImagenBotones(){
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }

    public void setDatosUsuario(String dniUsuario, String correoUsuario, String contrasenaUsuario) {
        this.dniUsuario = dniUsuario;
        this.correoUsuario = correoUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
    }
}