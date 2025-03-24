package com.example.proyectoalpha.controller.Chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorController {
    private static final int PUERTO = 12345;
    private static Map<String, PrintWriter> clientes = new HashMap<>();
    private ServerSocket serverSocket;

    public ServidorController() throws IOException {
        serverSocket = new ServerSocket(PUERTO);
    }

    public static int getPuerto() {
        return PUERTO;
    }

    public void iniciar() {
        new Thread(() -> {
            try {
                while (true) {
                    Socket socket = serverSocket.accept();
                    new ManejadorCliente(socket).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static class ManejadorCliente extends Thread {
        private Socket socket;
        private String nombreUsuario;
        private PrintWriter salida;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                salida = new PrintWriter(socket.getOutputStream(), true);

                // Recibir el nombre del usuario
                nombreUsuario = entrada.readLine();
                synchronized (clientes) {
                    clientes.put(nombreUsuario, salida);
                }

                String mensaje;
                while ((mensaje = entrada.readLine()) != null) {
                    String[] partes = mensaje.split(":", 2);
                    if (partes.length == 2) {
                        String destinatario = partes[0];
                        String mensajeTexto = partes[1];
                        enviarMensaje(destinatario, nombreUsuario + ": " + mensajeTexto);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (nombreUsuario != null) {
                    synchronized (clientes) {
                        clientes.remove(nombreUsuario);
                    }
                }
            }
        }

        private void enviarMensaje(String destinatario, String mensaje) {
            synchronized (clientes) {
                PrintWriter escritor = clientes.get(destinatario);
                if (escritor != null) {
                    escritor.println(mensaje);
                }
            }
        }
    }
}