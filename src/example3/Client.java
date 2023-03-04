package example3;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Socket socket = new Socket("localhost", 5005);
        System.out.println("Connected to server.");
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream =null;
        BufferedReader bufferedReader =null;
        BufferedWriter bufferedWriter =null;
        InputStreamReader inputStreamReader=null;
        OutputStreamWriter outputStreamWriter =null;


        inputStreamReader =  new InputStreamReader(socket.getInputStream());
        // Create input and output streams

        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Send text data to server
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write("send");
        bufferedWriter.newLine();
        bufferedWriter.flush();

        // Create object to send
        MyObject object = new MyObject();

        // Send object to server
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        System.out.println("Object sent to server: " + object.toString());

        // Receive response from server
        String response = bufferedReader.readLine();
        System.out.println("Response from server: " + response);

        // Request objects from server
        Thread.sleep(10000);
        bufferedWriter.write("retrieve");
        bufferedWriter.newLine();
        bufferedWriter.flush();
        objectInputStream = new ObjectInputStream(socket.getInputStream());

        // Receive objects from server

        MyObject receivedObject = (MyObject) objectInputStream.readObject();
        System.out.println("Object received from server: " + receivedObject.toString());

        // Close streams and socket
        objectOutputStream.close();
        bufferedReader.close();
        bufferedWriter.close();
        objectInputStream.close();
        socket.close();
        System.out.println ("Connection closed");
    }
}
