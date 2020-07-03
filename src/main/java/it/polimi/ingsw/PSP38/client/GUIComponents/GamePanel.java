package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameModeGUI;
import it.polimi.ingsw.PSP38.common.Message;

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
 * @author Maximilien Groh (10683107)
 * @author Davide Mantegazza (10568661)
 * @author Matteo Mita (10487862)
 */
public class GamePanel {
    private GameModeGUI gameModeGUI;
    private JPanel mainGamePanel;
    private JPanel divinityInfoPanel;
    private JLabel messageLabel;
    private BoardComponent boardComponent;
    private JPanel hourglassPanel;
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
        mainGamePanel.setBackground(SantoriniColor.white);
        mainGamePanel.add(new LogoPanel().createImagePanel(SantoriniColor.white), BorderLayout.NORTH);
        mainGamePanel.add(createBoardPanel(), BorderLayout.WEST);
        mainGamePanel.add(createControlPanel(), BorderLayout.CENTER);
        return mainGamePanel;
    }


    public JPanel createBoardPanel() {
        JPanel boardPanel = new JPanel(new BorderLayout());
        boardPanel.add(createMessagePanel(), BorderLayout.NORTH);
        boardComponent = new BoardComponent();
        boardPanel.setBackground(SantoriniColor.white);
        boardComponent.setPreferredSize(boardComponent.getPreferredSize());
        boardComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CellX = (e.getX() - 2 * BoardComponent.CELL_OFFSET_X) / (BoardComponent.CELL_WIDTH);
                CellY = (e.getY() - 2 * BoardComponent.CELL_OFFSET_Y) / (BoardComponent.CELL_HEIGHT);
                if (gameModeGUI.isMyTurn()) {
                    gameModeGUI.setColumnSelected(Integer.toString(CellX));
                    gameModeGUI.setRowSelected(Integer.toString(CellY));
                    gameModeGUI.setCoordinateReady(true);
                } else {
                    gameModeGUI.decodeMessage(Message.WAIT);
                }


            }
        });
        boardPanel.add(boardComponent, BorderLayout.CENTER);
        return boardPanel;
    }


    public JPanel createMessagePanel() {
        hourglassPanel = new HourglassPanel().createHourglassPanel();
        JPanel messagePanel = new JPanel(new FlowLayout());
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("font message", Font.BOLD, 30));
        messageLabel.setForeground(SantoriniColor.blue);
        messagePanel.setBackground(SantoriniColor.white);
        messagePanel.add(messageLabel);
        messagePanel.add(hourglassPanel);
        return messagePanel;
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
        messageLabel.repaint();
    }


    public void setVisibleHourglass(boolean visible) {
        hourglassPanel.setVisible(visible);
        hourglassPanel.repaint();
    }


    public JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(SantoriniColor.white);
        controlPanel.add(createDivinityInfoPanel(), BorderLayout.CENTER);
        return controlPanel;
    }

    public JPanel createDivinityInfoPanel() {
        divinityInfoPanel = new JPanel(new GridLayout(1, gameModeGUI.getNumOfPlayers()));
        divinityInfoPanel.setBackground(SantoriniColor.white);
        return divinityInfoPanel;
    }

    public void paintPlayersDivinities(Map<String, String> playersDivinities) {
        divinityInfoPanel.removeAll();
        Iterator<String> playerIterator = playersDivinities.keySet().iterator();
        for (int i = 0; i < gameModeGUI.getNumOfPlayers(); i++) {
            JPanel divinityNicknameInfoPanel = new JPanel(new BorderLayout());
            divinityNicknameInfoPanel.setBackground(SantoriniColor.white);
            Image divinityImage = null;
            Image divinityImageScaled;
            String nickname = playerIterator.next();
            try {
                divinityImage = ImageIO.read(getClass().getResource("/divinities/" + playersDivinities.get(nickname)
                        .toLowerCase() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            divinityImageScaled = divinityImage.getScaledInstance(200, -1, Image.SCALE_SMOOTH);
            JLabel divinityImageLabel = new JLabel(new ImageIcon(divinityImageScaled));
            JLabel divinityNicknameLabel = new JLabel(nickname.toUpperCase());
            divinityNicknameLabel.setFont(new Font("font message", Font.BOLD, 30));
            divinityNicknameLabel.setForeground(SantoriniColor.blue);
            divinityNicknameInfoPanel.add(divinityNicknameLabel, BorderLayout.NORTH);
            divinityNicknameInfoPanel.add(divinityImageLabel, BorderLayout.CENTER);
            divinityInfoPanel.add(divinityNicknameInfoPanel);
            divinityNicknameInfoPanel.repaint();
            divinityImageLabel.repaint();
            divinityImageLabel.repaint();

        }
        divinityInfoPanel.repaint();
        mainGamePanel.repaint();
    }

    public BoardComponent getBoardComponent() {
        return boardComponent;
    }

}
