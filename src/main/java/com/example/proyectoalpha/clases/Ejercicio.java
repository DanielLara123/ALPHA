package com.example.proyectoalpha.clases;

public class Ejercicio {
    private int ID_ejercicio;
    private String nombre;
    private String grupoMuscular;
    private double peso;
    private int series;
    private int repeticiones;
    private int descanso;

    // Constructor sin argumentos (requerido para deserialización)
    public Ejercicio() {}

    public Ejercicio(int ID_ejercicio, String nombre, String grupoMuscular, double peso, int series, int repeticiones, int descanso) {
        this.ID_ejercicio = ID_ejercicio;
        this.nombre = nombre;
        this.grupoMuscular = grupoMuscular;
        this.peso = peso;
        this.series = series;
        this.repeticiones = repeticiones;
        this.descanso = descanso;
    }

    public int getID_ejercicio() {
        return ID_ejercicio;
    }

    public void setID_ejercicio(int ID_ejercicio) {
        this.ID_ejercicio = ID_ejercicio;
    }

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

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
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

    public int getDescanso() {
        return descanso;
    }

    public void setDescanso(int descanso) {
        this.descanso = descanso;
    }
}


