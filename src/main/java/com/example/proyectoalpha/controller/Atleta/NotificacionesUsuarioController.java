package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class NotificacionesUsuarioController {

    @FXML
    private Button BtnVolver;

    @FXML
    private Label LblMensaje;

    private Usuario usuario;
    private MariaDBController mariaDBController = new MariaDBController();

    @FXML
    private void initialize() {
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
        mostrarDiasDesdeUltimoEntrenamiento();
    }

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    private void mostrarDiasDesdeUltimoEntrenamiento() {
        LocalDate fechaUltimoEntrenamiento = mariaDBController.obtenerFechaUltimoEntrenamiento(usuario.getID());
        if (fechaUltimoEntrenamiento != null) {
            long diasPasados = ChronoUnit.DAYS.between(fechaUltimoEntrenamiento, LocalDate.now());
            LblMensaje.setText("Han pasado " + diasPasados + " días desde tu último entrenamiento.");
        } else {
            LblMensaje.setText("No se encontró información sobre tu último entrenamiento.");
        }
    }
}