package com.example.proyectoalpha.controller.Administrador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        colocarImagenBotones();
    }

    private void manejarVerUsuarios() {
        cargarEscena("/com/example/proyectoalpha/Administrador/VerUsuarios.fxml", BtnVerUsuarios);
    }

    private void manejarEliminarUsuario() {
        cargarEscena("/com/example/proyectoalpha/Administrador/EliminarUsuario.fxml", BtnEliminarUsuario);
    }

    private void manejarCrearUsuario() {
        cargarEscena("/com/example/proyectoalpha/Administrador/CrearUsuario.fxml", BtnCrearUsuario);
    }

    private void manejarActualizarUsuarios() {
        cargarEscena("/com/example/proyectoalpha/Administrador/ActualizarUsuarios.fxml", BtnActualizarUsuarios);
    }

    private void manejarVolver() {
        cargarEscena("/com/example/proyectoalpha/Administrador/MenuAdministrador.fxml", BtnVolver);
    }

    private void cargarEscena(String rutaFXML, Button boton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) boton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void colocarImagenBotones() {
        colocarImagenBoton(BtnCrearUsuario, "/images/FotoAñadirUsuario.png", 200, 200);
        colocarImagenBoton(BtnEliminarUsuario, "/images/FotoEliminarUsuario.png", 200, 200);
        colocarImagenBoton(BtnVerUsuarios, "/images/FotoVerUsuarios.png", 200, 200);
        colocarImagenBoton(BtnActualizarUsuarios, "/images/FotoActualizarUsuario.png", 200, 200);
        colocarImagenBoton(BtnVolver, "/images/VolverAtras.png", 50, 50);
    }

    private void colocarImagenBoton(Button boton, String rutaImagen, int ancho, int alto) {
        URL urlImagen = getClass().getResource(rutaImagen);
        if (urlImagen != null) {
            Image imagen = new Image(urlImagen.toString(), ancho, alto, false, true);
            boton.setGraphic(new ImageView(imagen));
        } else {
            System.err.println("Error al cargar la imagen: " + rutaImagen);
        }
    }
}