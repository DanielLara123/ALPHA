package com.example.proyectoalpha.clases;

import java.util.List;

public class Rutina {
    private int ID_rutina;
    private String nombre;
    private int dias;

    public Rutina() {}

    public Rutina(int ID_rutina, String nombre, int dias) {
        this.ID_rutina = ID_rutina;
        this.nombre = nombre;
        this.dias = dias;
    }

    public Rutina(String nombreRutina, int id) {
        this.nombre = nombreRutina;
        this.ID_rutina = id;
    }

    public int getID_rutina() {
        return ID_rutina;
    }

    public void setID_rutina(int ID_rutina) {
        this.ID_rutina = ID_rutina;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }
}