package com.example.proyectoalpha.clases;

public class Usuario {
    private String email;
    private String Contrasena;
    private String TipoUsuario;

    // Getters and setters
    public String getCorreo() {
        return email;
    }

    public void setCorreo(String correo) {
        this.email = correo;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        this.Contrasena = contrasena;
    }

    public String getTipoUsuario() {
        return TipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.TipoUsuario = tipoUsuario;
    }
}