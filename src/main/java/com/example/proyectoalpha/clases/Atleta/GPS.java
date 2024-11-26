package com.example.proyectoalpha.clases.Atleta;

public class GPS {
    private Atleta atleta;
    private double latitud;
    private double longitud;

    public GPS(Atleta atleta) {
        this.atleta = atleta;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void actualizarPosicion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
