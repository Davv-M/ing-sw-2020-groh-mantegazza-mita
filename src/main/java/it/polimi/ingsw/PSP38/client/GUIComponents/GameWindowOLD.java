package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindowOLD implements ActionListener {
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
        panelHolder.add(SetupPanelsOLD.createIPPanel(),"ipPanel");
        panelHolder.add(SetupPanelsOLD.createNamePanel(), "namePanel");
        panelHolder.add(SetupPanelsOLD.createAgePanel(),"agePanel");
        return panelHolder;
    }

    public JFrame getMainSetupFrame() {
        return mainSetupFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== SetupPanelsOLD.getConnectButton()){
            Client.connectionHandling(SetupPanelsOLD.getIpAddress().getText(),Client.getServerSocketPort());
        }else if(e.getSource()== SetupPanelsOLD.getSubmitNicknameButton()){
            Client.getGameMode().setStringRead(SetupPanelsOLD.getNickname().getText());
        }else if(e.getSource()== SetupPanelsOLD.getSubmitAgeButton()){
            Client.getGameMode().setStringRead(SetupPanelsOLD.getAge().getText());
        }
    }

    public JPanel getPanelHolder() {
        return panelHolder;
    }
}
