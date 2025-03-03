package com.example.proyectoalpha.clases;


import java.sql.Date;

public class Entrenamiento {
    private int ID_entrenamiento;
    private Date fechaEntrenamiento;
    private double peso;
    private int repeticiones;
    private int series;
    private int descanso;
    private int ID_usuario;

    // Constructor sin argumentos (requerido para deserialización)
    public Entrenamiento() {}

    public Entrenamiento(int ID_entrenamiento, Date fechaEntrenamiento, double peso, int repeticiones, int series, int descanso, int ID_usuario) {
        this.ID_entrenamiento = ID_entrenamiento;
        this.fechaEntrenamiento = fechaEntrenamiento;
        this.peso = peso;
        this.repeticiones = repeticiones;
        this.series = series;
        this.descanso = descanso;
        this.ID_usuario = ID_usuario;
    }

    public int getID_entrenamiento() {
        return ID_entrenamiento;
    }

    public void setID_entrenamiento(int ID_entrenamiento) {
        this.ID_entrenamiento = ID_entrenamiento;
    }

    public Date getFechaEntrenamiento() {
        return fechaEntrenamiento;
    }

    public void setFechaEntrenamiento(Date fechaEntrenamiento) {
        this.fechaEntrenamiento = fechaEntrenamiento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getDescanso() {
        return descanso;
    }

    public void setDescanso(int descanso) {
        this.descanso = descanso;
    }

    public int getID_usuario() {
        return ID_usuario;
    }

    public void setID_usuario(int ID_usuario) {
        this.ID_usuario = ID_usuario;
    }
}
