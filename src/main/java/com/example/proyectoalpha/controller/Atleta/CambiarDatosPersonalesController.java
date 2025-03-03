package com.example.proyectoalpha.controller.Atleta;
/*
import com.example.proyectoalpha.controller.ConfirmacionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/*
public class CambiarDatosPersonalesController {

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnVolver;

    @FXML
    private TextField FieldContrasena;

    @FXML
    private TextField FieldCorreo;

    // Por implementar
    @FXML
    private ChoiceBox<?> ChoiceSeleccionarGimnasio;

    @FXML
    private TextField FieldDni;

    @FXML
    private Label LblMensaje;

    private String dniUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;

    private servicioUsuario servicioUsuario = new servicioUsuario();

    @FXML
    public void initialize() {
        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnVolver.setOnAction(event -> manejarVolver());
        colocarImagenBotones();
    }

    public void setDatosUsuario(String dni, String correo, String contrasena) {
        dniUsuario = dni;
        correoUsuario = correo;
        contrasenaUsuario = contrasena;
    }

    private void manejarContinuar() {
        String nuevoCorreo = FieldCorreo.getText().trim();
        String nuevaContrasena = FieldContrasena.getText().trim();
        String nuevoDni = FieldDni.getText().trim();

        if (nuevoCorreo.isEmpty() || nuevaContrasena.isEmpty() || nuevoDni.isEmpty()) {
            LblMensaje.setText("Los campos correo, contraseña y DNI son obligatorios");
        } else if (servicioUsuario.emailEstaRegistrado(nuevoCorreo) && !nuevoCorreo.equals(correoUsuario)) {
            LblMensaje.setText("El correo ya está registrado");
        } else if (!nuevoCorreo.endsWith("@gmail.com")) {
            LblMensaje.setText("El correo debe terminar en @gmail.com");
        } else if (!validarFormatoDni(nuevoDni)) {
            LblMensaje.setText("El formato del DNI es incorrecto");
        } else if (servicioUsuario.dniEstaRegistrado(nuevoDni)) {
            LblMensaje.setText("El DNI ya está registrado");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Confirmacion.fxml"));
                Parent root = loader.load();
                ConfirmacionController confirmacionController = loader.getController();
                confirmacionController.setMensaje("¿Está seguro de que quiere actualizar el usuario?");
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.showAndWait();

                if (confirmacionController.estaConfirmado()) {
                    // Rename JSON files if they exist
                    File datosMedicosFile = new File(correoUsuario + "_datosMedicos.json");
                    File historialFile = new File(correoUsuario + "_historial.json");
                    File rutinasFile = new File(correoUsuario + "_rutinas.json");

                    if (datosMedicosFile.exists()) {
                        datosMedicosFile.renameTo(new File(nuevoCorreo + "_datosMedicos.json"));
                    }

                    if (historialFile.exists()) {
                        historialFile.renameTo(new File(nuevoCorreo + "_historial.json"));
                    }

                    if (rutinasFile.exists()) {
                        rutinasFile.renameTo(new File(nuevoCorreo + "_rutinas.json"));
                    }

                    // Update user data
                    servicioUsuario.actualizarUsuario(correoUsuario, nuevoCorreo, nuevaContrasena, nuevoDni);
                    setDatosUsuario(nuevoDni, nuevoCorreo, nuevaContrasena); // Update the user data in the controller
                    LblMensaje.setText("Usuario actualizado correctamente");
                } else {
                    LblMensaje.setText("Actualización de usuario cancelada");
                }
            } catch (IOException e) {
                e.printStackTrace();
                LblMensaje.setText("Error al mostrar la confirmación");
            }
        }
    }

    private boolean validarFormatoDni(String dni) {
        return dni.matches("\\d{8}[A-HJ-NP-TV-Z]");
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/ConfiguracionUsuario.fxml"));
            Parent root = loader.load();

            ConfiguracionUsuarioController controller = loader.getController();
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

 */