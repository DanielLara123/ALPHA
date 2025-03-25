package com.example.proyectoalpha.controller.Chat;

import com.example.proyectoalpha.controller.Administrador.MenuAdminController;
import com.example.proyectoalpha.controller.Entrenador.MenuEntrenadorController;
import com.example.proyectoalpha.controller.Medico.MenuMedicoController;
import com.example.proyectoalpha.servicios.MariaDBController;
import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.Atleta.MenuAtletaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class ElegirDestinatarioController {

    @FXML
    private Button BtnVolver;

    @FXML
    private Button BtnAbrir;

    @FXML
    private ListView<String> UsuariosRecientes;

    @FXML
    private TextField BuscarUsuario;

    private Usuario usuario;
    private MariaDBController maria;
    private ObservableList<String> contactosRecientes;

    public ElegirDestinatarioController() {
        this.maria = new MariaDBController(); // Initialize the MariaDBController
    }

    @FXML
    private void initialize() {
        BtnVolver.setOnAction(e -> manejarVolver());
        BtnAbrir.setOnAction(e -> inicializarChat());
        BuscarUsuario.textProperty().addListener((observable, oldValue, newValue) -> filtrarContactos(newValue));
        colocarImagenBotones();
    }

    private void cargarContactosRecientes() {
        if (usuario != null) {
            List<String> contactos = maria.obtenerContactosRecientes(usuario.getID());
            contactosRecientes = FXCollections.observableArrayList(contactos);
            UsuariosRecientes.setItems(contactosRecientes);
        }
    }

    private void filtrarContactos(String filtro) {
        List<String> filtrados = contactosRecientes.stream()
                .filter(email -> email.toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
        UsuariosRecientes.setItems(FXCollections.observableArrayList(filtrados));
    }

    private void inicializarChat() {
        String emailSeleccionado = UsuariosRecientes.getSelectionModel().getSelectedItem();
        if (emailSeleccionado == null) {
            emailSeleccionado = BuscarUsuario.getText();
        }
        if (emailSeleccionado == null || emailSeleccionado.isEmpty()) return;

        Usuario usuario2 = maria.obtenerUsuarioPorEmail(emailSeleccionado);
        if (usuario2 == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Chat/Chat.fxml"));
            Parent root = loader.load();

            ClienteController chatController = loader.getController();
            chatController.setUsuario(usuario, usuario2); // Initialize usuario and usuario2
            chatController.inicializarCliente("localhost", 12345);

            Stage stage = (Stage) BtnAbrir.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDatosUsuario(Usuario usuario) {
        this.usuario = usuario;
        cargarContactosRecientes(); // Load recent contacts after setting the user
    }

    private void manejarVolver() {
        try {
            if (usuario.getTipoUsuario().equalsIgnoreCase("Atleta")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Atleta/MenuAtleta.fxml"));
                Parent root = loader.load();

                MenuAtletaController controller = loader.getController();
                controller.setDatosUsuario(usuario);

                Stage stage = (Stage) BtnVolver.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else if (usuario.getTipoUsuario().equalsIgnoreCase("Entrenador")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Entrenador/MenuEntrenador.fxml"));
                Parent root = loader.load();

                MenuEntrenadorController controller = loader.getController();
                controller.setDatosUsuario(usuario);

                Stage stage = (Stage) BtnVolver.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else if (usuario.getTipoUsuario().equalsIgnoreCase("Administrador")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Administrador/MenuAdministrador.fxml"));
                Parent root = loader.load();

                MenuAdminController controller = loader.getController();
                controller.setDatosUsuario(usuario);

                Stage stage = (Stage) BtnVolver.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Medico/MenuMedico.fxml"));
                Parent root = loader.load();

                MenuMedicoController controller = loader.getController();
                controller.setDatosUsuario(usuario);

                Stage stage = (Stage) BtnVolver.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }

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