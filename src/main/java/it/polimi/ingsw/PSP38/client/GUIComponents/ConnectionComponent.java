package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameModeGUI;
import it.polimi.ingsw.PSP38.client.ServerHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectionComponent extends JComponent implements ActionListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private final JTextField ipAddress = new JTextField();
    private JButton connectButton = new JButton();


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    public void createConnectionWindow(){
        setLayout(new GridLayout(3,1));
        JLabel title = new JLabel("santorini");
        add(title);
        add(createIpAddress());
        add(createConnectButton());
    }

    public JPanel createIpAddress() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2,1));
        JLabel ipLabel = new JLabel("IP address");
        controlPanel.add(ipLabel);
        controlPanel.add(ipAddress);
        return controlPanel;
    }

    public JPanel createConnectButton(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        connectButton = new JButton("Connect to server");
        connectButton.addActionListener(this);
        buttonPanel.add(connectButton);
        return buttonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==connectButton){
            String selectedIp = ipAddress.getText();
            connectionHandling(selectedIp);
        }
        setVisible(false);
    }

    public void connectionHandling(String address){
        try {
            InetAddress addr = InetAddress.getByName(address);
            Socket serverSocket = new Socket(addr, GameModeGUI.SERVER_SOCKET_PORT);
            GameModeGUI.setServerSocket(serverSocket);
            ServerHandler serverHandler = new ServerHandler(serverSocket);
            GameModeGUI.setServerHandler(serverHandler);
            Thread thread = new Thread(serverHandler);
            thread.start();
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");
    }
}