package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameModeGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

/**
 * Class that contains methods used to generate the graphic interface window of Santorini
 * @author Davide Mantegazza (10568661)
 */
public class SantoriniWindow extends Observable {
    public static final int WIDTH = 550;
    public static final int HEIGHT = 650;
    public static final String waitForPlayersImage = "santorini-hold-on.png";
    public static final String waitForDivinitiesImage = "santorini-wait-for-divinities.png";
    public static final String waitForNumPlayersImage = "santorini-wait-for-num-players.png";
    private JFrame mainSetupFrame = new JFrame();
    private JPanel cardHolder;
    private static GameModeGUI gameModeGUI;
    private StartPanel startPanel;
    private DivinityChoicePanel divinityChoicePanel;
    private WaitingPanel waitForPlayersPanel;
    private WaitingPanel waitForNumPlayersPanel;
    private WaitingPanel waitForDivinitiesPanel;

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
        mainSetupFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        waitForPlayersPanel = new WaitingPanel(waitForPlayersImage);
        cardHolder.add(waitForPlayersPanel.createWaitingPanel(), "waitForPlayers");
        waitForNumPlayersPanel = new WaitingPanel(waitForNumPlayersImage);
        cardHolder.add(waitForNumPlayersPanel.createWaitingPanel(), "waitForNumPlayers");
        divinityChoicePanel =new DivinityChoicePanel();
        cardHolder.add(divinityChoicePanel.createMainCardPanel(gameModeGUI), "cardChoice");
        waitForDivinitiesPanel = new WaitingPanel(waitForDivinitiesImage);
        cardHolder.add(waitForDivinitiesPanel.createWaitingPanel(), "waitForDivinities");
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

    public DivinityChoicePanel getDivinityChoicePanel() {
        return divinityChoicePanel;
    }
}
