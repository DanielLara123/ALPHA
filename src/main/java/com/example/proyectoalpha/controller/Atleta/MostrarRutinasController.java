package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Rutina;
import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.ConfirmacionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
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

    @FXML
    public void initialize() {
        BtnVerRutina.setOnAction(event -> manejarVerRutina());
        BtnEliminarRutina.setOnAction(event -> manejarEliminarRutina());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    private void manejarVerRutina() {
        String selectedItem = ListViewRutinas.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/VerRutinas.fxml"));
                Parent root = loader.load();

                VerRutinasController controller = loader.getController();
                controller.setDatosUsuario(usuario);

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
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Confirmacion.fxml"));
                Parent root = loader.load();
                ConfirmacionController confirmacionController = loader.getController();
                confirmacionController.setMensaje("¿Está seguro de que quiere eliminar la rutina?");
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.showAndWait();

                if (confirmacionController.estaConfirmado()) {
                    String rutinaName = selectedItem.split(" - ")[0];
                    showMessage("Rutina eliminada correctamente.");
                } else {
                    showMessage("Eliminación de rutina cancelada.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                showMessage("Error al mostrar la confirmación.");
            }
        } else {
            showMessage("Por favor, seleccione una rutina para eliminar.");
        }
    }

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
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
