package com.example.proyectoalpha.clases.Atleta;

public class Ejercicio {
    private String nombre;
    private String grupoMuscular;
    private int descanso;
    private int series;
    private int repeticiones;

    public Ejercicio(String nombre, String grupoMuscular, int descanso, int series, int repeticiones) {
        this.nombre = nombre;
        this.grupoMuscular = grupoMuscular;
        this.descanso = descanso;
        this.series = series;
        this.repeticiones = repeticiones;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion() {
        return descanso;
    }

    public int getSeries() {
        return series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }
}