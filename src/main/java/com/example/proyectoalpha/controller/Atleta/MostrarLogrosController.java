package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Usuario;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MostrarLogrosController {

    @FXML
    private Button BtnVolver;

    @FXML
    private ListView<String> ListViewLogros;

    @FXML
    private Label LblMensaje;

    private Usuario usuario;
    private String ejercicio;

    private static final String URL_DB = "jdbc:mariadb://localhost:3306/tu_base_de_datos";
    private static final String USUARIO_DB = "tu_usuario";
    private static final String PASSWORD_DB = "tu_contraseña";

    @FXML
    private void initialize() {
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
        cargarEjercicios();
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

    private void cargarEjercicios() {
        ListViewLogros.getItems().clear(); // Limpiar la lista antes de agregar datos

        try (Connection conexion = DriverManager.getConnection(URL_DB, USUARIO_DB, PASSWORD_DB)) {
            String consulta = "SELECT e.nombre AS ejercicio, s.fecha, s.duracion " +
                    "FROM ejercicios e " +
                    "JOIN sesiones s ON e.id = s.ejercicio_id " +
                    "WHERE e.nombre = ?";

            try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
                statement.setString(1, ejercicio);
                ResultSet resultado = statement.executeQuery();

                while (resultado.next()) {
                    String nombreEjercicio = resultado.getString("ejercicio");
                    String fecha = resultado.getString("fecha");
                    String duracion = resultado.getString("duracion");

                    String item = "Ejercicio: " + nombreEjercicio + " | Fecha: " + fecha + " | Duración: " + duracion;
                    ListViewLogros.getItems().add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LblMensaje.setText("Error al cargar los datos.");
        }
    }
}
