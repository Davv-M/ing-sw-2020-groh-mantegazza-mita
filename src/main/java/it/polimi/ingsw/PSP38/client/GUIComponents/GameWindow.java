package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow implements ActionListener {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private JFrame mainSetupFrame = new JFrame();
    private JPanel panelHolder;

    public void createGameWindow(){
        mainSetupFrame.setSize(WIDTH, HEIGHT);
        mainSetupFrame.setTitle("Santorini");
        mainSetupFrame.add(createPanelHolder());
        mainSetupFrame.setVisible(true);
    }

    public JPanel createPanelHolder(){
        panelHolder=new JPanel();
        panelHolder.setLayout(new CardLayout());
        panelHolder.add(SetupPanels.createIPPanel(),"ipPanel");
        panelHolder.add(SetupPanels.createNamePanel(), "namePanel");
        panelHolder.add(SetupPanels.createAgePanel(),"agePanel");
        return panelHolder;
    }

    public JFrame getMainSetupFrame() {
        return mainSetupFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==SetupPanels.getConnectButton()){
            Client.connectionHandling(SetupPanels.getIpAddress().getText(),Client.getServerSocketPort());
        }else if(e.getSource()==SetupPanels.getSubmitNicknameButton()){
            Client.getGameMode().setStringRead(SetupPanels.getNickname().getText());
        }else if(e.getSource()==SetupPanels.getSubmitAgeButton()){
            Client.getGameMode().setStringRead(SetupPanels.getAge().getText());
        }
    }

    public JPanel getPanelHolder() {
        return panelHolder;
    }
}
