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
    private LinkedList<Usuario> usuarios;
    private ObjectMapper objectMapper;

    public servicioUsuario() {
        objectMapper = new ObjectMapper();
        usuarios = cargarUsuarios();
    }

    private LinkedList<Usuario> cargarUsuarios() {
        LinkedList<Usuario> usuarios = new LinkedList<>();
        try {
            File file = new File(USER_FILE);
            if (file.exists()) {
                List<Usuario> usuariosList = objectMapper.readValue(file, new TypeReference<List<Usuario>>() {});
                for (Usuario usuario : usuariosList) {
                    usuarios.insertarCabeza(usuario);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    private void guardarUsuarios() {
        try {
            List<Usuario> usuariosList = new ArrayList<>();
            for (int i = 0; i < usuarios.size(); i++) {
                usuariosList.add(usuarios.obtener(i));
            }
            objectMapper.writeValue(new File(USER_FILE), usuariosList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.insertarCabeza(usuario);
        guardarUsuarios();
    }

    public Usuario loginUsuario(String correo, String contrasena) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario user = usuarios.obtener(i);
            if (user.getCorreo().equals(correo) && user.getContrasena().equals(contrasena)) {
                return user;
            }
        }
        return null;
    }

    public boolean emailEstaRegistrado(String correo) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.obtener(i).getCorreo().equals(correo)) {
                return true;
            }
        }
        return false;
    }

    public void eliminarUsuario(String correo) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.obtener(i).getCorreo().equals(correo)) {
                usuarios.eliminarPorValor(usuarios.obtener(i));
                guardarUsuarios();
                return;
            }
        }
    }

    public void actualizarUsuario(String correo, String nuevoCorreo, String nuevaContrasena, String nuevoDni) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.obtener(i);
            if (usuario.getCorreo().equals(correo)) {
                usuario.setCorreo(nuevoCorreo);
                usuario.setContrasena(nuevaContrasena);
                usuario.setDni(nuevoDni);
                guardarUsuarios();
                return;
            }
        }
    }

    public Usuario obtenerUsuarioPorCorreo(String correo) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.obtener(i);
            if (usuario.getCorreo().equals(correo)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean dniEstaRegistrado(String dni) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.obtener(i);
            if (usuario.getDni() != null && usuario.getDni().equals(dni)) {
                return true;
            }
        }
        return false;
    }

    public List<String> obtenerEmailsUsuarios() {
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++) {
            emails.add(usuarios.obtener(i).getCorreo());
        }
        return emails;
    }

    public List<String> obtenerEmailsAtletas() {
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.obtener(i);
            if ("Atleta".equals(usuario.getTipoUsuario())) {
                emails.add(usuario.getCorreo());
            }
        }
        return emails;
    }
}