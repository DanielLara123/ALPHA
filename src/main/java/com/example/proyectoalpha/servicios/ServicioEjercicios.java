package com.example.proyectoalpha.servicios;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ServicioEjercicios {
    private static final String EJERCICIOS_FILE = "ejercicios.json";

    private Map<String, List<String>> ejercicios;

    // Constructor
    public ServicioEjercicios() {
        ejercicios = new HashMap<>();
        cargarEjercicios(); // Cargar datos desde JSON
    }

    // Método para cargar ejercicios desde el archivo JSON
    private void cargarEjercicios() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(EJERCICIOS_FILE);
            if (file.exists()) {
                Map<String, Map<String, List<String>>> jsonData =
                        mapper.readValue(file, new TypeReference<>() {});
                ejercicios = jsonData.getOrDefault("ejercicios", new HashMap<>());
            } else {
                System.out.println("El archivo " + EJERCICIOS_FILE + " no existe. Se inicializará vacío.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar ejercicios desde el archivo.");
        }
    }

    // Método para guardar ejercicios en el archivo JSON
    public void guardarEjercicios() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Map<String, List<String>>> jsonData = new HashMap<>();
            jsonData.put("ejercicios", ejercicios); // Encapsular en el nivel superior "ejercicios"
            mapper.writeValue(new File(EJERCICIOS_FILE), jsonData);
            System.out.println("Ejercicios guardados en el archivo.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar ejercicios en el archivo.");
        }
    }

    // Método para añadir un ejercicio a una categoría
    public void agregarEjercicio(String categoria, String ejercicio) {
        if (ejercicios.containsKey(categoria)) {
            ejercicios.get(categoria).add(ejercicio);
            System.out.println("Ejercicio añadido a " + categoria + ": " + ejercicio);
            guardarEjercicios(); // Guardar cambios
        } else {
            System.out.println("Categoría no válida: " + categoria);
        }
    }

    // Método para obtener los ejercicios de una categoría
    public List<String> obtenerEjercicios(String categoria) {
        return ejercicios.getOrDefault(categoria, new ArrayList<>());
    }

    // Método para mostrar todos los ejercicios
    public void mostrarTodos() {
        for (Map.Entry<String, List<String>> entry : ejercicios.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // Método opcional para eliminar un ejercicio
    public void eliminarEjercicio(String categoria, String ejercicio) {
        if (ejercicios.containsKey(categoria) && ejercicios.get(categoria).remove(ejercicio)) {
            System.out.println("Ejercicio eliminado de " + categoria + ": " + ejercicio);
            guardarEjercicios(); // Guardar cambios
        } else {
            System.out.println("No se pudo encontrar el ejercicio o categoría.");
        }
    }
}
