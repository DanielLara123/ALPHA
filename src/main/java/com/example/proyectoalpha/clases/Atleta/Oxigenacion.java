package com.example.proyectoalpha.clases.Atleta;

public class Oxigenacion {
    private Atleta atleta;
    private int nivelOxigenacion;

    public Oxigenacion(Atleta atleta){
        this.atleta = atleta;
    }

    public int getNivelOxigenacion(){
        return nivelOxigenacion;
    }

    public void setNivelOxigenacion(int nivelOxigenacion){
        if (nivelOxigenacion < 0 || nivelOxigenacion > 100){
            throw new IllegalArgumentException("El nivel de oxigenación debe estar entre 0 y 100");
        }
        this.nivelOxigenacion = nivelOxigenacion;
    }

    public void actualizarNivelOxigenacion(int nuevoNivelOxigenacion){
        setNivelOxigenacion(nuevoNivelOxigenacion);
    }
}
