package example2;

import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(5009);
        System.out.println("Server started. Waiting for clients to connect...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");

        // Create input and output streams
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        // Read object and text data from example1.client
        Object object = objectInputStream.readObject();
        String text = bufferedReader.readLine();

        // Print object and text data received from example1.client
        System.out.println("Object received from example1.client: " + object.toString());
        System.out.println("Text received from example1.client: " + text);

        // Send response back to example1.client
        String response = "Hello from server!";
        bufferedWriter.write(response);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        // Close streams and socket
        objectInputStream.close();
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
    }
}
