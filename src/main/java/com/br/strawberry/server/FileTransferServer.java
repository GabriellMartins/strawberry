package com.br.strawberry.server;

import java.io.*;
import java.net.*;

public class FileTransferServer {
    private ServerSocket serverSocket;

    public FileTransferServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new FileTransferHandler(clientSocket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        FileTransferServer server = new FileTransferServer(5001);
        server.start();
    }
}

class FileTransferHandler extends Thread {
    private Socket clientSocket;
    private BufferedInputStream inputStream;
    private FileOutputStream fileOutputStream;

    public FileTransferHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            inputStream = new BufferedInputStream(clientSocket.getInputStream());
            fileOutputStream = new FileOutputStream("received_file.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.close();
            inputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
