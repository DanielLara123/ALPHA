package com.example.proyectoalpha.servicios;

import com.example.proyectoalpha.clases.Usuario;

import java.sql.*;

public class MariaDBController {

    // Database credentials
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String DB_URL = "jdbc:mariadb://127.0.0.1/alpha";

    // Method to create the Usuario table
    public void crearUsuario() {
        String sql = "CREATE TABLE IF NOT EXISTS Usuario (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                "nombre VARCHAR(50), " +
                "apellidos VARCHAR(255), " +
                "contraseña VARCHAR(255), " +
                "DNI VARCHAR(20) UNIQUE, " +
                "correo VARCHAR(100) UNIQUE, " +
                "tipoUsuario VARCHAR(15), " +
                "gimnasio VARCHAR(100));";
        executeUpdate(sql, "Tabla Usuario creada o ya existente.");
    }

    // Method to create the Chat table
    public void crearChat() {
        String sql = "CREATE TABLE IF NOT EXISTS Chat (" +
                "ID_chat INT PRIMARY KEY AUTO_INCREMENT, " +
                "contenido LONGTEXT, " +
                "fecha DATETIME, " +
                "ID_usuario1 INT, " +
                "ID_usuario2 INT, " +
                "FOREIGN KEY (ID_usuario1) REFERENCES Usuario(ID) ON DELETE CASCADE, " +
                "FOREIGN KEY (ID_usuario2) REFERENCES Usuario(ID) ON DELETE CASCADE);";
        executeUpdate(sql, "Tabla Chat creada o ya existente.");
    }

    // Method to create the Rutina table
    public void crearRutina() {
        String sql = "CREATE TABLE IF NOT EXISTS Rutina (" +
                "ID_rutina INT PRIMARY KEY AUTO_INCREMENT, " +
                "nombre VARCHAR(100), " +
                "dias INT);";
        executeUpdate(sql, "Tabla Rutina creada o ya existente.");
    }

    // Method to create the Rutina_Usuario table
    public void crearRutinaUsuario() {
        String sql = "CREATE TABLE IF NOT EXISTS Rutina_Usuario (" +
                "ID_usuario INT, " +
                "ID_rutina INT, " +
                "PRIMARY KEY (ID_usuario, ID_rutina), " +
                "FOREIGN KEY (ID_usuario) REFERENCES Usuario(ID) ON DELETE CASCADE, " +
                "FOREIGN KEY (ID_rutina) REFERENCES Rutina(ID_rutina) ON DELETE CASCADE);";
        executeUpdate(sql, "Tabla Rutina_Usuario creada o ya existente.");
    }

    // Method to create the Ejercicio table
    public void crearEjercicio() {
        String sql = "CREATE TABLE IF NOT EXISTS Ejercicio (" +
                "ID_ejercicio INT PRIMARY KEY AUTO_INCREMENT, " +
                "nombre VARCHAR(100), " +
                "grupo_muscular VARCHAR(100), " +
                "peso DECIMAL(5,2), " +
                "series INT, " +
                "repeticiones INT, " +
                "descanso INT);";
        executeUpdate(sql, "Tabla Ejercicio creada o ya existente.");
    }

    // Method to create the Rutina_Ejercicio table
    public void crearRutinaEjercicio() {
        String sql = "CREATE TABLE IF NOT EXISTS Rutina_Ejercicio (" +
                "ID_rutina INT, " +
                "ID_ejercicio INT, " +
                "PRIMARY KEY (ID_rutina, ID_ejercicio), " +
                "FOREIGN KEY (ID_rutina) REFERENCES Rutina(ID_rutina) ON DELETE CASCADE, " +
                "FOREIGN KEY (ID_ejercicio) REFERENCES Ejercicio(ID_ejercicio) ON DELETE CASCADE);";
        executeUpdate(sql, "Tabla Rutina_Ejercicio creada o ya existente.");
    }

    // Method to create the Sensores table
    public void crearSensores() {
        String sql = "CREATE TABLE IF NOT EXISTS Sensores (" +
                "ID_sensor INT PRIMARY KEY AUTO_INCREMENT, " +
                "tipo_dato VARCHAR(100), " +
                "fecha DATETIME, " +
                "valor VARCHAR(50), " +
                "ID_usuario INT, " +
                "FOREIGN KEY (ID_usuario) REFERENCES Usuario(ID) ON DELETE CASCADE);";
        executeUpdate(sql, "Tabla Sensores creada o ya existente.");
    }

    // Method to create the Entrenamiento table
    public void crearEntrenamiento() {
        String sql = "CREATE TABLE IF NOT EXISTS Entrenamiento (" +
                "ID_entrenamiento INT PRIMARY KEY AUTO_INCREMENT, " +
                "fecha DATETIME, " +
                "peso DECIMAL(5,2), " +
                "series INT, " +
                "repeticiones INT, " +
                "descanso INT, " +
                "ID_usuario INT, " +
                "FOREIGN KEY (ID_usuario) REFERENCES Usuario(ID) ON DELETE CASCADE);";
        executeUpdate(sql, "Tabla Entrenamiento creada o ya existente.");
    }

    // Helper method to execute SQL update statements
    private void executeUpdate(String sql, String successMessage) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println(successMessage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to login a user
    public Usuario loginUsuario(String correo, String contraseña) {
        String sql = "SELECT * FROM Usuario WHERE correo = ? AND contraseña = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            pstmt.setString(2, contraseña);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("ID"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("contraseña"),
                        rs.getString("DNI"),
                        rs.getString("correo"),
                        rs.getString("tipoUsuario"),
                        rs.getString("gimnasio")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuario (nombre, apellidos, contraseña, DNI, correo, tipoUsuario, gimnasio) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellidos());
            pstmt.setString(3, usuario.getContrasena());
            pstmt.setString(4, usuario.getDNI());
            pstmt.setString(5, usuario.getCorreo());
            pstmt.setString(6, usuario.getTipoUsuario());
            pstmt.setString(7, usuario.getGimnasio());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailEstaRegistrado(String correo) {
        String sql = "SELECT * FROM Usuario WHERE correo = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}