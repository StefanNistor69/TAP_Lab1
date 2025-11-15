import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try (
            Socket socket = new Socket(host, port);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("Conectat la server.");
            System.out.println(in.readLine());

            String userInput;

            while (true) {
                System.out.print("Write a line (or 'exit'): ");
                userInput = console.readLine();

                out.println(userInput);

                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }

                // citim ce trimite serverul
                if (in.ready()) {
                    String serverMsg;
                    while (in.ready() && (serverMsg = in.readLine()) != null) {
                        System.out.println(serverMsg);
                    }
                }
            }

            System.out.println("Clientul s-a închis.");

        } catch (IOException e) {
            System.out.println("Serverul s-a închis. Conexiune pierdută.");
        }
    }
}
