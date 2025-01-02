package com.example.proyectoalpha.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

public class ConfirmacionController {

    @FXML
    private DialogPane dialogPane;

    @FXML
    private Label mensajeLabel;

    private boolean confirmado;

    public void setMensaje(String mensaje) {
        mensajeLabel.setText(mensaje);
    }

    public boolean estaConfirmado() {
        return confirmado;
    }

    @FXML
    private void confirmar() {
        confirmado = true;
        dialogPane.getScene().getWindow().hide();
    }

    @FXML
    private void cancelar() {
        confirmado = false;
        dialogPane.getScene().getWindow().hide();
    }

    public DialogPane getDialogPane() {
        return dialogPane;
    }
}