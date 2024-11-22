package com.example.proyectoalpha.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ConfirmacionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnNo;

    @FXML
    private Button BtnSi;

    @FXML
    private Label LblMessage;

    private boolean confirmado = false;

    public void setMensaje(String s) {
        LblMessage.setText(s);
    }

    @FXML
    void initialize() {
        BtnSi.setOnAction(event -> {
            confirmado = true;
            closeWindow();
        });

        BtnNo.setOnAction(event -> {
            confirmado = false;
            closeWindow();
        });
    }

    public boolean estaConfirmado() {
        return confirmado;
    }

    private void closeWindow() {
        BtnNo.getScene().getWindow().hide();
    }
}
