package com.example.proyectoalpha.controller.Administrador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GestionUsuariosController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnActualizarUsuarios;

    @FXML
    private Button BtnCrearUsuario;

    @FXML
    private Button BtnEliminarUsuario;

    @FXML
    private Button BtnVerUsuarios;

    @FXML
    private Button BtnVolver;

    @FXML
    void initialize() {

        BtnActualizarUsuarios.setOnAction(event -> manejarActualizarUsuarios());
        BtnVolver.setOnAction(event -> manejarVolver());
        BtnCrearUsuario.setOnAction(event -> manejarCrearUsuario());
        BtnEliminarUsuario.setOnAction(event -> manejarEliminarUsuario());
        BtnVerUsuarios.setOnAction(event -> manejarVerUsuarios());

    }

    private void manejarVerUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/VerUsuarios.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnVerUsuarios.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarEliminarUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/EliminarUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnEliminarUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarCrearUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/CrearUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnCrearUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarActualizarUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/ActualizarUsuarios.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnActualizarUsuarios.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/MenuAdministrador.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
