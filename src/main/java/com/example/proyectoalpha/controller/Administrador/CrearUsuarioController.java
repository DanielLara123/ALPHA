package com.example.proyectoalpha.controller.Administrador;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CrearUsuarioController {

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnVolver;

    @FXML
    private ChoiceBox<String> ChoiceBoxRol;

    @FXML
    private TextField TextFieldApellidos;

    @FXML
    private TextField TextFieldContrasena;

    @FXML
    private TextField TextFieldCorreo;

    @FXML
    private TextField TextFieldDNI;

    @FXML
    private TextField TextFieldGimnasio;

    @FXML
    private TextField TextFieldNombre;

    private MariaDBController mariaDBController = new MariaDBController();
    private Usuario usuario;

    @FXML
    private void initialize() {
        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnVolver.setOnAction(event -> manejarVolver());
        ChoiceBoxRol.getItems().addAll("atleta", "entrenador", "médico", "administrador");
        colocarImagenBotones();
    }

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private void manejarContinuar() {
        Usuario usuario = new Usuario();
        usuario.setNombre(TextFieldNombre.getText());
        usuario.setApellidos(TextFieldApellidos.getText());
        usuario.setContrasena(TextFieldContrasena.getText());
        usuario.setDNI(TextFieldDNI.getText());
        usuario.setCorreo(TextFieldCorreo.getText());
        usuario.setTipoUsuario(ChoiceBoxRol.getValue());
        usuario.setGimnasio(TextFieldGimnasio.getText());

        boolean exito = mariaDBController.registrarUsuario(usuario);
        if (exito) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("Usuario registrado correctamente");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error al registrar el usuario");
            alert.showAndWait();
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/GestionUsuarios.fxml"));
            Parent root = loader.load();

            GestionUsuariosController controller = loader.getController();
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