package com.example.proyectoalpha.clases.Atleta;

import java.util.List;

public class Rutina {
    private String nombre;
    private List<Ejercicio> ejercicios;
    private String autor;

    public Rutina() {}

    public Rutina(String nombre, List<Ejercicio> ejercicios, String autor) {
        this.nombre = nombre;
        this.ejercicios = ejercicios;
        this.autor = autor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}