package com.example.proyectoalpha.controller.Administrador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.example.proyectoalpha.clases.Usuario;

public class MostrarUsuarioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnVolver;

    @FXML
    private Label LblMessage;

    @FXML
    void initialize() {
        BtnVolver.setOnAction(event -> closeWindow());
    }

    public void setUsuario(Usuario usuario) {
        LblMessage.setText("Correo: " + usuario.getCorreo() + "\nContraseña: " + usuario.getContrasena() + "\nRol: " + usuario.getTipoUsuario() + "\nDNI: " + usuario.getDni());
    }

    private void closeWindow() {
        BtnVolver.getScene().getWindow().hide();
    }
}