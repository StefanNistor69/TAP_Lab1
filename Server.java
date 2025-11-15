import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {

    private static boolean running = true;
    private static final List<Socket> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        int port = 5000;

        // Thread separat care ascultă comenzi din consola serverului
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String cmd = scanner.nextLine();
                if (cmd.equalsIgnoreCase("shutdown")) {
                    System.out.println("Server is closing...");
                    running = false;
                    closeAllClients();
                    break;
                }
            }
        }).start();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            System.out.println("Write shutdown to close the server");

            while (running) {
                try {
                    serverSocket.setSoTimeout(1000); // verifică periodic dacă serverul trebuie să se închidă
                    Socket clientSocket = serverSocket.accept();

                    clients.add(clientSocket);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    new Thread(new ClientHandler(clientSocket)).start();

                } catch (SocketTimeoutException e) {
                    // doar verificăm periodic
                }
            }

            System.out.println("Server closed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeAllClients() {
        for (Socket s : clients) {
            try {
                s.close();
            } catch (IOException ignored) {}
        }
        clients.clear();
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true)
        ) {
            out.println("Trimite cel puțin 3 rânduri. Scrie 'exit' pentru a închide conexiunea.");

            String line;
            int count = 0;
            StringBuilder content = new StringBuilder();

            while ((line = in.readLine()) != null) {
                if (line.equalsIgnoreCase("exit")) {
                    out.println("Conexiunea a fost închisă de client.");
                    break;
                }

                content.append(line).append("\n");
                count++;

                if (count >= 3) {
                    content.append("Line added by server 1\n");
                    content.append("Line added by server 2\n");

                    out.println("----- Răspuns de la server -----\n" + content);

                    content.setLength(0);
                    count = 0;
                }
            }

            System.out.println("Client deconectat: " + clientSocket.getInetAddress());
            clientSocket.close();

        } catch (IOException e) {
            System.out.println("Client deconectat forțat.");
        }
    }
}
