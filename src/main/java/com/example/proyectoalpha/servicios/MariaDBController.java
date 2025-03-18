package com.example.proyectoalpha.servicios;

import com.example.proyectoalpha.clases.Ejercicio;
import com.example.proyectoalpha.clases.Rutina;
import com.example.proyectoalpha.clases.Usuario;

import java.sql.*;
import java.util.*;

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
            if (rowsAffected > 0) {
                System.out.println("User registered successfully");
                return true;
            } else {
                System.out.println("No rows affected");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarUsuario(int userId) {
        String query = "DELETE FROM Usuario WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
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

    public boolean actualizarDatosUsuario(int userId, String correo, String contrasena, String gimnasio) {
        String query = "UPDATE Usuario SET correo = ?, contraseña = ?, gimnasio = ? WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, correo);
            pstmt.setString(2, contrasena);
            pstmt.setString(3, gimnasio);
            pstmt.setInt(4, userId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Ejercicio> obtenerEjerciciosPorGrupoMuscular(String grupoMuscular) {
        Set<Ejercicio> ejercicios = new HashSet<>();
        String query = "SELECT * FROM Ejercicio WHERE grupo_muscular = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, grupoMuscular);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Ejercicio ejercicio = new Ejercicio();
                ejercicio.setID_ejercicio(rs.getInt("ID_ejercicio"));
                ejercicio.setNombre(rs.getString("nombre"));
                ejercicio.setGrupoMuscular(rs.getString("grupo_muscular"));
                ejercicio.setPeso(rs.getDouble("peso"));
                ejercicio.setSeries(rs.getInt("series"));
                ejercicio.setRepeticiones(rs.getInt("repeticiones"));
                ejercicio.setDescanso(rs.getInt("descanso"));
                ejercicios.add(ejercicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(ejercicios);
    }

    public List<String> obtenerGruposMusculares() {
        List<String> gruposMusculares = new ArrayList<>();
        String query = "SELECT DISTINCT grupo_muscular FROM Ejercicio";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                gruposMusculares.add(rs.getString("grupo_muscular"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gruposMusculares;
    }

    public Map<String, List<Ejercicio>> obtenerMapaEjercicios() {
        Map<String, List<Ejercicio>> ejerciciosMap = new HashMap<>();
        String query = "SELECT * FROM Ejercicio";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Ejercicio ejercicio = new Ejercicio();
                ejercicio.setID_ejercicio(rs.getInt("ID_ejercicio"));
                ejercicio.setNombre(rs.getString("nombre"));
                ejercicio.setGrupoMuscular(rs.getString("grupo_muscular"));
                ejercicio.setPeso(rs.getDouble("peso"));
                ejercicio.setSeries(rs.getInt("series"));
                ejercicio.setRepeticiones(rs.getInt("repeticiones"));
                ejercicio.setDescanso(rs.getInt("descanso"));

                ejerciciosMap.computeIfAbsent(ejercicio.getGrupoMuscular(), k -> new ArrayList<>()).add(ejercicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ejerciciosMap;
    }

    public boolean guardarRutina(Rutina rutina, int usuarioId, List<Ejercicio> ejercicios) {
        String query = "INSERT INTO Rutina (nombre, dias) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, rutina.getNombre());
            pstmt.setInt(2, rutina.getDias());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int rutinaId = generatedKeys.getInt(1);
                        asociarRutinaConUsuario(rutinaId, usuarioId);
                        guardarEjerciciosRutina(rutinaId, ejercicios);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void guardarEjerciciosRutina(int rutinaId, List<Ejercicio> ejercicios) {
        String query = "INSERT INTO Rutina_Ejercicio (ID_rutina, ID_ejercicio) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (Ejercicio ejercicio : ejercicios) {
                // First, insert the exercise into the Ejercicio table
                String ejercicioQuery = "INSERT INTO Ejercicio (nombre, grupo_muscular, peso, series, repeticiones, descanso) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ejercicioStmt = conn.prepareStatement(ejercicioQuery, Statement.RETURN_GENERATED_KEYS)) {
                    ejercicioStmt.setString(1, ejercicio.getNombre());
                    ejercicioStmt.setString(2, ejercicio.getGrupoMuscular());
                    ejercicioStmt.setDouble(3, ejercicio.getPeso());
                    ejercicioStmt.setInt(4, ejercicio.getSeries());
                    ejercicioStmt.setInt(5, ejercicio.getRepeticiones());
                    ejercicioStmt.setInt(6, ejercicio.getDescanso());
                    ejercicioStmt.executeUpdate();

                    // Get the generated ID for the inserted exercise
                    try (ResultSet generatedKeys = ejercicioStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int ejercicioId = generatedKeys.getInt(1);

                            // Then, insert the relationship into the Rutina_Ejercicio table
                            pstmt.setInt(1, rutinaId);
                            pstmt.setInt(2, ejercicioId);
                            pstmt.addBatch();
                        }
                    }
                }
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void asociarRutinaConUsuario(int rutinaId, int usuarioId) {
        String query = "INSERT INTO Rutina_Usuario (ID_rutina, ID_usuario) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, rutinaId);
            pstmt.setInt(2, usuarioId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean eliminarRutinaPorNombre(String rutinaName, int id) {
        String query = "DELETE FROM Rutina WHERE nombre = ? AND ID_rutina IN (SELECT ID_rutina FROM Rutina_Usuario WHERE ID_usuario = ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, rutinaName);
            pstmt.setInt(2, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Rutina> obtenerRutinasPorUsuario(int id) {
        List<Rutina> rutinas = new ArrayList<>();
        String query = "SELECT r.ID_rutina, r.nombre, r.dias FROM Rutina r " +
                "JOIN Rutina_Usuario ru ON r.ID_rutina = ru.ID_rutina " +
                "WHERE ru.ID_usuario = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Rutina rutina = new Rutina();
                rutina.setID_rutina(rs.getInt("ID_rutina"));
                rutina.setNombre(rs.getString("nombre"));
                rutina.setDias(rs.getInt("dias"));
                rutinas.add(rutina);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rutinas;
    }

    public Rutina obtenerRutinaPorNombre(String rutinaSeleccionada, int id) {
        String query = "SELECT r.ID_rutina, r.nombre, r.dias FROM Rutina r " +
                "JOIN Rutina_Usuario ru ON r.ID_rutina = ru.ID_rutina " +
                "WHERE r.nombre = ? AND ru.ID_usuario = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, rutinaSeleccionada);
            pstmt.setInt(2, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Rutina rutina = new Rutina();
                rutina.setID_rutina(rs.getInt("ID_rutina"));
                rutina.setNombre(rs.getString("nombre"));
                rutina.setDias(rs.getInt("dias"));
                return rutina;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ejercicio> obtenerEjerciciosPorRutina(int idRutina) {
        List<Ejercicio> ejercicios = new ArrayList<>();
        String query = "SELECT e.ID_ejercicio, e.nombre, e.grupo_muscular, e.peso, e.series, e.repeticiones, e.descanso " +
                "FROM Ejercicio e " +
                "JOIN Rutina_Ejercicio re ON e.ID_ejercicio = re.ID_ejercicio " +
                "WHERE re.ID_rutina = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idRutina);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Ejercicio ejercicio = new Ejercicio();
                ejercicio.setID_ejercicio(rs.getInt("ID_ejercicio"));
                ejercicio.setNombre(rs.getString("nombre"));
                ejercicio.setGrupoMuscular(rs.getString("grupo_muscular"));
                ejercicio.setPeso(rs.getDouble("peso"));
                ejercicio.setSeries(rs.getInt("series"));
                ejercicio.setRepeticiones(rs.getInt("repeticiones"));
                ejercicio.setDescanso(rs.getInt("descanso"));
                ejercicios.add(ejercicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ejercicios;
    }

    public Ejercicio obtenerEjercicioPorNombre(String nombreEjercicio) {
        String query = "SELECT * FROM Ejercicio WHERE nombre = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombreEjercicio);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Ejercicio ejercicio = new Ejercicio();
                ejercicio.setID_ejercicio(rs.getInt("ID_ejercicio"));
                ejercicio.setNombre(rs.getString("nombre"));
                ejercicio.setGrupoMuscular(rs.getString("grupo_muscular"));
                ejercicio.setPeso(rs.getDouble("peso"));
                ejercicio.setSeries(rs.getInt("series"));
                ejercicio.setRepeticiones(rs.getInt("repeticiones"));
                ejercicio.setDescanso(rs.getInt("descanso"));
                return ejercicio;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

