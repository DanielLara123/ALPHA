package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.controller.ConfirmacionController;
import com.example.proyectoalpha.servicios.servicioUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ConfiguracionUsuarioController {

    @FXML
    private Button BtnCambiarDatosPersonales;

    @FXML
    private Button BtnDarseDeBaja;

    @FXML
    private Button BtnVolver;

    private String dniUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;


    @FXML
    private void initialize(){
        BtnCambiarDatosPersonales.setOnAction(event -> manejarCambiarDatosPersonales());
        BtnVolver.setOnAction(event -> manejarVolver());
        BtnDarseDeBaja.setOnAction(event -> manejarDarseDeBaja());
        colocarImagenBotones();
    }

    public void setDatosUsuario(String dni, String correo, String contrasena){
        dniUsuario = dni;
        correoUsuario = correo;
        contrasenaUsuario = contrasena;
    }


    private void colocarImagenBotones(){
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
            controller.setDatosUsuario(dniUsuario, correoUsuario, contrasenaUsuario);

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
            controller.setDatosUsuario(dniUsuario, correoUsuario, contrasenaUsuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarDarseDeBaja() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Confirmacion.fxml"));
            Parent root = loader.load();

            ConfirmacionController confirmacionController = loader.getController();
            confirmacionController.setMensaje("¿Está seguro de que quiere darse de baja?");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (confirmacionController.estaConfirmado()) {
                servicioUsuario servicioUsuario = new servicioUsuario();
                servicioUsuario.eliminarUsuario(correoUsuario);

                File historialFile = new File(correoUsuario + "_historial.json");
                File rutinasFile = new File(correoUsuario + "_rutinas.json");

                if (historialFile.exists()) {
                    historialFile.delete();
                }

                if (rutinasFile.exists()) {
                    rutinasFile.delete();
                }

                FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/TipoUsuario.fxml"));
                Parent mainRoot = mainLoader.load();
                Stage mainStage = (Stage) BtnDarseDeBaja.getScene().getWindow();
                mainStage.setScene(new Scene(mainRoot));
                mainStage.show();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
