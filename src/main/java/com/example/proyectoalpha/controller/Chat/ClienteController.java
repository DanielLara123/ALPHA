package com.example.proyectoalpha.controller.Chat;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.Atleta.MenuAtletaController;
import com.example.proyectoalpha.servicios.MariaDBController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.List;

public class ClienteController {
    @FXML
    private Button BtnVolver;

    @FXML
    private TextField TxtMensaje;

    @FXML
    private Button BtnEnviar;

    @FXML
    private ListView<String> ListViewMensajes;

    @FXML
    private Label LblDestinatario;

    @FXML
    private void initialize(){
        BtnEnviar.setOnAction(e -> enviarMensaje());
        BtnVolver.setOnAction(e -> manejarVolver());
        colocarImagenBotones();
    }

    private Usuario usuario2;
    private Usuario usuario;
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String usuarioActual; // Cambia según el usuario actual
    private String usuarioDestino;

    public void setUsuario(Usuario usuario, Usuario usuario2) {
        this.usuario = usuario;
        this.usuario2 = usuario2;
        this.usuarioActual = usuario.getNombre();
        this.usuarioDestino = usuario2.getNombre();
        LblDestinatario.setText(usuario2.getNombre());
    }

    private void cargarMensajesAnteriores() {
        MariaDBController mariaDBController = new MariaDBController();
        List<String> mensajes = mariaDBController.cargarChat(usuario.getID(), usuario2.getID());
        for (String mensaje : mensajes) {
            if (mensaje.startsWith(usuarioActual + ":")) {
                ListViewMensajes.getItems().add("Tú: " + mensaje.substring(usuarioActual.length() + 1));
            } else {
                ListViewMensajes.getItems().add(mensaje);
            }
        }
    }

    public void inicializarCliente(String servidor, int puerto) {
        if (usuario == null || usuario2 == null) {
            throw new IllegalStateException("Usuarios no están inicializados");
        }

        try {
            socket = new Socket(servidor, puerto);
            salida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar nombre de usuario al servidor
            salida.println(usuarioActual);

            // Cargar mensajes anteriores
            cargarMensajesAnteriores();

            // Hilo para recibir mensajes
            new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {
                        String finalMensaje = mensaje;
                        Platform.runLater(() -> ListViewMensajes.getItems().add(finalMensaje));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void enviarMensaje() {
        if (salida == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Connection Error");
            alert.setContentText("Error: Conexión fallida.");
            alert.showAndWait();
            return;
        }

        String mensaje = TxtMensaje.getText();
        if (!mensaje.isEmpty()) {
            String destinatario = usuarioDestino; // Adjust the recipient according to the logic
            salida.println(destinatario + ":" + mensaje);
            ListViewMensajes.getItems().add("Tú: " + mensaje);
            TxtMensaje.clear();
        }
        MariaDBController mariaDBController = new MariaDBController();
        mariaDBController.guardarChat(mensaje, usuario.getID(), usuario2.getID());
    }

    private void manejarVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Chat/ElegirDestinatario.fxml"));
            Parent root = loader.load();

            ElegirDestinatarioController controller = loader.getController();
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