package com.example.proyectoalpha.clases;

public class Rutina_Ejercicio {
    private int ID_rutina;
    private int ID_ejercicio;

    public Rutina_Ejercicio() {}

    public Rutina_Ejercicio(int ID_rutina, int ID_ejercicio) {
        this.ID_rutina = ID_rutina;
        this.ID_ejercicio = ID_ejercicio;
    }

    public int getID_rutina() {
        return ID_rutina;
    }

    public void setID_rutina(int ID_rutina) {
        this.ID_rutina = ID_rutina;
    }

    public int getID_ejercicio() {
        return ID_ejercicio;
    }

    public void setID_ejercicio(int ID_ejercicio) {
        this.ID_ejercicio = ID_ejercicio;
    }
}
