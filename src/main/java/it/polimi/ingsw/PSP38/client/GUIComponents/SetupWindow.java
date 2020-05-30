package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.Client;
import it.polimi.ingsw.PSP38.client.ImageCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Observable;

public class SetupWindow extends Observable implements ActionListener {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;
    private JFrame mainSetupFrame = new JFrame();
    private JPanel setupPanel;
    private JPanel controlPanel;
    private JPanel buttonPanel;
    private JLabel ipLabel;
    private JTextField ipAddress;
    private JLabel nicknameLabel;
    private JTextField nickname;
    private JLabel ageLabel;
    private JTextField age;
    private JButton connectButton;
    private Color panelColor = new Color(0,0,0,0);
    private final static Client clientUpdate = new Client();

    public void createSetupWindow(){
        mainSetupFrame.setSize(WIDTH, HEIGHT);
        mainSetupFrame.setTitle("Santorini");
        mainSetupFrame.add(createSetupPanel());
        mainSetupFrame.setVisible(true);
    }

    public JPanel createSetupPanel(){
        setupPanel = new JPanel();
        setupPanel.setLayout(new GridLayout(3,1));
        Color bkgColor = new Color(255, 0,0);
        setupPanel.setBackground(bkgColor);
        setupPanel.add(createImagePanel());
        setupPanel.add(createControlPanel());
        setupPanel.add(createButtonPanel());
        return setupPanel;
    }

    public JPanel createImagePanel() {
        Image santoriniLogo=null;
        JPanel imagePanel= new JPanel();
        imagePanel.setBackground(panelColor);
        File dir = null;
        try {
            dir = new File(Objects.requireNonNull(ImageCollection.class.getClassLoader()
                    .getResource("santorini-logo.png")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            santoriniLogo= ImageIO.read(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel logoLabel = new JLabel(new ImageIcon(santoriniLogo));
        imagePanel.add(logoLabel);
        return imagePanel;
    }
    public JPanel createControlPanel(){
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(6,1));
        controlPanel.setBackground(panelColor);
        ipLabel = new JLabel("IP address");
        controlPanel.add(ipLabel);
        ipAddress = new JTextField();
        controlPanel.add(ipAddress);
        nicknameLabel = new JLabel("Nickname");
        controlPanel.add(nicknameLabel);
        nickname = new JTextField();
        controlPanel.add(nickname);
        ageLabel = new JLabel("Age");
        controlPanel.add(ageLabel);
        age = new JTextField();
        controlPanel.add(age);
        return controlPanel;
    }

    public JPanel createButtonPanel(){
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(panelColor);
        connectButton = new JButton("Connect to server");
        connectButton.addActionListener(this);
        buttonPanel.add(connectButton);
        return buttonPanel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==connectButton){

        }
    }

    /*public void connectionHandling(String address){
        try {
            InetAddress addr = InetAddress.getByName(address);
            serverSocket = new Socket(addr, 3456);
            //System.out.println(InetAddress.getLocalHost());
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");
    }*/
}
