package com.example.proyectoalpha.controller.Chat;

import com.example.proyectoalpha.clases.Usuario;
import com.example.proyectoalpha.controller.Atleta.MenuAtletaController;
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
    }

    public void inicializarCliente(String servidor, int puerto) {
        try {
            socket = new Socket(servidor, puerto);
            salida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar nombre de usuario al servidor
            salida.println(usuarioActual);

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
        String mensaje = TxtMensaje.getText();
        if (!mensaje.isEmpty()) {
            String destinatario = usuarioDestino; // Ajusta el destinatario según la lógica
            salida.println(destinatario + ":" + mensaje);
            ListViewMensajes.getItems().add("Tú: " + mensaje);
            TxtMensaje.clear();
        }
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

    private void colocarImagenBotones() {
        URL volver = getClass().getResource("/images/VolverAtras.png");

        Image imagenVolver = new Image(String.valueOf(volver), 50, 50, false, true);

        BtnVolver.setGraphic(new ImageView(imagenVolver));
    }
}
