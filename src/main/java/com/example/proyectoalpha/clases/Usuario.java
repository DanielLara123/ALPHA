package com.example.proyectoalpha.clases;

import com.example.proyectoalpha.clases.Atleta.Ejercicio;
import com.example.proyectoalpha.clases.Atleta.Rutina;

import java.util.List;

public class Usuario {
    private String email;
    private String contrasena;
    private String tipoUsuario;
    private String dni;
    private String gimnasio;

    public Usuario() {}

    public Usuario(String mail, String password, String usertype, String dni) {
        this.email = mail;
        this.contrasena = password;
        this.tipoUsuario = usertype;
        this.dni = dni;
    }

    // Getters and setters
    public String getCorreo() {
        return email;
    }

    public void setCorreo(String correo) {
        this.email = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

}