package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Rutina;
import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.servicios.MariaDBController;
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
import java.util.List;

public class MostrarRutinasUsuarioController {

    @FXML
    private ListView<String> listViewRutinas;

    @FXML
    private Button btnMostrar;

    @FXML
    private Button BtnVolver;

    private Usuario usuario;
    private MariaDBController mariaDBController = new MariaDBController();

    @FXML
    public void initialize() {
        BtnVolver.setOnAction(event -> manejarVolver());
        btnMostrar.setOnAction(event -> mostrarRutinas());
        listViewRutinas.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setTextAlignment(TextAlignment.CENTER);
                    setStyle("-fx-alignment: CENTER; -fx-text-fill: gold; -fx-font-size: 16px; -fx-background-color: #000000;");
                    if (isSelected()) {
                        setStyle("-fx-alignment: CENTER; -fx-text-fill: black; -fx-font-size: 16px; -fx-background-color: gold;");
                    }
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
    }

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
        cargarRutinas();
    }

    private void cargarRutinas() {
        List<Rutina> rutinas = mariaDBController.obtenerRutinasPorUsuario(usuario.getID());
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Rutina rutina : rutinas) {
            items.add(rutina.getNombre());
        }
        listViewRutinas.setItems(items);
    }

    private void mostrarRutinas() {
        String rutinaSeleccionada = listViewRutinas.getSelectionModel().getSelectedItem();
        if (rutinaSeleccionada == null) {
            mostrarError("Por favor, selecciona una rutina de la lista.");
            return;
        }
        Rutina rutina = mariaDBController.obtenerRutinaPorNombre(rutinaSeleccionada, usuario.getID());
        if (rutina == null) {
            mostrarError("No se encontraron detalles para la rutina seleccionada.");
            return;
        }
        StringBuilder detalles = new StringBuilder();
        detalles.append("Nombre de la rutina: ").append(rutina.getNombre()).append("\n\n");
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