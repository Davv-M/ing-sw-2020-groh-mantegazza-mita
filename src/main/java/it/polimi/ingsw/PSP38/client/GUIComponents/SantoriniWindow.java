package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameModeGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

/**
 * Class that contains methods used to generate the graphic interface window of Santorini
 *
 * @author Davide Mantegazza (10568661)
 */
public class SantoriniWindow extends Observable {
    public static final int WIDTH = 550;
    public static final int HEIGHT = 650;
    private JFrame mainFrame = new JFrame();
    private JPanel cardHolder;
    private static GameModeGUI gameModeGUI;
    private StartPanel startPanel;
    private DivinityChoicePanel divinityChoicePanel;
    private WaitingPanel waitingPanel;
    private GamePanel gamePanel;

    /**
     * This method is used to generate the game window of Santorini
     *
     * @param gmg is the instance of class GameModeGUI that creates the window
     * @return the main frame for the game
     */
    public JFrame createSantoriniWindow(GameModeGUI gmg) {
        gameModeGUI = gmg;
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setTitle("Santorini");
        mainFrame.add(createCardHolder());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        return mainFrame;
    }

    /**
     * This method is used to create the JPanel that holds all of the panels of the window (the setup panel, the card selection panel and the game panel)
     *
     * @return the card holder panel
     */
    public JPanel createCardHolder() {
        cardHolder = new JPanel();
        cardHolder.setLayout(new CardLayout());
        startPanel = new StartPanel();
        cardHolder.add(startPanel.createStartPanel(gameModeGUI), "setup");
        waitingPanel = new WaitingPanel();
        cardHolder.add(waitingPanel.createWaitingPanel(), "waiting");
        divinityChoicePanel = new DivinityChoicePanel();
        cardHolder.add(divinityChoicePanel.createMainDivinityPanel(gameModeGUI), "cardChoice");
        gamePanel = new GamePanel();
        cardHolder.add(gamePanel.createMainGamePanel(gameModeGUI), "game");
        return cardHolder;
    }

    public JFrame getMainFrame() {
        return mainFrame;
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

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public WaitingPanel getWaitingPanel() {
        return waitingPanel;
    }
}
