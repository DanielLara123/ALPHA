package com.example.proyectoalpha.controller.Administrador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.Medico.MenuMedicoController;
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

    private Usuario usuario;

    @FXML
    void initialize() {
        BtnActualizarUsuarios.setOnAction(event -> manejarActualizarUsuarios());
        BtnVolver.setOnAction(event -> manejarVolver());
        BtnCrearUsuario.setOnAction(event -> manejarCrearUsuario());
        BtnEliminarUsuario.setOnAction(event -> manejarEliminarUsuario());
        BtnVerUsuarios.setOnAction(event -> manejarVerUsuarios());

        colocarImagenBotones();
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/MenuAdmin.fxml"));
            Parent root = loader.load();

            MenuAdminController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void manejarVerUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/VerUsuarios.fxml"));
            Parent root = loader.load();

            VerUsuariosController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnVerUsuarios.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarEliminarUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/EliminarUsuario.fxml"));
            Parent root = loader.load();

            EliminarUsuarioController controller = loader.getController();
            controller.setUsuario(usuario);

            Stage stage = (Stage) BtnEliminarUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarCrearUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/CrearUsuario.fxml"));
            Parent root = loader.load();

            CrearUsuarioController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnCrearUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarActualizarUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/ActualizarUsuarios.fxml"));
            Parent root = loader.load();

            ActualizarUsuariosController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnActualizarUsuarios.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}