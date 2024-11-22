package com.example.proyectoalpha.servicios;

import com.example.proyectoalpha.clases.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class servicioUsuario {
    private static final String USER_FILE = "usuarios.json";
    private List<Usuario> usuarios;
    private ObjectMapper objectMapper;

    public servicioUsuario() {
        objectMapper = new ObjectMapper();
        usuarios = cargarUsuarios();
    }

    private List<Usuario> cargarUsuarios() {
        try {
            File file = new File(USER_FILE);
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<List<Usuario>>() {});
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void guardarUsuarios() {
        try {
            objectMapper.writeValue(new File(USER_FILE), usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        guardarUsuarios();
    }

    public Usuario loginUsuario(String correo, String contrasena) {
        return usuarios.stream()
                .filter(user -> user.getCorreo().equals(correo) && user.getContrasena().equals(contrasena))
                .findFirst()
                .orElse(null);
    }

    public boolean emailEstaRegistrado(String correo) {
        return usuarios.stream().anyMatch(user -> user.getCorreo().equals(correo));
    }

    public void eliminarUsuario(String correo) {
        usuarios.removeIf(user -> user.getCorreo().equals(correo));
        guardarUsuarios();
    }

    public void actualizarUsuario(String correo, String nuevoCorreo, String nuevaContrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equals(correo)) {
                usuario.setCorreo(nuevoCorreo);
                usuario.setContrasena(nuevaContrasena);
                break;
            }
        }
        guardarUsuarios();
    }

    public Usuario obtenerUsuarioPorCorreo(String correo) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equals(correo)) {
                return usuario;
            }
        }
        return null;
    }
}