package com.example.proyectoalpha.controller.Atleta;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class RutinasUsuarioController {

    @FXML
    private Button BtnCrearRutina;

    @FXML
    private Button BtnMisRutinas;

    @FXML
    private Button BtnVolver;

    private String dniUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;

    @FXML
    public void initialize() {
        BtnMisRutinas.setOnAction(event -> mostrarRutinas());
        BtnCrearRutina.setOnAction(event -> crearRutina());
        BtnVolver.setOnAction(event -> volver());
    }

    private void mostrarRutinas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MostrarRutinas.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnMisRutinas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crearRutina() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/CrearRutina.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnCrearRutina.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/MenuAtleta.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDatosUsuario(String dniUsuario, String correoUsuario, String contrasenaUsuario) {
        this.dniUsuario = dniUsuario;
        this.correoUsuario = correoUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
    }
}