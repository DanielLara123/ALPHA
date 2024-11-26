package com.example.proyectoalpha.clases.Atleta;

import java.util.Timer;
import java.util.TimerTask;

public class Notificaciones {
    private Atleta atleta;
    private Timer timer;

    public Notificaciones(Atleta atleta) {
        this.atleta = atleta;
        this.timer = new Timer();
    }

    public void programarRecordatorio(long delay, long period) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                notificarUsuario();
            }
        }, delay, period);
    }

    public void cancelarRecordatorio() {
        timer.cancel();
    }

    private void notificarUsuario() {
        System.out.println("Es hora de entrenar, " + atleta.getCorreo() + "!");
    }
}