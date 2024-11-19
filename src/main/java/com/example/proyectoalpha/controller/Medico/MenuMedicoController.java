package com.example.proyectoalpha.controller.Medico;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuMedicoController {

    @FXML
    private Button BtnHistorialesMedicos;

    @FXML
    private Button BtnMonitorizacionSalud;

    @FXML
    private Button BtnAlertas;

    @FXML
    private Button BtnRecomendaciones;

    @FXML
    private Button BtnCerrarSesion;

    @FXML
    private ImageView LblTitulo;

    @FXML
    private void initialize() {
        BtnHistorialesMedicos.setOnAction(event -> HistorialMedico());
    }

    public void HistorialMedico(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoalpha/Medico/HistorialMedico.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BtnHistorialesMedicos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
