package com.br.strawberry.client;

import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private BufferedReader keyboard;

    public ChatClient(String address, int port) {
        try {
            socket = new Socket(address, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            keyboard = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        new Thread(new Receiver()).start();
        String message;
        try {
            while ((message = keyboard.readLine()) != null) {
                output.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Receiver implements Runnable {
        @Override
        public void run() {
            String message;
            try {
                while ((message = input.readLine()) != null) {
                    System.out.println("Server: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", 5002);
        client.start();
    }
}
