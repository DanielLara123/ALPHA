package com.example.proyectoalpha.clases;

public class Rutina_Usuario {
    private int ID_rutina;
    private int ID_usuario;

    public Rutina_Usuario() {}

    public Rutina_Usuario(int ID_rutina, int ID_usuario) {
        this.ID_rutina = ID_rutina;
        this.ID_usuario = ID_usuario;
    }

    public int getID_rutina() {
        return ID_rutina;
    }

    public void setID_rutina(int ID_rutina) {
        this.ID_rutina = ID_rutina;
    }

    public int getID_usuario() {
        return ID_usuario;
    }

    public void setID_usuario(int ID_usuario) {
        this.ID_usuario = ID_usuario;
    }
}
