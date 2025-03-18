package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Ejercicio;
import com.example.proyectoalpha.clases.Rutina;
import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.servicios.MariaDBController;
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

import java.io.IOException;
import java.net.URL;
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

    private Usuario usuario;
    private MariaDBController mariaDBController = new MariaDBController();

    public void setRutina(Rutina selectedRutina) {
        this.rutina = selectedRutina;
        loadEjercicios();
    }

    private void loadEjercicios() {
        List<Ejercicio> ejerciciosList = mariaDBController.obtenerEjerciciosPorRutina(rutina.getID_rutina());
        if (ejerciciosList != null) {
            ejercicios = FXCollections.observableArrayList(ejerciciosList);
            tableViewEjercicios.setItems(ejercicios);
            System.out.println("Ejercicios loaded: " + ejercicios.size());
        } else {
            System.out.println("No ejercicios found for rutina ID: " + rutina.getID_rutina());
        }
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
                            // Save the series to the database or perform other actions
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
            controller.setDatosUsuario(usuario);

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

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}