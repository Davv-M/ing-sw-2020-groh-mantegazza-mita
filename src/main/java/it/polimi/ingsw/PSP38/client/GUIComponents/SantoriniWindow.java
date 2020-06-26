package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.Client;
import it.polimi.ingsw.PSP38.client.GameModeGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

/**
 * Class that contains methods used to generate the graphic interface window of Santorini
 * @author Davide Mantegazza (10568661)
 */
public class SantoriniWindow extends Observable /*implements ActionListener*/ {
    public static final int WIDTH = 550;
    public static final int HEIGHT = 650;
    public static final String waitForPlayersImage = "santorini-hold-on.png";
    private JFrame mainSetupFrame = new JFrame();
    private JPanel cardHolder;
    private static GameModeGUI gameModeGUI;
    private StartPanel startPanel;
    private CardChoicePanel cardChoicePanel;
    private WaitingPanel waitingPanel;

    /**
     * This method is used to generate the game window of Santorini
     * @param gmg is the instance of class GameModeGUI that creates the window
     * @return the main frame for the game
     */
    public JFrame createSantoriniWindow(GameModeGUI gmg){
        gameModeGUI=gmg;
        mainSetupFrame.setSize(WIDTH, HEIGHT);
        mainSetupFrame.setTitle("Santorini");
        mainSetupFrame.add(createCardHolder());
        mainSetupFrame.setVisible(true);
        return mainSetupFrame;
    }

    /**
     * This method is used to create the JPanel that holds all of the panels of the window (the setup panel, the card selection panel and the game panel)
     * @return the card holder panel
     */
    public JPanel createCardHolder(){
        cardHolder = new JPanel();
        cardHolder.setLayout(new CardLayout());
        startPanel=new StartPanel();
        cardHolder.add(startPanel.createStartPanel(gameModeGUI), "setup");
        waitingPanel = new WaitingPanel();
        cardHolder.add(waitingPanel.createWaitingPanel(waitForPlayersImage), "waitForPlayers");
        cardChoicePanel=new CardChoicePanel();
        cardHolder.add(cardChoicePanel.createMainCardPanel(gameModeGUI), "cardChoice");
        return cardHolder;
    }

    public JFrame getMainSetupFrame() {
        return mainSetupFrame;
    }

    public JPanel getCardHolder() {
        return cardHolder;
    }

    public StartPanel getStartPanel() {
        return startPanel;
    }

    public CardChoicePanel getCardChoicePanel() {
        return cardChoicePanel;
    }
}
