package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameModeGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * This class contains all the methods needed to create the panel with the preliminary settings of the game
 *
 * @author Davide Mantegazza (10568661)
 */
public class StartPanel implements ActionListener {
    private JPanel startPanel;
    private JTextField ipAddress;
    private JTextField nickname;
    private JTextField age;
    private JButton connectButton;
    private GameModeGUI gameModeGUI;

    /**
     * This method is used to create the main panel for the initial settings of the game
     *
     * @param gmg is the current instance of GameModeGUI
     * @return the main panel
     */
    public JPanel createStartPanel(GameModeGUI gmg) {
        gameModeGUI = gmg;
        startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(3, 1));
        startPanel.setBackground(SantoriniColor.blue);
        startPanel.add(new LogoPanel().createImagePanel(SantoriniColor.blue));
        startPanel.add(createControlPanel());
        startPanel.add(createButtonPanel());
        return startPanel;
    }


    /**
     * This method is used to generate the panel that contains the text fields used to input the server's IP address,
     * the player's nickname and the player's age
     *
     * @return the panel containing the text fields previously mentioned
     */
    public JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(6, 1));
        controlPanel.setBackground(SantoriniColor.blue);
        JLabel ipLabel = new JLabel("IP address");
        ipLabel.setForeground(SantoriniColor.white);
        controlPanel.add(ipLabel);
        ipAddress = new JTextField();
        ipAddress.setBackground(SantoriniColor.lightBlue);
        controlPanel.add(ipAddress);
        JLabel nicknameLabel = new JLabel("Nickname");
        nicknameLabel.setForeground(SantoriniColor.white);
        controlPanel.add(nicknameLabel);
        nickname = new JTextField();
        nickname.setBackground(SantoriniColor.lightBlue);
        controlPanel.add(nickname);
        JLabel ageLabel = new JLabel("Age");
        ageLabel.setForeground(SantoriniColor.white);
        controlPanel.add(ageLabel);
        age = new JTextField();
        age.setBackground(SantoriniColor.lightBlue);
        controlPanel.add(age);
        return controlPanel;
    }

    /**
     * This method is used to create the panel that contains the button used to submit the inputted data and connect to the server
     *
     * @return the panel with the button used to submit the data and connect to the server
     */
    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(SantoriniColor.blue);
        connectButton = new JButton("Connect to server");
        connectButton.addActionListener(this);
        buttonPanel.add(connectButton);
        return buttonPanel;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connectButton) {
            if (nickname.getText().isEmpty() || ipAddress.getText().isEmpty() || age.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill all text fields", "Login error ", JOptionPane.WARNING_MESSAGE);
            } else {
                gameModeGUI.setIP(ipAddress.getText());
                gameModeGUI.setNickname(nickname.getText());
                gameModeGUI.setAge(age.getText());
            }
        }
    }

    public JPanel getStartPanel() {
        return startPanel;
    }
}
