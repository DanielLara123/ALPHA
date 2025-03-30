package com.example.proyectoalpha.controller.Administrador;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.example.proyectoalpha.clases.Usuario;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MostrarUsuarioController {


    @FXML
    private Button BtnVolver;

    @FXML
    private Label LblMessage;

    private Usuario usuario;
    private Usuario usuarioAVer;

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setCorreoUsuarioAVer(Usuario usuarioAVer) {
        this.usuarioAVer = usuarioAVer;
        mostrarDatosUsuario();
    }

    private void mostrarDatosUsuario() {
        if (usuarioAVer != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Nombre: ").append(usuarioAVer.getNombre()).append("\n");
            sb.append("Apellido: ").append(usuarioAVer.getApellidos()).append("\n");
            sb.append("Correo: ").append(usuarioAVer.getCorreo()).append("\n");
            sb.append("Contrasena: ").append(usuarioAVer.getContrasena()).append("\n");
            sb.append("DNI: ").append(usuarioAVer.getDNI()).append("\n");
            sb.append("Tipo de Usuario: ").append(usuarioAVer.getTipoUsuario()).append("\n");
            sb.append("Gimnasio: ").append(usuarioAVer.getGimnasio()).append("\n");
            LblMessage.setText(sb.toString());
        } else {
            LblMessage.setText("No se encontraron datos del usuario.");
        }
    }

    @FXML
    void initialize() {
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/GestionUsuarios.fxml"));
            Parent root = loader.load();

            GestionUsuariosController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        if (volver != null) {
            Image imagenVolver = new Image(volver.toString(), 50, 50, false, true);
            BtnVolver.setGraphic(new ImageView(imagenVolver));
        } else {
            LblMessage.setText("Error al cargar la imagen de volver");
        }
    }

}

