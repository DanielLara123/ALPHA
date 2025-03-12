package com.example.proyectoalpha.controller.RegistroInicioSesion;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroController {

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnVolver;

    @FXML
    private Button BtnYaTienesCuenta;

    @FXML
    private Label LblMessage;

    @FXML
    private PasswordField PasswordFieldContrasena;

    @FXML
    private TextField TextFieldApellidos;

    @FXML
    private TextField TextFieldCorreo;

    @FXML
    private TextField TextFieldDNI;

    @FXML
    private TextField TextFieldGimnasio;

    @FXML
    private TextField TextFieldNombre;

    MariaDBController MariaDB = new MariaDBController();

    @FXML
    void initialize() {
        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnVolver.setOnAction(event -> manejarVolver());
        BtnYaTienesCuenta.setOnAction(event -> manejarYaTienesCuenta());
    }

    private void manejarYaTienesCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/RegistroInicioSesion/InicioSesion.fxml"));
            Parent root = loader.load();

            InicioSesionController controller = loader.getController();
            controller.setTipoUsuario("Atleta");

            Stage stage = (Stage) BtnYaTienesCuenta.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/TipoUsuario.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) BtnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarContinuar() {
        String nombre = TextFieldNombre.getText();
        String apellidos = TextFieldApellidos.getText();
        String correo = TextFieldCorreo.getText();
        String dni = TextFieldDNI.getText();
        String contrasena = PasswordFieldContrasena.getText();
        String gimnasio = TextFieldGimnasio.getText();

        if (nombre.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || dni.isEmpty() || contrasena.isEmpty() || gimnasio.isEmpty()) {
            LblMessage.setText("Por favor, rellene todos los campos");
        } else if (!correo.endsWith("@gmail.com")) {
            LblMessage.setText("Por favor, introduzca un correo de Gmail");
        } else if (MariaDB.emailEstaRegistrado(correo)) {
            LblMessage.setText("El correo ya esta registrado");
        } else {
            Usuario usuario = new Usuario(nombre, apellidos, contrasena, dni, correo, "Atleta", gimnasio);
            boolean registrado = MariaDB.registrarUsuario(usuario);
            if (registrado) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro exitoso");
                alert.setHeaderText(null);
                alert.setContentText("Usuario registrado correctamente");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al registrar");
                alert.setHeaderText(null);
                alert.setContentText("Error al registrar el usuario");
                alert.showAndWait();
            }
        }
    }

}
