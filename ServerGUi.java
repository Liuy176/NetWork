package Client_Server;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerGUi extends JFrame {
    private JTextArea textArea;
    private ServerSocket serverSocket;
    private Socket sendClientSocket, receiveClientSocket;
    private ExecutorService pool = Executors.newFixedThreadPool(2);

    public ServerGUi() throws IOException {
        setTitle("Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        setVisible(true);

        serverSocket = new ServerSocket(1234);
        textArea.append("Server started. Waiting for clients...\n");

        pool.execute(this::handleSendClient);
        pool.execute(this::handleReceiveClient);
    }

    private void handleSendClient() {
        try {
            sendClientSocket = serverSocket.accept();
            textArea.append("Send Client Connected.\n");

            BufferedReader reader = new BufferedReader(new InputStreamReader(sendClientSocket.getInputStream()));
            String message;
            while ((message = reader.readLine()) != null) {
                textArea.append("Received from Send Client: " + message + "\n");
                forwardMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleReceiveClient() {
        try {
            receiveClientSocket = serverSocket.accept();
            textArea.append("Receive Client Connected.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void forwardMessage(String message) {
        if (receiveClientSocket != null && !receiveClientSocket.isClosed()) {
            try {
                PrintWriter writer = new PrintWriter(receiveClientSocket.getOutputStream(), true);
                writer.println(message);
                textArea.append("Forwarded: " + message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ServerGUi();
    }
}
