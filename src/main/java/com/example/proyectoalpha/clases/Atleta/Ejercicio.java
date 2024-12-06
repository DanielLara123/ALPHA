package com.example.proyectoalpha.clases.Atleta;

public class Ejercicio {
    private String nombre;
    private String grupoMuscular;
    private int descanso;
    private int series;
    private int repeticiones;

    // Constructor sin argumentos (requerido para deserialización)
    public Ejercicio() {}

    // Constructor completo
    public Ejercicio(String nombre, String grupoMuscular, int descanso, int series, int repeticiones) {
        this.nombre = nombre;
        this.grupoMuscular = grupoMuscular;
        this.descanso = descanso;
        this.series = series;
        this.repeticiones = repeticiones;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public int getDescanso() {
        return descanso;
    }

    public void setDescanso(int descanso) {
        this.descanso = descanso;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    @Override
    public String toString() {
        return nombre; // Para mostrar solo el nombre en los menús desplegables
    }
}
