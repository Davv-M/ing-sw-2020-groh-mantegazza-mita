package it.polimi.ingsw.PSP38.client;

import javax.swing.*;
import java.awt.*;

public class SetupWindow extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;
    public SetupWindow(){
        super();
        setSize(WIDTH, HEIGHT);
        setTitle("Santorini");
        setLayout(new GridLayout(3,1));

        JLabel title=new JLabel("santorini");
        add(title);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(8,1));
        JLabel ipLabel = new JLabel("IP address");
        controlPanel.add(ipLabel);
        JTextField ipAddress = new JTextField();
        controlPanel.add(ipAddress);
        JLabel nicknameLabel = new JLabel("Nickname");
        controlPanel.add(nicknameLabel);
        JTextField nickname = new JTextField();
        controlPanel.add(nickname);
        JLabel ageLabel = new JLabel("Age");
        controlPanel.add(ageLabel);
        JTextField age = new JTextField();
        controlPanel.add(age);
        JRadioButton guiButton = new JRadioButton("Play with GUI");
        controlPanel.add(guiButton);
        JRadioButton cliButton = new JRadioButton("Play with CLI");
        controlPanel.add(cliButton);
        add(controlPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton connectButton = new JButton("Connect to server");
        buttonPanel.add(connectButton);
        add(buttonPanel);
    }
}
