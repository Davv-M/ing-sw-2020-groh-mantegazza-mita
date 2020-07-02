package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameModeGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * This class contains the methods needed to take care of the game interface
 */
public class GamePanel {
    private GameModeGUI gameModeGUI;
    private JPanel mainGamePanel;
    private JPanel divinityInfoPanel;
    private JLabel messageLabel;
    private BoardComponent boardComponent;
    private int CellX;
    private int CellY;


    /**
     * This method is used to generate the main game panel
     *
     * @return the main game panel
     */
    public JPanel createMainGamePanel(GameModeGUI gmg) {
        gameModeGUI = gmg;
        mainGamePanel = new JPanel();
        mainGamePanel.setLayout(new BorderLayout());
        mainGamePanel.setBackground(SantoriniColor.bkgColor);
        mainGamePanel.add(createMessagePanel(), BorderLayout.NORTH);
        mainGamePanel.add(createBoardPanel(), BorderLayout.WEST);
        mainGamePanel.add(createControlPanel(), BorderLayout.EAST);
        return mainGamePanel;
    }

    public JPanel createBoardPanel() {
        JPanel boardPanel = new JPanel();
        boardComponent = new BoardComponent();
        boardPanel.setBackground(SantoriniColor.bkgColor);
        boardComponent.setPreferredSize(boardComponent.getPreferredSize());
        boardComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("X:" + e.getX());
                System.out.println("Y:" + e.getY());
                CellX = (e.getX() - 2 * BoardComponent.CELL_OFFSET_X) / (BoardComponent.CELL_WIDTH);
                CellY = (e.getY() - 2 * BoardComponent.CELL_OFFSET_Y) / (BoardComponent.CELL_HEIGHT);
                System.out.println(CellX);
                System.out.println(CellY);
                GameModeGUI.setColumnSelected(Integer.toString(CellX));
                GameModeGUI.setRowSelected(Integer.toString(CellY));
                gameModeGUI.setCoordinateReady(true);
            }
        });
        boardPanel.add(boardComponent);
        return boardPanel;
    }


    public JPanel createMessagePanel() {
        JPanel messagePanel = new JPanel(new FlowLayout());
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("font message", Font.BOLD, 30));
        messageLabel.setForeground(SantoriniColor.messageColor);
        messagePanel.setBackground(SantoriniColor.bkgColor);
        messagePanel.add(messageLabel);
        return messagePanel;
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
        messageLabel.repaint();
    }

    public JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(createDivinityInfoPanel());
        return controlPanel;
    }

    public JPanel createDivinityInfoPanel() {
        divinityInfoPanel = new JPanel(new GridLayout(1, gameModeGUI.getNumOfPlayers()));
        divinityInfoPanel.setBackground(SantoriniColor.bkgColor);
        return divinityInfoPanel;
    }

    public void paintPlayersDivinities(Map<String, String> playersDivinities) {
        divinityInfoPanel.removeAll();
        Iterator<String> playerIterator = playersDivinities.keySet().iterator();
        for (int i = 0; i < gameModeGUI.getNumOfPlayers(); i++) {
            Image divinityImage = null;
            Image divinityImageScaled;
            try {
                divinityImage = ImageIO.read(getClass().getResource("/divinityImages/" + playersDivinities.get(playerIterator.next())
                        .toLowerCase() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            divinityImageScaled = divinityImage.getScaledInstance(200, -1, Image.SCALE_SMOOTH);
            JLabel divinityImageLabel = new JLabel(new ImageIcon(divinityImageScaled));
            divinityInfoPanel.add(divinityImageLabel);
            divinityImageLabel.repaint();
            divinityImageLabel.repaint();
            mainGamePanel.repaint();
        }
    }

    public BoardComponent getBoardComponent() {
        return boardComponent;
    }

}
