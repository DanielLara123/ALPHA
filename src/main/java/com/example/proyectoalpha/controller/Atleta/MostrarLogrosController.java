package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Entrenamiento;
import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MostrarLogrosController {

    @FXML
    private Button BtnVolver;

    @FXML
    private ListView<String> ListViewLogros;

    @FXML
    private Label LblMensaje;

    private Usuario usuario;
    private MariaDBController mariaDBController = new MariaDBController();

    @FXML
    private void initialize() {
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
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

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");
        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);
        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }

    public void setDatos(String selectedEjercicio, String selectedGrupoMuscular, Usuario usuario) {
        this.usuario = usuario;
        List<Entrenamiento> logros = mariaDBController.obtenerLogros(selectedEjercicio, selectedGrupoMuscular, usuario.getID());
        logros.sort((e1, e2) -> e2.getFechaEntrenamiento().compareTo(e1.getFechaEntrenamiento())); // Sort by date in descending order
        ListViewLogros.getItems().clear();
        for (Entrenamiento logro : logros) {
            ListViewLogros.getItems().add(logro.toString());
        }
    }
}