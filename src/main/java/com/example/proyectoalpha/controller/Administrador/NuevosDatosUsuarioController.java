package com.example.proyectoalpha.controller.Administrador;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.Atleta.ConfiguracionUsuarioController;
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

public class NuevosDatosUsuarioController {

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
    private TextField TextFieldGimnasio;

    @FXML
    private TextField TextFieldNombre;

    @FXML
    private TextField TextFieldDNI;

    private MariaDBController mariaDBController;
    private Usuario usuario;
    private Usuario usuarioAActualizar;

    @FXML
    private void initialize() {
        mariaDBController = new MariaDBController();
        BtnContinuar.setOnAction(event -> manejarContinuar());
        BtnVolver.setOnAction(event -> manejarVolver());
        setImagenesBotones();
    }


    private void manejarContinuar() {
        String nuevoNombre = TextFieldNombre.getText();
        String nuevoApellidos = TextFieldApellidos.getText();
        String nuevoCorreo = TextFieldCorreo.getText();
        String nuevaContrasena = TextFieldContrasena.getText();
        String nuevoGimnasio = TextFieldGimnasio.getText();
        String nuevoRol = ChoiceBoxRol.getValue();
        String nuevoDNI = TextFieldDNI.getText();

        boolean exito = mariaDBController.actualizarDatosUsuario(usuarioAActualizar.getID(), nuevoNombre, nuevoApellidos, nuevoCorreo, nuevaContrasena, nuevoGimnasio, nuevoRol, nuevoDNI);

        if (exito) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("Datos actualizados correctamente");
            alert.showAndWait();
            manejarVolver();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error al actualizar los datos");
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

    public void setImagenesBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setCorreoUsuarioAActualizar(String correo) {
        this.usuarioAActualizar = mariaDBController.obtenerUsuarioPorCorreo(correo);
        if (usuarioAActualizar != null) {
            TextFieldNombre.setText(usuarioAActualizar.getNombre());
            TextFieldApellidos.setText(usuarioAActualizar.getApellidos());
            TextFieldContrasena.setText(usuarioAActualizar.getContrasena());
            TextFieldCorreo.setText(usuarioAActualizar.getCorreo());
            ChoiceBoxRol.setValue(usuarioAActualizar.getTipoUsuario());
            TextFieldGimnasio.setText(usuarioAActualizar.getGimnasio());
            TextFieldDNI.setText(usuarioAActualizar.getDNI());
        }
    }
}