package Client_Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSendGUI extends JFrame {
    private JTextField textField;
    private PrintWriter writer;

    public ClientSendGUI() {
        setTitle("Client - Sender");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 100);
        setLocationRelativeTo(null);

        textField = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(this::sendMessage);

        setLayout(new FlowLayout());
        add(textField);
        add(sendButton);

        setVisible(true);

        connectToServer();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 1234);
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + e.getMessage());
            System.exit(1);
        }
    }

    private void sendMessage(ActionEvent event) {
        String message = textField.getText().trim();
        if (!message.isEmpty()) {
            writer.println(message);
            textField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientReceiveGUI::new);
    }
}
