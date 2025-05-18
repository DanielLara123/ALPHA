package com.example.proyectoalpha.servicios;

import com.example.proyectoalpha.clases.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class MariaDBController {

    // Database credentials
    private static final String USER = "pii2_Alpha";
    private static final String PASS = "secure_password";
    private static final String DB_URL = "jdbc:mariadb://195.235.211.197/pii2_Alpha";

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
                "ID_ejercicio INT, " + // New attribute
                "FOREIGN KEY (ID_usuario) REFERENCES Usuario(ID) ON DELETE CASCADE, " +
                "FOREIGN KEY (ID_ejercicio) REFERENCES Ejercicio(ID_ejercicio) ON DELETE CASCADE);";
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
        String sql = "INSERT INTO Usuario (nombre, apellidos, contraseña, DNI, correo, tipoUsuario, gimnasio, estado) VALUES (?, ?, ?, ?, ?, ?, ?, 'activo')";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Hash the password
            String hashedPassword = hashPassword(usuario.getContrasena());

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellidos());
            pstmt.setString(3, hashedPassword); // Use the hashed password
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

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public boolean eliminarUsuario(int userId) {
        String query = "UPDATE Usuario SET estado = 'dado de baja' WHERE ID = ?";
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
        Set<String> nombresEjercicios = new HashSet<>();
        List<Ejercicio> ejercicios = new ArrayList<>();
        String query = "SELECT DISTINCT nombre FROM Ejercicio WHERE grupo_muscular = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, grupoMuscular);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                if (nombresEjercicios.add(nombre)) {
                    Ejercicio ejercicio = new Ejercicio();
                    ejercicio.setNombre(nombre);
                    ejercicios.add(ejercicio);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ejercicios;
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

    public boolean guardarEntrenamiento(Entrenamiento entrenamiento) {
        String sql = "INSERT INTO Entrenamiento (fecha, peso, series, repeticiones, descanso, ID_usuario, ID_ejercicio) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, entrenamiento.getFechaEntrenamiento());
            pstmt.setDouble(2, entrenamiento.getPeso());
            pstmt.setInt(3, entrenamiento.getSeries());
            pstmt.setInt(4, entrenamiento.getRepeticiones());
            pstmt.setInt(5, entrenamiento.getDescanso());
            pstmt.setInt(6, entrenamiento.getID_usuario());
            pstmt.setInt(7, entrenamiento.getID_ejercicio());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Entrenamiento> obtenerLogros(String ejercicio, String grupoMuscular, int usuarioId) {
        List<Entrenamiento> logros = new ArrayList<>();
        String sql = "SELECT * FROM Entrenamiento e " +
                "JOIN Ejercicio ej ON e.ID_ejercicio = ej.ID_ejercicio " +
                "WHERE ej.nombre = ? AND ej.grupo_muscular = ? AND e.ID_usuario = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ejercicio);
            pstmt.setString(2, grupoMuscular);
            pstmt.setInt(3, usuarioId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Entrenamiento entrenamiento = new Entrenamiento();
                entrenamiento.setID_entrenamiento(rs.getInt("ID_entrenamiento"));
                entrenamiento.setFechaEntrenamiento(rs.getDate("fecha"));
                entrenamiento.setPeso(rs.getDouble("peso"));
                entrenamiento.setSeries(rs.getInt("series"));
                entrenamiento.setRepeticiones(rs.getInt("repeticiones"));
                entrenamiento.setDescanso(rs.getInt("descanso"));
                entrenamiento.setID_usuario(rs.getInt("ID_usuario"));
                entrenamiento.setID_ejercicio(rs.getInt("ID_ejercicio"));
                logros.add(entrenamiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logros;
    }

    public void guardarChat(String contenido, int idUsuario1, int idUsuario2) {
        String sql = "INSERT INTO Chat (contenido, fecha, ID_usuario1, ID_usuario2) VALUES (?, NOW(), ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contenido);
            pstmt.setInt(2, idUsuario1);
            pstmt.setInt(3, idUsuario2);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> cargarChat(int idUsuario1, int idUsuario2) {
        List<String> mensajes = new ArrayList<>();
        String sql = "SELECT contenido, fecha FROM Chat WHERE (ID_usuario1 = ? AND ID_usuario2 = ?) OR (ID_usuario1 = ? AND ID_usuario2 = ?) ORDER BY fecha";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario1);
            pstmt.setInt(2, idUsuario2);
            pstmt.setInt(3, idUsuario2);
            pstmt.setInt(4, idUsuario1);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String mensaje = rs.getString("contenido");
                mensajes.add(mensaje);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mensajes;
    }

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("ID"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("contraseña"),
                        rs.getString("DNI"),
                        rs.getString("correo"),
                        rs.getString("tipoUsuario"),
                        rs.getString("gimnasio")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public Usuario obtenerUsuarioPorCorreo(String correoAtleta) {
        String query = "SELECT * FROM Usuario WHERE correo = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, correoAtleta);
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

    public boolean guardarEjercicio(Ejercicio nuevoEjercicio) {
        String query = "INSERT INTO Ejercicio (nombre, grupo_muscular, peso, series, repeticiones, descanso) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nuevoEjercicio.getNombre());
            pstmt.setString(2, nuevoEjercicio.getGrupoMuscular());
            pstmt.setDouble(3, nuevoEjercicio.getPeso());
            pstmt.setInt(4, nuevoEjercicio.getSeries());
            pstmt.setInt(5, nuevoEjercicio.getRepeticiones());
            pstmt.setInt(6, nuevoEjercicio.getDescanso());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LocalDate obtenerFechaUltimoEntrenamiento(int usuarioID) {
        String query = "SELECT MAX(fecha) AS ultima_fecha FROM Entrenamiento WHERE ID_usuario= ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, usuarioID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Date fecha = rs.getDate("ultima_fecha");
                if (fecha != null) {
                    return fecha.toLocalDate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String obtenerRemitenteMensaje(String contenido, int idUsuario1, int idUsuario2) {
        String sql = "SELECT ID_usuario1, ID_usuario2 FROM Chat WHERE contenido = ? AND ((ID_usuario1 = ? AND ID_usuario2 = ?) OR (ID_usuario1 = ? AND ID_usuario2 = ?))";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contenido);
            pstmt.setInt(2, idUsuario1);
            pstmt.setInt(3, idUsuario2);
            pstmt.setInt(4, idUsuario2);
            pstmt.setInt(5, idUsuario1);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int senderId = rs.getInt("ID_usuario1");
                if (senderId == idUsuario1) {
                    return "Usuario1";
                } else {
                    return "Usuario2";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Desconocido";
    }

    public List<String> obtenerContactosRecientes(int usuarioID) {
        List<String> contactos = new ArrayList<>();
        String query = "SELECT correo FROM usuario WHERE usuarioID = ? ORDER BY fechaReciente DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, usuarioID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                contactos.add(rs.getString("correo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactos;
    }

    public Usuario obtenerUsuarioPorEmail(String correoAtleta) {
        String query = "SELECT * FROM Usuario WHERE correo = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, correoAtleta);
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

    public byte[] obtenerImagenPorId(int id) {
        String query = "SELECT img_data FROM Imagenes WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBytes("img_data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Sensor> obtenerDatosSensorPorUsuario(int ID_usuario) {
        String query = "SELECT s.tipo_dato, s.fecha, s.valor " +
                "FROM Sensores s " +
                "JOIN ( " +
                "    SELECT tipo_dato, MAX(fecha) AS max_fecha " +
                "    FROM Sensores " +
                "    WHERE ID_usuario = ? " +
                "    GROUP BY tipo_dato " +
                ") subquery " +
                "ON s.tipo_dato = subquery.tipo_dato AND s.fecha = subquery.max_fecha " +
                "WHERE s.ID_usuario = ?";
        List<Sensor> sensores = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, ID_usuario);
            pstmt.setInt(2, ID_usuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Sensor sensor = new Sensor();
                sensor.setTipoDato(rs.getString("tipo_dato"));
                sensor.setFecha(rs.getDate("fecha"));
                sensor.setValor(rs.getString("valor"));
                sensor.setID_usuario(ID_usuario);
                sensores.add(sensor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sensores;
    }

    public List<Usuario> obtenerUsuariosPorGimnasio(String gimnasio) {
        String query = "SELECT * FROM Usuario WHERE gimnasio = ?";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, gimnasio);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setID(rs.getInt("ID"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setTipoUsuario(rs.getString("tipoUsuario"));
                usuario.setGimnasio(rs.getString("gimnasio"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }


    public boolean actualizarDatosUsuario(int id, String nuevoNombre, String nuevoApellidos, String nuevoCorreo, String nuevaContrasena, String nuevoGimnasio, String nuevoRol, String nuevoDNI) {
        String query = "UPDATE Usuario SET nombre = ?, apellidos = ?, correo = ?, contraseña = ?, gimnasio = ?, tipoUsuario = ?, DNI = ? WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, nuevoApellidos);
            pstmt.setString(3, nuevoCorreo);
            pstmt.setString(4, nuevaContrasena);
            pstmt.setString(5, nuevoGimnasio);
            pstmt.setString(6, nuevoRol);
            pstmt.setString(7, nuevoDNI);
            pstmt.setInt(8, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarUsuarioPorCorreo(String correoSeleccionado) {
        String query = "UPDATE Usuario SET estado = 'dado de baja' WHERE correo = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, correoSeleccionado);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> obtenerUsuariosRecientes(int usuarioID) {
        List<String> correos = new ArrayList<>();
        String query = "SELECT DISTINCT u.correo FROM Chat c " +
                "JOIN Usuario u ON (c.ID_usuario1 = u.id OR c.ID_usuario2 = u.id) " +
                "WHERE (c.ID_usuario1 = ? OR c.ID_usuario2 = ?) " +
                "ORDER BY c.fecha DESC";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, usuarioID);
            statement.setInt(2, usuarioID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                correos.add(resultSet.getString("correo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return correos;
    }
}


