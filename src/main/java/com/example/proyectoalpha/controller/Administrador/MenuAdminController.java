package com.example.proyectoalpha.controller.Administrador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.proyectoalpha.clases.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MenuAdminController {

    @FXML
    private Button BtnCerrarSesion;

    @FXML
    private Button BtnControlAfluencia;

    @FXML
    private Button BtnGestionUsuarios;

    private Usuario usuario;

    @FXML
    void initialize() {
        BtnCerrarSesion.setOnAction(event -> manejarCerrarSesion());
        BtnGestionUsuarios.setOnAction(event -> manejarGestionUsuarios());
        BtnControlAfluencia.setOnAction(event -> manejarControlAfluencia());

        colocarImagenBotones();
    }

    private void manejarControlAfluencia() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/ControlAfluencia.fxml"));
            Parent root = loader.load();

            ControlAfluenciaController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnControlAfluencia.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarGestionUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/GestionUsuarios.fxml"));
            Parent root = loader.load();

            GestionUsuariosController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnGestionUsuarios.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private void manejarCerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/TipoUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnCerrarSesion.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void colocarImagenBotones() {
        colocarImagenBoton(BtnGestionUsuarios, "/images/FotoGestionUsuarios.png", 200, 200);
        colocarImagenBoton(BtnControlAfluencia, "/images/FotoControlAfluencia.png", 200, 200);
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