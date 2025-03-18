package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Ejercicio;
import com.example.proyectoalpha.clases.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class LogrosUsuarioController {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/tubasedatos";
    private static final String USER = "tuusuario";
    private static final String PASS = "tucontraseña";

    @FXML
    private Button BtnBuscar;

    @FXML
    private Button BtnVolver;

    @FXML
    private ChoiceBox<String> ChoiceEjercicio;

    @FXML
    private ChoiceBox<String> ChoiceGrupoMuscular;

    @FXML
    private Label LblMensaje;

    private Usuario usuario;

    @FXML
    private void initialize() {
        BtnBuscar.setOnAction(event -> manejarContinuar());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
        cargarGruposMusculares();

        // Agregar listener para cargar ejercicios cuando se seleccione un grupo muscular
        ChoiceGrupoMuscular.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarEjerciciosPorGrupo(newValue);
            }
        });
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

    private void manejarContinuar() {
        if (verificarSeleccion()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MostrarLogros.fxml"));
                Parent root = loader.load();

                MostrarLogrosController controller = loader.getController();
                controller.setDatosUsuario(usuario);
                controller.setEjercicio(ChoiceEjercicio.getValue());

                Stage stage = (Stage) BtnBuscar.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LblMensaje.setText("Seleccione un grupo muscular y un ejercicio.");
        }
    }

    private boolean verificarSeleccion() {
        return ChoiceGrupoMuscular.getValue() != null && ChoiceEjercicio.getValue() != null;
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }

    // 🔥 Cargar los grupos musculares en el ChoiceBox
    private void cargarGruposMusculares() {
        System.out.println("🔎 Cargando grupos musculares...");

        Set<String> gruposMusculares = new HashSet<>();
        String query = "SELECT DISTINCT grupo_muscular FROM Ejercicio";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String grupo = rs.getString("grupo_muscular");
                gruposMusculares.add(grupo);
                System.out.println("✅ Grupo muscular encontrado: " + grupo);
            }

            // Cargar datos en el ChoiceBox
            ChoiceGrupoMuscular.setItems(FXCollections.observableArrayList(gruposMusculares));

        } catch (SQLException e) {
            System.err.println("❌ Error al cargar grupos musculares");
            e.printStackTrace();
        }
    }

    // 🔥 Cargar los ejercicios en el ChoiceBox según el grupo muscular seleccionado
    private void cargarEjerciciosPorGrupo(String grupoMuscular) {
        System.out.println("🔄 Cargando ejercicios para el grupo muscular: " + grupoMuscular);

        List<String> nombresEjercicios = new ArrayList<>();
        String query = "SELECT nombre FROM Ejercicio WHERE grupo_muscular = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, grupoMuscular);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String nombreEjercicio = rs.getString("nombre");
                nombresEjercicios.add(nombreEjercicio);
                System.out.println("✅ Ejercicio encontrado: " + nombreEjercicio);
            }

            // Cargar datos en el ChoiceBox
            ChoiceEjercicio.setItems(FXCollections.observableArrayList(nombresEjercicios));

        } catch (SQLException e) {
            System.err.println("❌ Error al cargar ejercicios");
            e.printStackTrace();
        }
    }
}
