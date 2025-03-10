package com.example.proyectoalpha.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.example.proyectoalpha.controller.RegistroInicioSesion.InicioSesionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TipoUsuarioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnAdministrador;

    @FXML
    private Button BtnAtleta;

    @FXML
    private Button BtnEntrenador;

    @FXML
    private Button BtnMedico;

    @FXML
    void initialize() {
        BtnAdministrador.setOnAction(event -> loadInicioSesion("Administrador"));
        BtnAtleta.setOnAction(event -> loadInicioSesion("Atleta"));
        BtnEntrenador.setOnAction(event -> loadInicioSesion("Entrenador"));
        BtnMedico.setOnAction(event -> loadInicioSesion("Medico"));

        colocarImagenBotones();
    }

    private void loadInicioSesion(String tipoUsuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/RegistroInicioSesion/InicioSesion.fxml"));
            Parent root = loader.load();

            InicioSesionController inicioSesionController = loader.getController();
            inicioSesionController.setTipoUsuario(tipoUsuario);

            Stage stage = (Stage) BtnAdministrador.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void colocarImagenBotones(){
        URL medico = getClass().getResource("/images/Medico.png");
        URL admin = getClass().getResource("/images/Administrador.png");
        URL deportista = getClass().getResource("/images/Deportista.png");
        URL entrenador = getClass().getResource("/images/Entrenador.png");

        Image imagenMedico = new Image(String.valueOf(medico), 200, 200, false, true);
        Image imagenAdmin = new Image(String.valueOf(admin), 200, 200, false, true);
        Image imagenDeportista = new Image(String.valueOf(deportista), 200, 200, false, true);
        Image imagenEntrenador = new Image(String.valueOf(entrenador), 200, 200, false, true);

        BtnMedico.setGraphic(new ImageView(imagenMedico));
        BtnAdministrador.setGraphic(new ImageView(imagenAdmin));
        BtnEntrenador.setGraphic(new ImageView(imagenEntrenador));
        BtnAtleta.setGraphic(new ImageView(imagenDeportista));
    }
}

