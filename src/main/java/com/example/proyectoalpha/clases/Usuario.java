package com.example.proyectoalpha.clases;

public class Usuario {
    private int ID;
    private String nombre;
    private String apellidos;
    private String contrasena;
    private String DNI;
    private String correo;
    private String tipoUsuario;
    private String gimnasio;

    public Usuario() {}

    public Usuario(int ID, String nombre, String apellidos, String contrasena, String DNI, String correo, String tipoUsuario, String gimnasio) {
        this.ID = ID;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contrasena = contrasena;
        this.DNI = DNI;
        this.correo = correo;
        this.tipoUsuario = tipoUsuario;
        this.gimnasio = gimnasio;
    }

    public Usuario(String nombre, String apellidos, String contrasena, String DNI, String correo, String tipoUsuario, String gimnasio) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contrasena = contrasena;
        this.DNI = DNI;
        this.correo = correo;
        this.tipoUsuario = tipoUsuario;
        this.gimnasio = gimnasio;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getGimnasio() {
        return gimnasio;
    }

    public void setGimnasio(String gimnasio) {
        this.gimnasio = gimnasio;
    }
}