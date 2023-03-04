package exmple3Serializeble;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    String ip = "localhost";
    int port = 10000;
    ServerSocket serverSocket = null;
    InputStreamReader inputStreamReader = null;
    OutputStreamWriter outputStreamWriter = null;
    BufferedReader bufferedReader = null;
    BufferedWriter bufferedWriter = null;
    ObjectInputStream objectInputStream = null;
    ObjectOutputStream objectOutputStream = null;

    public Server(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        serverSocket = new ServerSocket(this.port);
        System.out.println("waiting for clients...");
    }


    private void listen() throws IOException {
        while (true) {


            try {
                Socket client = serverSocket.accept();
                System.out.println("new connection establish;");
                this.inputStreamReader = new InputStreamReader(client.getInputStream());
                this.outputStreamWriter = new OutputStreamWriter(client.getOutputStream());
                this.bufferedWriter = new BufferedWriter(this.outputStreamWriter);
                this.bufferedReader = new BufferedReader(this.inputStreamReader);
                this.objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                this.objectInputStream = new ObjectInputStream(client.getInputStream());


                String s = bufferedReader.readLine();
                if (s.contains("<send>")) {  // this is the problem
                    this.bufferedWriter.write("<ack><send>");
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                }

                Thread.sleep(3000);
                Car c = ((Car) objectInputStream.readObject());
                Thread.sleep(500);
                System.out.println(c.color);
                bufferedWriter.write("object received successfully");
                bufferedWriter.newLine();
                bufferedWriter.flush();
                System.out.println("object received successfully");


            } catch (IOException e) {
                System.out.println("exit trough error");
                close();
                throw new RuntimeException(e);

            } catch (ClassNotFoundException e) {
                System.out.println("exit trough error");
                close();
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

    }

    public void close() throws IOException {
        if (this.serverSocket != null) this.serverSocket.close();
        if (this.inputStreamReader != null) this.inputStreamReader.close();
        if (outputStreamWriter != null) this.outputStreamWriter.close();
        if (bufferedReader != null) this.bufferedReader.close();
        if (bufferedWriter != null) this.bufferedWriter.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server("localhost", 10000);
        server.listen();

    }

}
