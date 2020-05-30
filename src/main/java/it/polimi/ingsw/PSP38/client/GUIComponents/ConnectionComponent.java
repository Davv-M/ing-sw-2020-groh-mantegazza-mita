package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.ServerHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectionComponent extends JComponent implements ActionListener {
    private final static int SERVER_SOCKET_PORT = 3456;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private JFrame frame = new JFrame();
    private JTextField ipAddress = new JTextField();
    private JButton connectButton = new JButton();
    private String selectedIp;
    private Socket serverSocket;
    private static ServerHandler nextInputObserver;

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
            selectedIp=ipAddress.getText();
            //System.out.println("Ip selezionato "+selectedIp);
            connectionHandling(selectedIp);
        }
        setVisible(false);
    }

    public void connectionHandling(String address){
        try {
            InetAddress addr = InetAddress.getByName(address);
            serverSocket = new Socket(addr, SERVER_SOCKET_PORT);
            ServerHandler serverHandler = new ServerHandler(serverSocket);
            nextInputObserver = serverHandler;
            Thread thread = new Thread(serverHandler);
            thread.start();
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");
    }
}