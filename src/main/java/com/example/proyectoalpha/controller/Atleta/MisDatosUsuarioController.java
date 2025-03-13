package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MisDatosUsuarioController {

    @FXML
    private Button BtnVolver;

    @FXML
    private Label LblApellidos;

    @FXML
    private Label LblNombre;

    @FXML
    private Label LblSetDni;

    @FXML
    private Label LblGimnasio;

    @FXML
    private Label LblCorreo;

    @FXML
    private Label LblPassword;

    @FXML
    private Label LblTipoUsuario;

    private Usuario usuario;

    @FXML
    public void initialize() {
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
        mostrarDatosUsuario(usuario);
    }
    // Setea los datos del usuario en los labels
    public void mostrarDatosUsuario(Usuario usuario) {
        LblSetDni.setText(usuario.getDNI());
        LblCorreo.setText(usuario.getCorreo());
        LblPassword.setText(usuario.getContrasena());
        LblNombre.setText(usuario.getNombre());
        LblApellidos.setText(usuario.getApellidos());
        LblTipoUsuario.setText(usuario.getTipoUsuario());
        LblGimnasio.setText(usuario.getGimnasio());
    }


    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MenuAtleta.fxml"));
            Parent root = loader.load();

            MenuAtletaController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }

}


