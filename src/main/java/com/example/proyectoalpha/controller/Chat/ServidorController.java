package com.example.proyectoalpha.controller.Chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorController {
    private static final int PORT = 12345;
    private static Map<String, ClientHandler> clients = new HashMap<>();

    public static void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Servidor iniciado en el puerto " + PORT);
                while (true) {
                    Socket socket = serverSocket.accept();
                    new ClientHandler(socket).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;
        private String chatPartner;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Recibir nombre de usuario
                username = in.readLine();
                synchronized (clients) {
                    clients.put(username, this);
                }

                // Recibir nombre del usuario con quien desea chatear
                chatPartner = in.readLine();

                // Esperar a que ambos usuarios estén listos
                while (true) {
                    synchronized (clients) {
                        if (clients.containsKey(chatPartner) && clients.get(chatPartner).chatPartner.equals(username)) {
                            out.println("Conectado con " + chatPartner);
                            clients.get(chatPartner).out.println("Conectado con " + username);
                            break;
                        }
                    }
                    Thread.sleep(500);
                }

                // Chat entre los dos usuarios
                String message;
                while ((message = in.readLine()) != null) {
                    synchronized (clients) {
                        if (clients.containsKey(chatPartner)) {
                            clients.get(chatPartner).out.println(username + ": " + message);
                        }
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    synchronized (clients) {
                        clients.remove(username);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

