package com.example.proyectoalpha.clases;

import java.sql.Date;

public class Sensor {
    private int ID_sensor;
    private String tipoDato;
    private Date fecha;
    private double valor;
    private int ID_usuario;

    public Sensor() {}

    public Sensor(int ID_sensor, String tipoDato, Date fecha, double valor, int ID_usuario) {
        this.ID_sensor = ID_sensor;
        this.tipoDato = tipoDato;
        this.fecha = fecha;
        this.valor = valor;
        this.ID_usuario = ID_usuario;
    }

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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getID_usuario() {
        return ID_usuario;
    }

    public void setID_usuario(int ID_usuario) {
        this.ID_usuario = ID_usuario;
    }
}
