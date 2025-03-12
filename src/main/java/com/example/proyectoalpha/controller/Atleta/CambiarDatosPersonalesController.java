package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CambiarDatosPersonalesController {

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnVolver;

    @FXML
    private TextField TextFieldContrasena;

    @FXML
    private TextField TextFieldCorreo;

    @FXML
    private TextField TextFieldGimnasio;

    private Usuario usuario;

    public void setDatosUsuario(Usuario usuario){
        this.usuario = usuario;
    }
}
