package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Atleta.Ejercicio;
import com.example.proyectoalpha.servicios.ServicioEjercicios;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EjerciciosUsuarioController {

    @FXML
    private Button BtnBuscar;

    @FXML
    private Button BtnVolver;

    @FXML
    private ChoiceBox<String> ChoiceGrupoMuscular;

    @FXML
    private ImageView ImgAlpha;

    private ServicioEjercicios servicioEjercicios;

    private String dniUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;

    @FXML
    public void initialize() {
        servicioEjercicios = new ServicioEjercicios();
        Set<String> gruposMusculares = servicioEjercicios.obtenerGruposMusculares();
        ChoiceGrupoMuscular.getItems().addAll(gruposMusculares);
        BtnVolver.setOnAction(event -> manejarVolver());
        BtnBuscar.setOnAction(event -> manejarBuscar());
        colocarImagenBotones();
    }

    public void setDatosUsuario(String dni, String correo, String contrasena) {
        this.dniUsuario = dni;
        this.correoUsuario = correo;
        this.contrasenaUsuario = contrasena;
    }

    @FXML
    private void manejarBuscar() {
        String selectedGrupoMuscular = ChoiceGrupoMuscular.getValue();
        if (selectedGrupoMuscular != null) {
            List<Ejercicio> ejerciciosFiltrados = servicioEjercicios.obtenerEjercicios(selectedGrupoMuscular);
            List<String> nombresEjercicios = ejerciciosFiltrados.stream()
                    .map(Ejercicio::getNombre)
                    .collect(Collectors.toList());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MostrarEjercicios.fxml"));
                Parent root = loader.load();
                MostrarEjerciciosController controller = loader.getController();
                controller.setEjercicios(nombresEjercicios);

                controller.setDatosUsuario(dniUsuario, correoUsuario, contrasenaUsuario);

                Stage stage = (Stage) BtnBuscar.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MenuAtleta.fxml"));
            Parent root = loader.load();

            MenuAtletaController controller = loader.getController();
            controller.setDatosUsuario(dniUsuario, correoUsuario, contrasenaUsuario);

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