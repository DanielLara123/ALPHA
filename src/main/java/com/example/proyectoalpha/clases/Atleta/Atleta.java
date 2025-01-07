package com.example.proyectoalpha.clases.Atleta;

import com.example.proyectoalpha.clases.Usuario;
import java.util.List;

public class Atleta extends Usuario {
    private List<Rutina> rutinas;
    private List<HistorialesEjercicios> historialesEjercicios;

    // Constructor sin argumentos (requerido para deserialización)
    public Atleta() {}

    // Constructor completo
    public Atleta(String mail, String password, String usertype, String dni, List<Rutina> rutinas, List<HistorialesEjercicios> historialEjercicios) {
        super(mail, password, usertype, dni);
        this.rutinas = rutinas;
        this.historialesEjercicios = historialesEjercicios;
    }

    // Getters y Setters
    public List<Rutina> getRutinas() {
        return rutinas;
    }

    public void setRutinas(List<Rutina> rutinas) {
        this.rutinas = rutinas;
    }

    public List<HistorialesEjercicios> getHistorialEjercicios() {
        return historialesEjercicios;
    }

    public void setHistorialEjercicios(List<HistorialesEjercicios> historialEjercicios) {
        this.historialesEjercicios = historialEjercicios;
    }
}