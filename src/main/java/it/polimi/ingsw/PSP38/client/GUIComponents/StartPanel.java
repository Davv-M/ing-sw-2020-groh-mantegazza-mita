package it.polimi.ingsw.PSP38.client.GUIComponents;

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

public class StartPanel implements ActionListener {
    private JPanel startPanel;
    private JPanel imagePanel;
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
    private GameModeGUI gameModeGUI;

    public JPanel createStartPanel(GameModeGUI gmg){
        gameModeGUI=gmg;
        startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(3,1));
        startPanel.setBackground(bkgColor);
        startPanel.add(createImagePanel());
        startPanel.add(createControlPanel());
        startPanel.add(createButtonPanel());
        return startPanel;
    }

    public JPanel createImagePanel() {
        Image santoriniLogo = null;
        Image santoriniLogoScaled;
        imagePanel = new JPanel();
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
        santoriniLogoScaled =santoriniLogo.getScaledInstance(450,-1,Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(santoriniLogoScaled));
        imagePanel.add(logoLabel);
        return imagePanel;
    }
    public JPanel createControlPanel(){
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(6,1));
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
                JOptionPane.showMessageDialog(null,"tutti i campi sono obbligatori","Login error ",JOptionPane.WARNING_MESSAGE);
            }else {
                gameModeGUI.setIP(ipAddress.getText());
                gameModeGUI.setNickname(nickname.getText());
                gameModeGUI.setAge(age.getText());
                /*CardLayout cl = (CardLayout)(gameModeGUI.getSantoriniWindow().getCardHolder().getLayout());
                cl.show(gameModeGUI.getSantoriniWindow().getCardHolder(), "cardChoice");*/
            }
        }
    }

    public JPanel getStartPanel() {
        return startPanel;
    }
}