package com.example.proyectoalpha.clases.Atleta;

import java.util.List;

public class HistorialesEjercicios {
    private String nombreEjercicio;
    private List<HistorialEjercicio> historialEjercicios;

    public HistorialesEjercicios() {}

    public HistorialesEjercicios(String nombreEjercicio, List<HistorialEjercicio> historialEjercicios) {
        this.nombreEjercicio = nombreEjercicio;
        this.historialEjercicios = historialEjercicios;
    }

    public String getNombreEjercicio() {
        return nombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }

    public List<HistorialEjercicio> getHistorialEjercicios() {
        return historialEjercicios;
    }

    public void setHistorialEjercicios(List<HistorialEjercicio> historialEjercicios) {
        this.historialEjercicios = historialEjercicios;
    }

}
