package com.example.proyectoalpha.controller.Administrador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.ConfirmacionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.example.proyectoalpha.servicios.servicioUsuario;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CrearUsuarioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnVolver;

    @FXML
    private TextField FieldContrasena;

    @FXML
    private TextField FieldCorreo;

    @FXML
    private TextField FieldDni;

    @FXML
    private ChoiceBox<String> FieldRol;

    @FXML
    private Label LblMensaje;

    private servicioUsuario servicioUsuario;

    @FXML
    void initialize() {
        servicioUsuario = new servicioUsuario();

        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }


    private void manejarContinuar() {
        String correo = FieldCorreo.getText();
        String contrasena = FieldContrasena.getText();
        String dni = FieldDni.getText();
        String tipoUsuario = FieldRol.getValue();

        if (correo.isEmpty() || contrasena.isEmpty() || tipoUsuario.isEmpty()) {
            LblMensaje.setText("Los campos correo, contraseña y rol son obligatorios");
            return;
        }

        if(servicioUsuario.emailEstaRegistrado(correo)){
            LblMensaje.setText("El correo ya está registrado");
            return;
        }

        if(!correo.endsWith("@gmail.com")){
            LblMensaje.setText("El correo debe terminar en @gmail.com");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Confirmacion.fxml"));
            Parent root = loader.load();
            ConfirmacionController confirmacionController = loader.getController();
            confirmacionController.setMensaje("¿Estás seguro de que desea crear el usuario?");
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (confirmacionController.estaConfirmado()) {
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setCorreo(correo);
                nuevoUsuario.setContrasena(contrasena);
                nuevoUsuario.setDni(dni);
                nuevoUsuario.setTipoUsuario(tipoUsuario);

                servicioUsuario.registrarUsuario(nuevoUsuario);
                LblMensaje.setText("Usuario creado correctamente");
            } else {
                LblMensaje.setText("Creación de usuario cancelada");
            }
        } catch (IOException e) {
            e.printStackTrace();
            LblMensaje.setText("Error al mostrar la confirmación");
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/GestionUsuarios.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }

}
