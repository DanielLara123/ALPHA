package com.example.proyectoalpha.controller.Administrador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EliminarUsuarioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnVolver;

    @FXML
    private TextField FieldCorreo;

    @FXML
    private Label LblMensaje;

    @FXML
    void initialize() {
        assert BtnContinuar != null : "fx:id=\"BtnContinuar\" was not injected: check your FXML file 'EliminarUsuario.fxml'.";
        assert BtnVolver != null : "fx:id=\"BtnVolver\" was not injected: check your FXML file 'EliminarUsuario.fxml'.";
        assert FieldCorreo != null : "fx:id=\"FieldCorreo\" was not injected: check your FXML file 'EliminarUsuario.fxml'.";
        assert LblMensaje != null : "fx:id=\"LblMensaje\" was not injected: check your FXML file 'EliminarUsuario.fxml'.";

    }

}
