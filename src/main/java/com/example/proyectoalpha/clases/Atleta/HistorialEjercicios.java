package com.example.proyectoalpha.clases.Atleta;

import java.util.ArrayList;
import java.util.List;

public class HistorialEjercicios {
    private Atleta atleta;
    private List<EjercicioRealizado> historial;

    public HistorialEjercicios(Atleta atleta) {
        this.atleta = atleta;
        this.historial = new ArrayList<>();
    }

    public void agregarEjercicioRealizado(String nombre, String grupoMuscular, int duracion, int series, int repeticiones, String fecha) {
        EjercicioRealizado ejercicio = new EjercicioRealizado(nombre, grupoMuscular, duracion, series, repeticiones, fecha);
        historial.add(ejercicio);
    }

    public void eliminarEjercicioRealizado(String nombre, String fecha) {
        historial.removeIf(ejercicio -> ejercicio.getNombre().equals(nombre) && ejercicio.getFecha().equals(fecha));
    }

    public List<EjercicioRealizado> listarHistorial() {
        return new ArrayList<>(historial);
    }

    public static class EjercicioRealizado extends Ejercicio {
        private String fecha;

        public EjercicioRealizado(String nombre, String grupoMuscular, int duracion, int series, int repeticiones, String fecha) {
            super(nombre, grupoMuscular, duracion, series, repeticiones);
            this.fecha = fecha;
        }

        public String getFecha() {
            return fecha;
        }
    }
}