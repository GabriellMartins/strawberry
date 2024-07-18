package com.br.strawberry.server;

import java.io.*;
import java.net.*;

public class FileTransferClient {
    private Socket socket;
    private FileInputStream fileInputStream;
    private BufferedOutputStream outputStream;

    public FileTransferClient(String address, int port, String filePath) {
        try {
            socket = new Socket(address, port);
            fileInputStream = new FileInputStream(filePath);
            outputStream = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFile() {
        try {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            fileInputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileTransferClient client = new FileTransferClient("localhost", 5001, "path/to/file.txt");
        client.sendFile();
    }
}
