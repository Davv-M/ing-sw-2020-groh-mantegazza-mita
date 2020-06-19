package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameModeGUI;

import javax.swing.*;
import java.awt.*;

public class CardChoicePanel {
    private JPanel mainCardPanel;
    private JPanel cardDescriptionPanel;
    private JPanel cardButtonsPanel;
    private GameModeGUI gameModeGUI;

    public JPanel createMainCardPanel(GameModeGUI gmg){
        gameModeGUI=gmg;
        mainCardPanel = new JPanel(new BorderLayout());
        mainCardPanel.add(createDescriptionPanel(), BorderLayout.SOUTH);
        return mainCardPanel;
    }

    public JPanel createCardButtonsPanel(){
        cardButtonsPanel = new JPanel(new GridLayout(4,4));
        return cardButtonsPanel;
    }

    public JPanel createDescriptionPanel(){
        cardDescriptionPanel = new JPanel();
        return cardDescriptionPanel;
    }

    public void initializeButtonPanel(){

    }
}
