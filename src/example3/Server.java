package example3;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(5005);
        System.out.println("Server started. Waiting for clients to connect...");

        List<MyObject> objectList = new ArrayList<>();

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");

            // Create input and output streams
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Read data from client
            String request = bufferedReader.readLine();
            System.out.println("Request received from client: " + request);

            if (request.equals("send")) {
                // Receive object from client
                MyObject object = (MyObject) objectInputStream.readObject();
                objectList.add(object);
                System.out.println("Object received from client: " + object.toString());



                // Send response to client
                bufferedWriter.write("Object received by server!");
                bufferedWriter.newLine();
                bufferedWriter.flush();

            }
            if (request.equals("retrieve")) {
                System.out.println("sending files");
                // Send object list to client
                for (MyObject obj : objectList) {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(obj);
                    objectOutputStream.flush();
                }
            }

            // Close streams and socket
            objectInputStream.close();
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
        }
    }
}
