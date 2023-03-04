package example2;
import example3.MyObject;

import java.net.*;
import java.io.*;

/**
 * In this example, the example1.client connects to the server using the IP address "localhost" and port number 5000. The example1.client sends an object and text data to the server using the output streams. The server receives the object and text data using the input streams and then sends a response back to the example1.client using the output streams. The example1.client receives the response from the server using the input stream.
 */
public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String serverAddress = "localhost";
        int serverPort = 5009;

        // Create socket
        Socket socket = new Socket(serverAddress, serverPort);
        System.out.println("Connected to server!");

        // Create input and output streams
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Send object and text data to server
        MyObject object = new MyObject();
        String text = "Hello from example1.client!";
        objectOutputStream.writeObject(object);
        bufferedWriter.write(text);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        // Receive response from server
        String response = bufferedReader.readLine();
        System.out.println("Response from server: " + response);

        // Close streams and socket
        objectOutputStream.close();
        bufferedWriter.close();
        bufferedReader.close();
        socket.close();
    }
}

