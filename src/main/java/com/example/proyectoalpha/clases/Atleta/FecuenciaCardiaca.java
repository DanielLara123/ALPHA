package com.example.proyectoalpha.clases.Atleta;

public class FecuenciaCardiaca {
    private Atleta atleta;
    private int frecuenciaCardiaca;

    public FecuenciaCardiaca(Atleta atleta) {
        this.atleta = atleta;
    }

    public int getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(int frecuenciaCardiaca) {
        if (frecuenciaCardiaca < 0) {
            throw new IllegalArgumentException("La frecuencia cardiaca no puede ser negativa");
        }
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public void calcularFrecuenciaCardiaca(int edad, int frecuenciaCardiacaReposo) {
        int maximo = 220 - edad;
        this.frecuenciaCardiaca = (int) ((maximo - frecuenciaCardiacaReposo) * 0.7 + frecuenciaCardiacaReposo);
    }


}
