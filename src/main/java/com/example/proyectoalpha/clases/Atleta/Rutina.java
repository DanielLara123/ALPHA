package com.example.proyectoalpha.clases.Atleta;

import java.util.List;
import java.util.ArrayList;

public class Rutina {
    private Atleta atleta;
    private List<Ejercicio> ejercicios;

    public Rutina(Atleta atleta) {
        this.atleta = atleta;
        this.ejercicios = new ArrayList<>();
    }

    public void agregarEjercicio(String nombre, int duracion, int series, int repeticiones) {
        Ejercicio ejercicio = new Ejercicio(nombre, duracion, series, repeticiones);
        ejercicios.add(ejercicio);
    }

    public void eliminarEjercicio(String nombre) {
        ejercicios.removeIf(ejercicio -> ejercicio.getNombre().equals(nombre));
    }

    public List<Ejercicio> listarEjercicios() {
        return new ArrayList<>(ejercicios);
    }

}
