package com.example.proyectoalpha.servicios;

import java.sql.*; // Importa las clases necesarias para la conexión con MariaDB

public class Sql {

    // Credenciales de la base de datos
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String DB_URL = "jdbc:mariadb://127.0.0.1/pii24-alphateam";

    // Método para crear la tabla USERS si no existe
    public void crearUsuario() {
        String sql = "CREATE TABLE IF NOT EXISTS Usuarios (" +
                "id INTEGER NOT NULL AUTO_INCREMENT, " +
                "nombre VARCHAR(50), " +
                "apellidos VARCHAR(255), " +
                "password VARCHAR(255) NOT NULL," +
                "dni VARCHAR(20) UNIQUE NOT NULL," +
                "correo VARCHAR(100) UNIQUE NOT NULL," +
                "tipoUsuario VARCHAR(15)," +
                "gimnasio VARCHAR(100)," +
                "PRIMARY KEY (id));";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) { // Crea un objeto Statement para ejecutar la consulta
            stmt.executeUpdate(sql); // Ejecuta la consulta SQL para crear la tabla
            System.out.println("Tabla Usuarios creada o ya existente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearEntrenamientosUsuario() {
        String sql = "CREATE TABLE IF NOT EXISTS Entrenamientos (" +
                "id_entrenamiento INTEGER NOT NULL AUTO_INCREMENT, " +
                "fecha DATE, " +
                "peso DECIMAL(5, 2), " +
                "series INTEGER," +
                "repeticiones INTEGER," +
                "descanso INTEGER ," +
                "id_usuario INTEGER NOT NULL" +
                "FOREIGN KEY (id_usuario)," +
                "PRIMARY KEY (id_entrenamiento));";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) { // Crea un objeto Statement para ejecutar la consulta
            stmt.executeUpdate(sql); // Ejecuta la consulta SQL para crear la tabla
            System.out.println("Tabla Entrenamientos creada o ya existente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para insertar usuarios de prueba en la base de datos
    public void insertTestUsers() {
        String sql = "INSERT IGNORE INTO USERS (email, password, role) VALUES (?, ?, ?)"; // Usa IGNORE para evitar duplicados
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Utiliza PreparedStatement para prevenir inyección SQL
            // Lista de usuarios de prueba con email, contraseña y rol
            String[][] users = {
                    {"user1@example.com", "password123", "user"},
                    {"admin@example.com", "adminpass", "admin"}
            };
            for (String[] user : users) {
                pstmt.setString(1, user[0]); // Establece el primer parámetro (email)
                pstmt.setString(2, user[1]); // Establece el segundo parámetro (password)
                pstmt.setString(3, user[2]); // Establece el tercer parámetro (role)
                pstmt.executeUpdate(); // Ejecuta la consulta para insertar los datos
            }
            System.out.println("Usuarios de prueba insertados.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearSensores() {
        String sql = "CREATE TABLE IF NOT EXISTS Sensores (" +
                "id_sensor INTEGER NOT NULL AUTO_INCREMENT, " +
                "tipo_dato VARCHAR(100), " +
                "fecha DATE, " +
                "valor DECIMAL(10,2)," +
                "id_usuario INT NOT NULL," +
                "FOREIGN KEY (id_usuario)," +
                "PRIMARY KEY (id_sensor));";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) { // Crea un objeto Statement para ejecutar la consulta
            stmt.executeUpdate(sql); // Ejecuta la consulta SQL para crear la tabla
            System.out.println("Tabla Sensores creada o ya existente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearChat() {
        String sql = "CREATE TABLE IF NOT EXISTS Chats (" +
                "id_chat INTEGER NOT NULL AUTO_INCREMENT, " +
                "contenido LONGTEXT, " +
                "fecha DATE, " +
                "id_usuario1 INTEGER NOT NULL," +
                "id_usuario2 INTEGER NOT NULL," +
                "FOREIGN KEY (id_usuario1)," +
                "FOREIGN KEY (id_usuario2)," +
                "PRIMARY KEY (id));";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) { // Crea un objeto Statement para ejecutar la consulta
            stmt.executeUpdate(sql); // Ejecuta la consulta SQL para crear la tabla
            System.out.println("Tabla Chats creada o ya existente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearRutinaUsuario() {
        String sql = "CREATE TABLE IF NOT EXISTS RutinasUsuario (" +
                "id_usuario INTEGER NOT NULL AUTO_INCREMENT, " +
                "id_rutina INTEGER NOT NULL AUTO_INCREMENT, " +
                "PRIMARY KEY (id_usuario))," +
                "PRIMARY KEY (id_rutina))," +
                "FOREIGN KEY (id_usuario)," +
                "FOREIGN KEY (id_rutina),;";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) { // Crea un objeto Statement para ejecutar la consulta
            stmt.executeUpdate(sql); // Ejecuta la consulta SQL para crear la tabla
            System.out.println("Tabla RutinasUsuario creada o ya existente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearRutina() {
        String sql = "CREATE TABLE IF NOT EXISTS Rutinas (" +
                "id_rutina INTEGER NOT NULL AUTO_INCREMENT, " +
                "nombre VARCHAR(100), " +
                "dias INT, " +
                "PRIMARY KEY (id_rutina));";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) { // Crea un objeto Statement para ejecutar la consulta
            stmt.executeUpdate(sql); // Ejecuta la consulta SQL para crear la tabla
            System.out.println("Tabla Rutinas creada o ya existente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearRutinaEjercicio() {
        String sql = "CREATE TABLE IF NOT EXISTS EjerciciosRutina (" +
                "id_rutina INTEGER NOT NULL AUTO_INCREMENT, " +
                "id_ejercicio INTEGER NOT NULL AUTO_INCREMENT, " +
                "FOREIGN KEY (id_rutina)," +
                "FOREIGN KEY (id_ejercicio)," +
                "PRIMARY KEY (id_rutina))," +
                "PRIMARY KEY (id_ejercicio));";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) { // Crea un objeto Statement para ejecutar la consulta
            stmt.executeUpdate(sql); // Ejecuta la consulta SQL para crear la tabla
            System.out.println("Tabla EjerciciosRutina creada o ya existente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearEjercicio() {
        String sql = "CREATE TABLE IF NOT EXISTS Ejercicios (" +
                "id_ejercicio INTEGER NOT NULL AUTO_INCREMENT, " +
                "nombre VARCHAR(100), " +
                "grupo_muscular VARCHAR(100), " +
                "peso DECIMAL(5, 2)," +
                "series INTEGER," +
                "repeticiones INTEGER," +
                "descanso INTEGER," +
                "PRIMARY KEY (id_ejercicio));";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) { // Crea un objeto Statement para ejecutar la consulta
            stmt.executeUpdate(sql); // Ejecuta la consulta SQL para crear la tabla
            System.out.println("Tabla Ejercicios creada o ya existente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para autenticar a un usuario y obtener su rol
    public String login(String email, String password) {
        String sql = "SELECT role FROM USERS WHERE email=? AND password=?"; // Consulta para verificar credenciales
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara la consulta SQL con parámetros

            pstmt.setString(1, email); // Sustituye el primer parámetro con el email ingresado
            pstmt.setString(2, password); // Sustituye el segundo parámetro con la contraseña ingresada
            ResultSet rs = pstmt.executeQuery(); // Ejecuta la consulta y obtiene el resultado

            // Verifica si el usuario existe y obtiene su rol
            if (rs.next()) { // Si hay un resultado, el usuario existe
                return rs.getString("role"); // Retorna el rol del usuario
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encontró el usuario o hubo un error
    }
}
