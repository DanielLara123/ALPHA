package com.example.proyectoalpha.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmacionController {

    @FXML
    private Button BtnNo;

    @FXML
    private Button BtnSi;

    @FXML
    private Label LblMessage;

    private boolean confirmado;

    @FXML
    private void initialize() {
        BtnSi.setOnAction(event -> handleYes());
        BtnNo.setOnAction(event -> handleNo());
    }

    public void setMensaje(String mensaje) {
        LblMessage.setText(mensaje);
    }

    public boolean estaConfirmado() {
        return confirmado;
    }

    private void handleYes() {
        confirmado = true;
        closeWindow();
    }

    private void handleNo() {
        confirmado = false;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) BtnSi.getScene().getWindow();
        stage.close();
    }
}