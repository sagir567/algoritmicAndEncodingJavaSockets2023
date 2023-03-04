package exmple3Serializeble;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket s = null;
    private InputStreamReader inputStreamReader = null;
    private OutputStreamWriter outputStreamWriter = null;
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;
    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;
    private int port;
    private String ip = "localhost";


    public Client(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        login(ip, port);


    }

    private void login(String ip, int port) throws IOException {
        try {
            this.s = new Socket(ip, port);
            System.out.println("connection establish");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
            this.inputStreamReader = new InputStreamReader(s.getInputStream());
            this.outputStreamWriter = new OutputStreamWriter(s.getOutputStream());
            this.bufferedWriter = new BufferedWriter(outputStreamWriter);
            this.bufferedReader = new BufferedReader(inputStreamReader);
            this.objectOutputStream = new ObjectOutputStream(s.getOutputStream());
            this.objectInputStream = new ObjectInputStream(s.getInputStream());



        } catch (Exception e) {
            e.printStackTrace();
            close();

        }
    }
    public void sendObject(Object o) throws IOException, InterruptedException {
        if (this.s == null) throw new RuntimeException("socket is null");
        if (this.bufferedWriter == null) throw new RuntimeException("bufferedWriter is null");
        if (this.bufferedReader == null) throw new RuntimeException("bufferedReader is null");
        if (this.inputStreamReader == null) throw new RuntimeException("inputStreamReader is null");
        if(this.outputStreamWriter == null) throw new RuntimeException("outputStreamWriter is null");

        this.bufferedWriter.write("<send><Car>");
        this.bufferedWriter.newLine();
        this.bufferedWriter.flush();
        Thread.sleep(3000);
        String inFromServ = bufferedReader.readLine();


        int i = 0;
        while(inFromServ != null && i <= 3){

            if (inFromServ.contains("<ack><send>")) {
                System.out.println("ack received");
                this.objectOutputStream.writeObject(o);

                this.objectOutputStream.flush();
                System.out.println("object sent");
                break;
            }
            System.out.println("waiting for ack");
            Thread.sleep(3000);
            if (i == 3){
                System.out.println("timeout");
                s.close();
                break;
            }
            i++;
            inFromServ = bufferedReader.readLine();

        }


        inFromServ = bufferedReader.readLine();
        if (inFromServ.contains("object received successfully")) {
            System.out.println("done");
            close();
        }

    }


    public void close() throws IOException {
        if (this.s != null) this.s.close();
        if (this.inputStreamReader != null) this.inputStreamReader.close();
        if (outputStreamWriter != null) this.outputStreamWriter.close();
        if (bufferedReader != null) this.bufferedReader.close();
        if (bufferedWriter != null) this.bufferedWriter.close();
    }


    public static void main(String[] args) throws IOException, InterruptedException {


        Client c = new Client("localhost", 10000);
        c.sendObject(new Car("red"));


    }


}
