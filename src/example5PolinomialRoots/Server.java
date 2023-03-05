package example5PolinomialRoots;

import java.io.*;
import java.net.*;


/**
 * In this implementation, the Client class creates a BufferedReader to read input from the server and a BufferedWriter to send output to the server.
 *
 * When sending the polynomial equation, the BufferedWriter uses the "write()" method to write the equation as a string and the newLine() method to add a newline character.
 * Finally, the flush() method is called on the BufferedWriter to ensure that any buffered data is sent to the server.
 *
 * Note that using BufferedWriter instead of PrintWriter is not strictly necessary in this example,
 * but it may be useful in certain situations where more control over the output stream is needed.
 */

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5009);
        System.out.println("Server started.");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String equation = in.readLine();
            System.out.println("Received equation: " + equation);

            String[] coefficients = equation.split(",");
            double a = Double.parseDouble(coefficients[0]);
            double b = Double.parseDouble(coefficients[1]);
            double c = Double.parseDouble(coefficients[2]);

            double discriminant = b * b - 4 * a * c;
            if (discriminant > 0) {
                double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                out.write(root1 + "," + root2);
                out.newLine();
            } else if (discriminant == 0) {
                double root = -b / (2 * a);
                out.write(root + "," + root);
                out.newLine();
            } else {
                out.write("no roots");
                out.newLine();
            }
            out.flush();

            clientSocket.close();
        }
    }
}
