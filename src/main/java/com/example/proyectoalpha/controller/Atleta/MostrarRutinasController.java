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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MostrarRutinasController {

    @FXML
    private Button BtnEliminarRutina;

    @FXML
    private Button BtnVerRutina;

    @FXML
    private Button BtnVolver;

    @FXML
    private Label LblMensaje;

    @FXML
    private ListView<String> ListViewRutinas;

    private Usuario usuario;
    private MariaDBController mariaDBController = new MariaDBController();

    @FXML
    public void initialize() {
        BtnVerRutina.setOnAction(event -> manejarVerRutina());
        BtnEliminarRutina.setOnAction(event -> manejarEliminarRutina());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
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
        ListViewRutinas.setItems(items);
    }

    private void manejarVerRutina() {
        String selectedItem = ListViewRutinas.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/VerRutinas.fxml"));
                Parent root = loader.load();

                VerRutinasController controller = loader.getController();
                controller.setDatosUsuario(usuario);

                Rutina rutina = mariaDBController.obtenerRutinaPorNombre(selectedItem, usuario.getID());
                if (rutina != null) {
                    controller.setRutina(rutina);
                }

                Stage stage = (Stage) BtnVolver.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showMessage("Por favor, seleccione una rutina para visualizar.");
        }
    }

    private void manejarEliminarRutina() {
        String selectedItem = ListViewRutinas.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Está seguro de que quiere eliminar la rutina?");
            alert.setContentText("Esta acción no se puede deshacer.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    boolean success = mariaDBController.eliminarRutinaPorNombre(selectedItem, usuario.getID());
                    if (success) {
                        showMessage("Rutina eliminada correctamente.");
                        cargarRutinas();
                    } else {
                        showMessage("Error al eliminar la rutina.");
                    }
                } else {
                    showMessage("Eliminación de rutina cancelada.");
                }
            });
        } else {
            showMessage("Por favor, seleccione una rutina para eliminar.");
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/RutinasUsuario.fxml"));
            Parent root = loader.load();

            RutinasUsuarioController controller = loader.getController();
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

    private void showMessage(String message) {
        LblMensaje.setText(message);
    }
}