package example4Files;

import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server is listening on port 8080");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New client connected");

            SocketHandler socketHandler = new SocketHandler(socket);
            new Thread(socketHandler).start();
        }
    }
}

class SocketHandler implements Runnable {
    private final Socket socket;
    private final String FILE_DIR = "server_files/";

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            while (true) {
                String messageType = dataInputStream.readUTF();

                if (messageType.equals("sendFile")) {
                    String fileName = dataInputStream.readUTF();
                    long fileSize = dataInputStream.readLong();
                    File file = new File(FILE_DIR + fileName);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[4096];
                    int read;
                    long remaining = fileSize;
                    while ((read = objectInputStream.read(buffer, 0, (int) Math.min(buffer.length, remaining))) > 0) {
                        remaining -= read;
                        fileOutputStream.write(buffer, 0, read);
                        if (remaining == 0) {
                            break;
                        }
                    }
                    fileOutputStream.close();
                    System.out.println("File received: " + file.getName());
                } else if (messageType.equals("requestFile")) {
                    String fileName = dataInputStream.readUTF();
                    File file = new File(FILE_DIR + fileName);
                    if (file.exists()) {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] buffer = new byte[4096];
                        int read;
                        while ((read = fileInputStream.read(buffer)) != -1) {
                            objectOutputStream.write(buffer, 0, read);
                        }
                        fileInputStream.close();
                        System.out.println("File sent: " + file.getName());
                    } else {
                        System.out.println("File not found: " + file.getName());
                    }
                } else if (messageType.equals("sendMessage")) {
                    String message = objectInputStream.readUTF();
                    System.out.println("Message received: " + message);
                    objectOutputStream.writeUTF("Message received");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
