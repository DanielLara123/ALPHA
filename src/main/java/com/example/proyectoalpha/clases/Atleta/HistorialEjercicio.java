package com.example.proyectoalpha.clases.Atleta;

import java.util.Date;

public class HistorialEjercicio {
    private Date fecha;
    private double peso;
    private int repeticiones;

    // Constructor sin argumentos (requerido para deserialización)
    public HistorialEjercicio() {}

    // Constructor completo
    public HistorialEjercicio(Date fecha, double peso, int repeticiones) {
        this.fecha = fecha;
        this.peso = peso;
        this.repeticiones = repeticiones;
    }


    // Getters y Setters
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
}