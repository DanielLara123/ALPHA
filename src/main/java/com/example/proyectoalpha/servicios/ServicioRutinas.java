package com.example.proyectoalpha.servicios;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ServicioRutinas {
    private static final String RUTINAS_FILE = "rutinas.json";

    private Map<String, Map<String, Object>> rutinas; // Almacena las rutinas

    // Constructor
    public ServicioRutinas() {
        rutinas = new HashMap<>();
        cargarRutinas(); // Cargar datos desde el archivo JSON
    }

    // Método para cargar rutinas desde el archivo JSON
    private void cargarRutinas() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(RUTINAS_FILE);
            if (file.exists()) {
                Map<String, Map<String, Map<String, Object>>> jsonData =
                        mapper.readValue(file, new TypeReference<>() {});
                rutinas = jsonData.getOrDefault("rutinas", new HashMap<>());
            } else {
                System.out.println("El archivo " + RUTINAS_FILE + " no existe. Se inicializará vacío.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar rutinas desde el archivo.");
        }
    }

    // Método para guardar rutinas en el archivo JSON
    public void guardarRutinas() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Map<String, Map<String, Object>>> jsonData = new HashMap<>();
            jsonData.put("rutinas", rutinas); // Encapsular en el nivel superior "rutinas"
            mapper.writeValue(new File(RUTINAS_FILE), jsonData);
            System.out.println("Rutinas guardadas en el archivo.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar rutinas en el archivo.");
        }
    }

    // Método para añadir una rutina
    public void agregarRutina(String nombreRutina, Map<String, Object> rutinaData) {
        if (rutinas.containsKey(nombreRutina)) {
            System.out.println("Ya existe una rutina con el nombre: " + nombreRutina);
        } else {
            rutinas.put(nombreRutina, rutinaData);
            System.out.println("Rutina añadida: " + nombreRutina);
            guardarRutinas(); // Guardar cambios
        }
    }

    // Método para obtener una rutina por su nombre
    public Map<String, Object> obtenerRutina(String nombreRutina) {
        return rutinas.getOrDefault(nombreRutina, new HashMap<>());
    }

    // Método para eliminar una rutina
    public void eliminarRutina(String nombreRutina) {
        if (rutinas.remove(nombreRutina) != null) {
            System.out.println("Rutina eliminada: " + nombreRutina);
            guardarRutinas(); // Guardar cambios
        } else {
            System.out.println("No se encontró la rutina: " + nombreRutina);
        }
    }

    // Método para mostrar todas las rutinas
    public void mostrarTodas() {
        for (Map.Entry<String, Map<String, Object>> entry : rutinas.entrySet()) {
            System.out.println("Rutina: " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}
