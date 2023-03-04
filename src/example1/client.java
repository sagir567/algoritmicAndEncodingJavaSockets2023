package example1;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class client
{


    Socket socket =null;
    String ip ="localhost";
    int portNum =1234;
    InputStreamReader inputStreamReader= null;
    OutputStreamWriter outputStreamWriter = null;
    BufferedReader bufferedReader =null;
    BufferedWriter bufferedWriter = null;


    Scanner sc;

    public client () throws IOException {
        try{

            this.socket = new Socket(this.ip,this.portNum);
            this.inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            this.bufferedReader  = new BufferedReader(inputStreamReader);
            this.bufferedWriter = new BufferedWriter(outputStreamWriter);

            this.sc = new Scanner(System.in);



        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            {
                if(socket!= null)socket.close();
                if(inputStreamReader != null)inputStreamReader.close();
                if(outputStreamWriter!=null)outputStreamWriter.close();
                if(bufferedReader != null)bufferedReader.close();
                if(bufferedWriter != null) bufferedWriter.close();
            }
        }


    }
    public void sendData() throws IOException {

        try {
            while (true) {

                String msg = this.sc.nextLine();
                bufferedWriter.write(msg);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                System.out.println("Server: " + bufferedReader.readLine());

                if (msg.equalsIgnoreCase("BYE")) ;
                break;
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            {
                if(socket!= null)socket.close();
                if(inputStreamReader != null)inputStreamReader.close();
                if(outputStreamWriter!=null)outputStreamWriter.close();
                if(bufferedReader != null)bufferedReader.close();
                if(bufferedWriter != null) bufferedWriter.close();
            }
        }
    }

}
