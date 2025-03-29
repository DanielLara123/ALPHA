package com.example.proyectoalpha.clases;

import java.sql.Date;

public class Sensor {
    private int ID_sensor;
    private String tipoDato;
    private Date fecha;
    private String valor; // Cambiado a String
    private int ID_usuario;

    // Getters y setters
    public int getID_sensor() {
        return ID_sensor;
    }

    public void setID_sensor(int ID_sensor) {
        this.ID_sensor = ID_sensor;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getID_usuario() {
        return ID_usuario;
    }

    public void setID_usuario(int ID_usuario) {
        this.ID_usuario = ID_usuario;
    }
}