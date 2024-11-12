module com.example.proyectoalpha {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens com.example.proyectoalpha to javafx.fxml;
    exports com.example.proyectoalpha;
    exports com.example.proyectoalpha.controller;
    opens com.example.proyectoalpha.controller to javafx.fxml;
    exports com.example.proyectoalpha.clases;
}