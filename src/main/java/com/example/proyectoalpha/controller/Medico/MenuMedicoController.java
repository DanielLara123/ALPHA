package com.example.proyectoalpha.controller.Medico;

import com.example.proyectoalpha.clases.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MenuMedicoController {


    @FXML
    private Button BtnEstadisticasMedicasMedico;

    @FXML
    private Button BtnEmisionDeRecomendacionesMedico;

    @FXML
    private Button BtnCerrarSesion;

    private Usuario usuario;


    @FXML
    private void initialize() {
        colocarImagenBotones();
        BtnEstadisticasMedicasMedico.setOnAction(event -> HistorialMedico());
        BtnEmisionDeRecomendacionesMedico.setOnAction(event -> manejarChat());
        BtnCerrarSesion.setOnAction(event -> manejarCerrarSesion());
    }

    public void setDatosUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public void HistorialMedico(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Medico/HistorialMedico.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnEstadisticasMedicasMedico.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void manejarCerrarSesion(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/TipoUsuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnCerrarSesion.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void manejarChat(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Medico/ChatMedico.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnEmisionDeRecomendacionesMedico.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void colocarImagenBotones(){
        URL EstadisticasMedicasMedico = getClass().getResource("/images/EstadisticasMedicasMedico.png");
        URL EmisionDeRecomendacionesMedico = getClass().getResource("/images/FotoChat.png");

        Image imagenEstadisticasMedicasMedico = new Image(String.valueOf(EstadisticasMedicasMedico), 200, 200, false, true);
        Image imagenEmisionDeRecomendacionesMedico = new Image(String.valueOf(EmisionDeRecomendacionesMedico), 200, 200, false, true);

        BtnEstadisticasMedicasMedico.setGraphic(new ImageView(imagenEstadisticasMedicasMedico));
        BtnEmisionDeRecomendacionesMedico.setGraphic(new ImageView(imagenEmisionDeRecomendacionesMedico));

    }
}
