package com.example.proyectoalpha.controller.Administrador;
/*
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import com.example.proyectoalpha.controller.ConfirmacionController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/*
public class EliminarUsuarioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnVolver;

    // Por implementar
    @FXML
    private ChoiceBox<?> ChoiceBoxCorreoAtleta;

    @FXML
    private Label LblMensaje;

    private servicioUsuario servicioUsuario = new servicioUsuario();

    @FXML
    void initialize() {
       servicioUsuario = new servicioUsuario();

       BtnContinuar.setOnAction(event -> manejarContinuar());
       BtnVolver.setOnAction(event -> manejarVolver());
       colocarImagenBotones();
    }

    private void manejarContinuar() {
        String correo = ChoiceBoxCorreoAtleta.getValue().toString();

        if (correo.isEmpty()) {
            LblMensaje.setText("Por favor, ingrese un correo");
        } else if (!servicioUsuario.emailEstaRegistrado(correo)) {
            LblMensaje.setText("El correo ingresado no está registrado");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Confirmacion.fxml"));
                Parent root = loader.load();
                ConfirmacionController confirmacionController = loader.getController();
                confirmacionController.setMensaje("¿Está seguro de que quiere eliminar el usuario?");
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();

                if (confirmacionController.estaConfirmado()) {
                    servicioUsuario.eliminarUsuario(correo);

                    File saludFile = new File(correo + "_datosMedicos.json");
                    File historialFile = new File(correo + "_historial.json");
                    File rutinasFile = new File(correo + "_rutinas.json");

                    if (saludFile.exists()) {
                        saludFile.delete();
                    }

                    if (historialFile.exists()) {
                        historialFile.delete();
                    }

                    if (rutinasFile.exists()) {
                        rutinasFile.delete();
                    }

                    LblMensaje.setText("Usuario eliminado correctamente");
                } else {
                    LblMensaje.setText("Eliminación de usuario cancelada");
                }
            } catch (IOException e) {
                e.printStackTrace();
                LblMensaje.setText("Error al mostrar la confirmación");
            }
        }
    }

    private void manejarVolver(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/GestionUsuarios.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            LblMensaje.setText("Error al volver al menú");
        }
    }

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        if (volver != null) {
            Image imagenVolver = new Image(volver.toString(), 50, 50, false, true);
            BtnVolver.setGraphic(new ImageView(imagenVolver));
        } else {
            LblMensaje.setText("Error al cargar la imagen de volver");
        }
    }

}

 */
