package com.example.proyectoalpha.controller.RegistroInicioSesion;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.Administrador.MenuAdminController;
import com.example.proyectoalpha.controller.Atleta.MenuAtletaController;
import com.example.proyectoalpha.controller.Entrenador.MenuEntrenadorController;
import com.example.proyectoalpha.controller.Medico.MenuMedicoController;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class InicioSesionController {

    @FXML
    private Button BtnContinuar;

    @FXML
    private Button BtnNoTienesCuenta;

    @FXML
    private PasswordField LblContrasena;

    @FXML
    private TextField LblCorreo;

    @FXML
    private Label LblMessage;

    @FXML
    private Button BtnVolver;

    private MariaDBController mariaDBController;
    private String tipoUsuario;

    @FXML
    void initialize() {
        mariaDBController = new MariaDBController();

        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnNoTienesCuenta.setOnAction(event -> manejarNoTienesCuenta());
        BtnVolver.setOnAction(event -> manejarVolver());
        setImagenesBotones();
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    private void manejarContinuar() {
        String correo = LblCorreo.getText();
        String contrasenaIngresada = LblContrasena.getText();

        Usuario usuario = mariaDBController.obtenerUsuarioPorEmail(correo);
        if (usuario != null) {
            // Comparar la contraseña ingresada con el hash almacenado
            if (hashPassword(contrasenaIngresada).equals(usuario.getContrasena())) {
                if (normalize(usuario.getTipoUsuario()).equalsIgnoreCase(normalize(tipoUsuario))) {
                    switch (normalize(tipoUsuario).toLowerCase()) {
                        case "atleta":
                            MenuAtleta(usuario);
                            break;
                        case "medico":
                            MenuMedico(usuario);
                            break;
                        case "administrador":
                            MenuAdministrador(usuario);
                            break;
                        default:
                            MenuEntrenador(usuario);
                            break;
                    }
                } else {
                    LblMessage.setText("Tipo de usuario incorrecto");
                }
            } else {
                LblMessage.setText("Correo o contraseña incorrectos");
            }
        } else {
            LblMessage.setText("Correo o contraseña incorrectos");
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar el hash de la contraseña", e);
        }
    }

    private void manejarNoTienesCuenta() {
        if (!"Atleta".equalsIgnoreCase(tipoUsuario)) {
            LblMessage.setText("Solo los atletas pueden registrarse");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/RegistroInicioSesion/Registro.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) BtnNoTienesCuenta.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    private void MenuAtleta(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MenuAtleta.fxml"));
            Parent root = loader.load();

            MenuAtletaController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnContinuar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void MenuMedico(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Medico/MenuMedico.fxml"));
            Parent root = loader.load();

            MenuMedicoController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnContinuar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void MenuAdministrador(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/MenuAdmin.fxml"));
            Parent root = loader.load();

            MenuAdminController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnContinuar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void MenuEntrenador(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/MenuEntrenador.fxml"));
            Parent root = loader.load();

            MenuEntrenadorController controller = loader.getController();
            controller.setDatosUsuario(usuario);

            Stage stage = (Stage) BtnContinuar.getScene().getWindow();
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

    private String normalize(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
}