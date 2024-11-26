package com.example.proyectoalpha.clases.Atleta;

public class Ejercicio {
    private String nombre;
    private int duracion;
    private int series;
    private int repeticiones;

    public Ejercicio(String nombre, int duracion, int series, int repeticiones) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.series = series;
        this.repeticiones = repeticiones;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getSeries() {
        return series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }
}