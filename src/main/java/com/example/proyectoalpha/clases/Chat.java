package com.example.proyectoalpha.clases;

public class Chat {
    private int ID_chat;
    private String contenido;
    private String fecha;
    private int ID_usuario1;
    private int ID_usuario2;

    public Chat() {}

    public Chat(int ID_chat, String contenido, String fecha, int ID_usuario1, int ID_usuario2) {
        this.ID_chat = ID_chat;
        this.contenido = contenido;
        this.fecha = fecha;
        this.ID_usuario1 = ID_usuario1;
        this.ID_usuario2 = ID_usuario2;
    }

    public int getID_chat() {
        return ID_chat;
    }

    public void setID_chat(int ID_chat) {
        this.ID_chat = ID_chat;
    }

    public String getContenido() {
        return contenido;
    }

    public int getID_usuario1() {
        return ID_usuario1;
    }

    public int getID_usuario2() {
        return ID_usuario2;
    }

    public void setID_usuario1(int ID_usuario1) {
        this.ID_usuario1 = ID_usuario1;
    }

    public void setID_usuario2(int ID_usuario2) {
        this.ID_usuario2 = ID_usuario2;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
