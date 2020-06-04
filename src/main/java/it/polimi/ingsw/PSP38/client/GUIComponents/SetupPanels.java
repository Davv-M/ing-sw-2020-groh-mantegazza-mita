package it.polimi.ingsw.PSP38.client.GUIComponents;

import javax.swing.*;
import java.awt.*;

public class SetupPanels {
    private static JPanel ipPanel;
    private static JPanel namePanel;
    private static JPanel agePanel;
    private static JTextField ipAddress;
    private static JTextField nickname;
    private static JTextField age;
    private static JButton connectButton;
    private static JButton submitNicknameButton;
    private static JButton submitAgeButton;

    public static JPanel createIPPanel(){
        ipPanel=new JPanel();
        ipPanel.setLayout(new GridLayout(4,1));
        JLabel title = new JLabel("santorini");
        ipPanel.add(title);
        JLabel ipLabel = new JLabel("input ip adderss");
        ipPanel.add(ipLabel);
        ipAddress = new JTextField();
        ipPanel.add(ipAddress);
        connectButton = new JButton("Connect");
        ipPanel.add(connectButton);
        return ipPanel;
    }

    public static JPanel createNamePanel(){
        namePanel=new JPanel();
        namePanel.setLayout(new GridLayout(4,1));
        JLabel title = new JLabel("santorini");
        namePanel.add(title);
        JLabel ipLabel = new JLabel("nickname");
        namePanel.add(ipLabel);
        nickname = new JTextField();
        namePanel.add(nickname);
        submitNicknameButton = new JButton("Submit nickname");
        namePanel.add(submitNicknameButton);
        return namePanel;
    }

    public static JPanel createAgePanel(){
        agePanel=new JPanel();
        agePanel.setLayout(new GridLayout(4,1));
        JLabel title = new JLabel("santorini");
        agePanel.add(title);
        JLabel ipLabel = new JLabel("insert your age");
        agePanel.add(ipLabel);
        age = new JTextField();
        agePanel.add(age);
        submitAgeButton = new JButton("Submit age");
        agePanel.add(submitAgeButton);
        return agePanel;
    }
}
