package com.example.proyectoalpha.controller.Medico;

import com.example.proyectoalpha.clases.Sensor;
import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.Atleta.MenuAtletaController;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class HistorialMedicoController {

    @FXML
    private Button BtnEmergencia;

    @FXML
    private Button BtnMostrar;

    @FXML
    private Button BtnVolver;

    @FXML
    private TextField TextFieldAtleta;

    @FXML
    private Label LblFC;

    @FXML
    private Label LblGpsData;

    @FXML
    private Label LblO2;

    private Usuario usuario;
    private MariaDBController mariaDBController = new MariaDBController();

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    private void initialize() {
        BtnMostrar.setOnAction(event -> mostrarDatosAtleta());
        BtnEmergencia.setOnAction(event -> manejarEmergencia());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    private void manejarEmergencia() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Emergencia");
        alert.setHeaderText(null);
        alert.setContentText("Llamando a 112");
        alert.showAndWait();

    }

    private void mostrarDatosAtleta() {
        String correo = TextFieldAtleta.getText();
        Usuario usuario = mariaDBController.obtenerUsuarioPorCorreo(correo);
        if (usuario != null && "Atleta".equalsIgnoreCase(usuario.getTipoUsuario())) {
            List<Sensor> sensores = mariaDBController.obtenerDatosSensorPorUsuario(usuario.getID());
            if (!sensores.isEmpty()) {
                for (Sensor sensor : sensores) {
                    switch (sensor.getTipoDato()) {
                        case "Frecuencia Cardiaca":
                            LblFC.setText(sensor.getValor());
                            break;
                        case "Oxigenacion en Sangre":
                            LblO2.setText(sensor.getValor());
                            break;
                        case "Ubicacion":
                            LblGpsData.setText(sensor.getValor());
                            break;
                    }
                }
            } else {
                LblFC.setText("No encontrado");
                LblGpsData.setText("No encontrado");
                LblO2.setText("No encontrado");
            }
        } else {
            LblFC.setText("No encontrado");
            LblGpsData.setText("No encontrado");
            LblO2.setText("No encontrado");
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Medico/MenuMedico.fxml"));
            Parent root = loader.load();

            MenuMedicoController controller = loader.getController();
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