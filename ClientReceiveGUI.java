package Client_Server;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

import java.awt.event.ActionEvent;
import java.io.PrintWriter;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceiveGUI extends JFrame {
    private JTextArea textArea;

    public ClientReceiveGUI() {
        setTitle("Client - Receiver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        setVisible(true);

        connectAndListen();
    }

    private void connectAndListen() {
        try {
            Socket socket = new Socket("localhost", 1234);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            while ((message = reader.readLine()) != null) {
                String finalMessage = message;
                SwingUtilities.invokeLater(() -> textArea.append("Received: " + finalMessage + "\n"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientReceiveGUI::new);
    }
}
