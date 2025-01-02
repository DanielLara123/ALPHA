package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Atleta.Rutina;
import com.example.proyectoalpha.controller.ConfirmacionController;
import com.example.proyectoalpha.servicios.ServicioRutinas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MostrarRutinasController {

    @FXML
    private Button BtnEliminarRutina;

    @FXML
    private Button BtnVerRutina;

    @FXML
    private Button BtnVolver;

    @FXML
    private Label LblMensaje;

    @FXML
    private ListView<String> ListViewRutinas;

    private String dniUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;

    private ObservableList<String> rutinaList;
    private ServicioRutinas servicioRutinas;

    @FXML
    public void initialize() {
        servicioRutinas = new ServicioRutinas();
        BtnVerRutina.setOnAction(event -> manejarVerRutina());
        BtnEliminarRutina.setOnAction(event -> manejarEliminarRutina());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();

    }

    private void manejarVerRutina() {
        String selectedItem = ListViewRutinas.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/VerRutinas.fxml"));
                Parent root = loader.load();

                VerRutinasController controller = loader.getController();
                Rutina selectedRutina = servicioRutinas.getRutinaByName(correoUsuario, selectedItem.split(" - ")[0]);
                controller.setDatosUsuario(dniUsuario, correoUsuario, contrasenaUsuario);
                controller.setRutina(selectedRutina);

                Stage stage = (Stage) BtnVolver.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showMessage("Por favor, seleccione una rutina para visualizar.");
        }
    }

    private void manejarEliminarRutina() {

    }

    public void setDatosUsuario(String dni, String correo, String contrasena) {
        dniUsuario = dni;
        correoUsuario = correo;
        contrasenaUsuario = contrasena;
        loadRutinas();
    }

    private void loadRutinas() {
        try {
            List<Rutina> rutinas = servicioRutinas.loadRutinas(correoUsuario);
            rutinaList = FXCollections.observableArrayList();
            for (Rutina rutina : rutinas) {
                rutinaList.add(rutina.getNombre() + " - " + rutina.getAutor());
            }
            ListViewRutinas.setItems(rutinaList);
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Error: No se pudieron cargar las rutinas.");
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/RutinasUsuario.fxml"));
            Parent root = loader.load();

            RutinasUsuarioController controller = loader.getController();
            controller.setDatosUsuario(dniUsuario, correoUsuario, contrasenaUsuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void colocarImagenBotones(){
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }

    private void showMessage(String message) {
        LblMensaje.setText(message);
    }
}