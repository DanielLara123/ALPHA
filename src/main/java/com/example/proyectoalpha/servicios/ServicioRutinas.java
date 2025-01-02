package com.example.proyectoalpha.servicios;

import com.example.proyectoalpha.clases.Atleta.Rutina;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServicioRutinas {

    private final ObjectMapper objectMapper;

    public ServicioRutinas() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Rutina> loadRutinas(String correoUsuario) throws IOException {
        File file = new File(correoUsuario + "_rutinas.json");
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Rutina.class);
        return objectMapper.readValue(file, listType);
    }

    public void saveRutinas(String correoUsuario, List<Rutina> rutinas) throws IOException {
        File file = new File(correoUsuario + "_rutinas.json");
        objectMapper.writeValue(file, rutinas);
    }

    public Rutina getRutinaByName(String correoUsuario, String nombre) {
        try {
            List<Rutina> rutinas = loadRutinas(correoUsuario);
            for (Rutina rutina : rutinas) {
                if (rutina.getNombre().equals(nombre)) {
                    return rutina;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void eliminarRutina(String correoUsuario, String s) {
        try {
            List<Rutina> rutinas = loadRutinas(correoUsuario);
            Rutina rutina = getRutinaByName(correoUsuario, s);
            rutinas.remove(rutina);
            saveRutinas(correoUsuario, rutinas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}