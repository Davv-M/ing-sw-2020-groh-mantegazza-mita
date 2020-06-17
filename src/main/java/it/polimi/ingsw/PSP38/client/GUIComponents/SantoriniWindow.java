package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.Client;
import it.polimi.ingsw.PSP38.client.GameModeGUI;
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

public class SantoriniWindow extends Observable /*implements ActionListener*/ {
    public static final int WIDTH = 550;
    public static final int HEIGHT = 650;
    private JFrame mainSetupFrame = new JFrame();
    private JPanel cardHolder;
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
    private Color bkgColor = new Color(25, 109,165);
    private Color bkgTextColor = new Color(62,159,225);
    private Color textColor  = Color.WHITE;
    private final static Client clientUpdate = new Client();
    private static GameModeGUI gameModeGUI;
    private JLabel errorMessage;
    private StartPanel startPanel;
    private CardChoicePanel cardChoicePanel;

    public JFrame createSantoriniWindow(GameModeGUI gmg){
        gameModeGUI=gmg;
        mainSetupFrame.setSize(WIDTH, HEIGHT);
        mainSetupFrame.setTitle("Santorini");
        mainSetupFrame.add(createCardHolder());
        mainSetupFrame.setVisible(true);
        return mainSetupFrame;
    }

    public JPanel createCardHolder(){
        cardHolder = new JPanel();
        cardHolder.setLayout(new CardLayout());
        startPanel=new StartPanel();
        cardHolder.add(startPanel.createSetupPanel(gameModeGUI), "setup");
        cardChoicePanel=new CardChoicePanel();
        cardHolder.add(cardChoicePanel.createMainCardPanel(gameModeGUI), "cardChoice");
        return cardHolder;
    }

    /*public JPanel createSetupPanel(){
        setupPanel = new JPanel();
        setupPanel.setLayout(new GridLayout(3,1));
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
        controlPanel.setLayout(new GridLayout(7,1));
        controlPanel.setBackground(panelColor);
        ipLabel = new JLabel("IP address");
        ipLabel.setForeground(textColor);
        controlPanel.add(ipLabel);
        ipAddress = new JTextField();
        ipAddress.setBackground(bkgTextColor);
        controlPanel.add(ipAddress);
        nicknameLabel = new JLabel("Nickname");
        nicknameLabel.setForeground(textColor);
        controlPanel.add(nicknameLabel);
        nickname = new JTextField();
        nickname.setBackground(bkgTextColor);
        controlPanel.add(nickname);
        ageLabel = new JLabel("Age");
        ageLabel.setForeground(textColor);
        controlPanel.add(ageLabel);
        age = new JTextField();
        age.setBackground(bkgTextColor);
        controlPanel.add(age);
        errorMessage = new JLabel( "");
        errorMessage.setForeground(textColor);
        errorMessage.setBackground(bkgColor);
        controlPanel.add(errorMessage);
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
            if (nickname.getText().isEmpty() || ipAddress.getText().isEmpty() || age.getText().isEmpty()){
                errorMessage.setText("tutti i campi sono obbligatori");
            }else {
                gameModeGUI.setIP(ipAddress.getText());
                gameModeGUI.setNickname(nickname.getText());
                gameModeGUI.setAge(age.getText());
            }
        }
    }*/

    /*@Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==connectButton){
            if (nickname.getText()==null||ipAddress.getText()==null||age.getText()==null){
                System.out.println("errore inserimento");
            }else {
                gameModeGUI.setIP(ipAddress.getText());
                gameModeGUI.setNickname(nickname.getText());
                gameModeGUI.setAge(age.getText());
                System.out.println("Pronto");
            }
        }
    }*/

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

    public JFrame getMainSetupFrame() {
        return mainSetupFrame;
    }

    public JPanel getCardHolder() {
        return cardHolder;
    }
}
