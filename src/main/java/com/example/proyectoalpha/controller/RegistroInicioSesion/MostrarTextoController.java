package com.example.proyectoalpha.controller.RegistroInicioSesion;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MostrarTextoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label LblMessage;

    @FXML
    private Button BtnContinuar;

    private String tipoUsuario;


    public void setMessage(String message) {
        LblMessage.setText(message);
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @FXML
    void initialize() {

        BtnContinuar.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/RegistroInicioSesion/InicioSesion.fxml"));
                Parent root = loader.load();
                InicioSesionController inicioSesionController = loader.getController();
                inicioSesionController.setTipoUsuario(tipoUsuario);
                Stage stage = (Stage) BtnContinuar.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
