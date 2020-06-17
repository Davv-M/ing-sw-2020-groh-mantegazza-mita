package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameMode;
import it.polimi.ingsw.PSP38.client.GameModeGUI;

import javax.swing.*;
import java.awt.*;

public class CardChoicePanel {
    private static JPanel mainCardPanel;
    private static JPanel cardDescriptionPanel;
    private GameModeGUI gameModeGUI;

    public JPanel createMainCardPanel(GameModeGUI gmg){
        gameModeGUI=gmg;
        mainCardPanel = new JPanel(new BorderLayout());
        mainCardPanel.add(createDescriptionPanel(), BorderLayout.SOUTH);
        return mainCardPanel;
    }

    public JPanel createDescriptionPanel(){
        cardDescriptionPanel = new JPanel();
        return cardDescriptionPanel;
    }
}
