package com.example.proyectoalpha.servicios;

import com.example.proyectoalpha.clases.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioEjercicios {
    private static final String EJERCICIOS_FILE = "ejercicios.json";

    private Map<String, List<String>> ejercicios;

    // Constructor
    public ServicioEjercicios() {
        ejercicios = new HashMap<>();

        // Inicializar las categorías con listas vacías
        String[] categorias = {
                "pecho", "espalda", "hombro", "abdominales",
                "biceps", "triceps", "cuadriceps", "gemelos",
                "femoral", "aductor", "abductor", "antebrazo"
        };

        for (String categoria : categorias) {
            ejercicios.put(categoria, new ArrayList<>());
        }
    }

    // Método para añadir un ejercicio a una categoría
    public void agregarEjercicio(String categoria, String ejercicio) {
        // Verificar si la categoría existe
        if (ejercicios.containsKey(categoria)) {
            ejercicios.get(categoria).add(ejercicio);
            System.out.println("Ejercicio añadido a " + categoria + ": " + ejercicio);
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
}



