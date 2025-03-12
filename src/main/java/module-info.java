module com.example.proyectoalpha {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires java.sql;

    opens com.example.proyectoalpha to javafx.fxml;
    exports com.example.proyectoalpha;
    exports com.example.proyectoalpha.controller;
    opens com.example.proyectoalpha.controller to javafx.fxml;
    exports com.example.proyectoalpha.clases;
    exports com.example.proyectoalpha.controller.Administrador;
    opens com.example.proyectoalpha.controller.Administrador to javafx.fxml;
    exports com.example.proyectoalpha.controller.Medico;
    opens com.example.proyectoalpha.controller.Medico to javafx.fxml;
    exports com.example.proyectoalpha.controller.Entrenador;
    opens com.example.proyectoalpha.controller.Entrenador to javafx.fxml;
    exports com.example.proyectoalpha.controller.RegistroInicioSesion;
    opens com.example.proyectoalpha.controller.RegistroInicioSesion to javafx.fxml;
}