package example5PolinomialRoots;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5009);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner sc = new Scanner(System.in);
        int input=1;
        String equation;
        while (input!=0){
            System.out.println("input 1 to send new polynom \n" +
                                "input 0 to exit\n");
            input= sc.nextInt();

            switch (input){
                case 1:
                    equation ="";
                    for (int i = 0;i<3;i++){
                        System.out.println("please add the x^"+i);
                        equation+= Integer.toString(sc.nextInt());
                        equation+=",";
                    }
                    System.out.println("Sending equation: " + equation);
                    out.println(equation);

                    String response = in.readLine();
                    System.out.println("Received roots: " + response);


                    break;
                case 0:
                    socket.close();


                default:
                    System.out.println("please add valid input!");
            }



        }





    }
}
