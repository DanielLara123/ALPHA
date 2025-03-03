package com.example.proyectoalpha.controller.Atleta;

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
    private Label LblSetContrasena;

    @FXML
    private Label LblSetCorreo;

    @FXML
    private Label LblSetDni;

    //Por implementar
    @FXML
    private Label LblEntrenador;

    //Por implementar
    @FXML
    private Label LblGimnasio;

    //Por implementar
    @FXML
    private Label LblMedico;


    @FXML
    private Button BtnVolver;

    @FXML
    public void initialize() {
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    // Setea los datos del usuario en los labels
    public void setDatosUsuario(String dni, String correo, String contrasena) {
        LblSetDni.setText(dni);
        LblSetCorreo.setText(correo);
        LblSetContrasena.setText(contrasena);
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MenuAtleta.fxml"));
            Parent root = loader.load();

            MenuAtletaController controller = loader.getController();
            controller.setDatosUsuario(LblSetDni.getText(), LblSetCorreo.getText(), LblSetContrasena.getText());

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
