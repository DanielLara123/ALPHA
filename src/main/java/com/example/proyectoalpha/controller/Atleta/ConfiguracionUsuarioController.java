package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.*;
import com.example.proyectoalpha.controller.ConfirmacionController;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;


public class ConfiguracionUsuarioController {

    @FXML
    private Button BtnCambiarDatosPersonales;

    @FXML
    private Button BtnDarseDeBaja;

    @FXML
    private Button BtnVolver;

    private Usuario usuario;
    private MariaDBController mariaDBController = new MariaDBController();


    @FXML
    private void initialize() {
        BtnCambiarDatosPersonales.setOnAction(event -> manejarCambiarDatosPersonales());
        BtnVolver.setOnAction(event -> manejarVolver());
        BtnDarseDeBaja.setOnAction(event -> manejarDarseDeBaja());
        colocarImagenBotones();
    }

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    private void colocarImagenBotones() {
        URL cambiardatospersonales = getClass().getResource("/images/CambiarDatosPersonales.png");
        URL darsedebaja = getClass().getResource("/images/DarseDeBaja.png");
        URL volver = getClass().getResource("/images/VolverAtras.png");


        Image imagenCambiarDatosPersonales = new Image(String.valueOf(cambiardatospersonales), 200, 200, false, true);
        Image imagenDarseDeBaja = new Image(String.valueOf(darsedebaja), 200, 200, false, true);
        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnCambiarDatosPersonales.setGraphic(new ImageView(imagenCambiarDatosPersonales));
        BtnDarseDeBaja.setGraphic(new ImageView(imagenDarseDeBaja));
        BtnVolver.setGraphic(new ImageView(imagenVolver));

    }

    private void manejarCambiarDatosPersonales() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/CambiarDatosPersonales.fxml"));
            Parent root = loader.load();

            CambiarDatosPersonalesController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnCambiarDatosPersonales.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MenuAtleta.fxml"));
            Parent root = loader.load();

            MenuAtletaController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarDarseDeBaja() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("Darse de baja");
        alert.setContentText("¿Estás seguro de que deseas darte de baja? Esta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = mariaDBController.eliminarUsuario(usuario.getID());
            if (success) {
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Información");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("Te has dado de baja exitosamente.");
                infoAlert.showAndWait();

                // Redirect to the login screen or close the application
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/TipoUsuario.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) BtnDarseDeBaja.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Hubo un error al intentar darte de baja. Por favor, inténtalo de nuevo.");
                errorAlert.showAndWait();
            }
        }
    }
}