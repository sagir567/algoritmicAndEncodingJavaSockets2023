package example4Files;

import java.net.*;
import java.io.*;

public class Client {
    private final String SERVER_ADDRESS = "localhost";
    private final int SERVER_PORT = 8080;
    private final String FILE_DIR = "client_files/";

    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private DataOutputStream dataOutputStream;

    public Client() throws IOException {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        System.out.println("Connected to server");

        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void sendFile(File file) throws IOException {
        dataOutputStream.writeUTF("sendFile");
        dataOutputStream.writeUTF(file.getName());

        FileInputStream fileInputStream = new FileInputStream(file);
        long fileSize = file.length();
        dataOutputStream.writeLong(fileSize);
        byte[] buffer = new byte[4096];
        int read;
        long remaining = fileSize;
        while ((read = fileInputStream.read(buffer, 0, (int) Math.min(buffer.length, remaining))) > 0) {
            remaining -= read;
            objectOutputStream.write(buffer, 0, read);
            if (remaining == 0) {
                break;
            }
        }
        fileInputStream.close();
        System.out.println("File sent: " + file.getName());
    }

    public void requestFile(String fileName) throws IOException {
        dataOutputStream.writeUTF("requestFile");
        dataOutputStream.writeUTF(fileName);

        File file = new File(FILE_DIR + fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] buffer = new byte[4096];
        int read;
        while ((read = objectInputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, read);
        }
        fileOutputStream.close();
        System.out.println("File received: " + file.getName());
    }

    public void sendMessage(String message) throws IOException {
        dataOutputStream.writeUTF("sendMessage");
        objectOutputStream.writeUTF(message);
        String response = objectInputStream.readUTF();
        System.out.println("Server response: " + response);
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();

        File fileToSend = new File("client_files/file.txt");
        client.sendFile(fileToSend);

        String fileToRequest = "file.txt";
        client.requestFile(fileToRequest);

        String messageToSend = "Hello, server!";


    }
}