package com.example.proyectoalpha.controller.Atleta;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CambiarDatosPersonalesController {

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnVolver;

    @FXML
    private TextField TextFieldContrasena;

    @FXML
    private TextField TextFieldCorreo;

    @FXML
    private TextField TextFieldGimnasio;

    private Usuario usuario;
    private MariaDBController mariaDBController = new MariaDBController();

    @FXML
    void initialize() {
        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnVolver.setOnAction(event -> manejarVolver());
        setImagenesBotones();
    }


    public void setDatosUsuario(Usuario usuario){
        this.usuario = usuario;
        TextFieldCorreo.setText(usuario.getCorreo());
        TextFieldContrasena.setText(usuario.getContrasena());
        TextFieldGimnasio.setText(usuario.getGimnasio());
    }

    public void manejarContinuar(){
        String nuevoCorreo = TextFieldCorreo.getText();
        String nuevaContrasena = TextFieldContrasena.getText();
        String nuevoGimnasio = TextFieldGimnasio.getText();

        boolean success = mariaDBController.actualizarDatosUsuario(usuario.getID(), nuevoCorreo, nuevaContrasena, nuevoGimnasio);
        if (success) {
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Información");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("Datos actualizados exitosamente.");
            infoAlert.showAndWait();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Hubo un error al actualizar los datos. Por favor, inténtalo de nuevo.");
            errorAlert.showAndWait();
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/ConfiguracionUsuario.fxml"));
            Parent root = loader.load();

            ConfiguracionUsuarioController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImagenesBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }


}
