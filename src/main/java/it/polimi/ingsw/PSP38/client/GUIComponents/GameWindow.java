package it.polimi.ingsw.PSP38.client.GUIComponents;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
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
}
