package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameModeGUI;
import it.polimi.ingsw.PSP38.client.ImageCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * This class contains the methods needed to take care of the game interface
 */
public class GamePanel{
    private GameModeGUI gameModeGUI;
    private JPanel mainGamePanel;
    private JPanel controlPanel;
    private JPanel divinityInfoPanel;
    private JLabel divinityImageLabel;
    private JPanel inputPanel;
    private JPanel messagePanel;
    private JLabel messageLabel;
    private JPanel boardPanel;
    private BoardComponent boardComponent;
    private int CellX;
    private int CellY;


    /**
     * This method is used to generate the main game panel
     * @return the main game panel
     */
    public JPanel createMainGamePanel(GameModeGUI gmg){
        gameModeGUI=gmg;
        mainGamePanel = new JPanel();
        mainGamePanel.setLayout(new BorderLayout());
        mainGamePanel.add(createMessagePanel(), BorderLayout.NORTH);
        mainGamePanel.add(createBoardPanel(), BorderLayout.WEST);
        mainGamePanel.add(createControlPanel(), BorderLayout.EAST);
        return mainGamePanel;
    }

    public JPanel createBoardPanel(){
        boardPanel = new JPanel();
        boardComponent = new BoardComponent();
        boardComponent.setPreferredSize(boardComponent.getPreferredSize());
        boardComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("X:"+e.getX());
                System.out.println("Y:"+e.getY());
                CellX = (e.getX()-2*BoardComponent.CELL_OFFSET_X)/(BoardComponent.CELL_WIDTH);
                CellY = (e.getY()-2*BoardComponent.CELL_OFFSET_Y)/(BoardComponent.CELL_HEIGHT);
                System.out.println(CellX);
                System.out.println(CellY);
                gameModeGUI.setColumnSelected(Integer.toString(CellX));
                gameModeGUI.setRowSelected(Integer.toString(CellY));
                gameModeGUI.setCoordinateReady(true);


            }
        });
        boardPanel.add(boardComponent);
        return boardPanel;
    }



    public JPanel createMessagePanel(){
        messagePanel = new JPanel(new FlowLayout());
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("font message", Font.BOLD, 30));
        messagePanel.add(messageLabel);
        return messagePanel;
    }

    public void setMessage(String message){
        messageLabel.setText(message);
        messageLabel.repaint();
    }

    public JPanel createControlPanel(){
        controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(createDivinityInfoPanel());
        return controlPanel;
    }

    public JPanel createDivinityInfoPanel(){
        divinityInfoPanel = new JPanel(new FlowLayout());
        return divinityInfoPanel;
    }

    public void paintDivinityChosen(){
        Image divinityImage =null;
        Image divinityImageScaled;
        File dir = null;
        try {
            dir = new File(Objects.requireNonNull(ImageCollection.class.getClassLoader()
                    .getResource("divinityImages/"+gameModeGUI.getMyDivinity().toLowerCase()+".png")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            divinityImage = ImageIO.read(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        divinityImageScaled = divinityImage.getScaledInstance(200,-1,Image.SCALE_SMOOTH);
        divinityImageLabel = new JLabel(new ImageIcon(divinityImageScaled));
        divinityInfoPanel.removeAll();
        divinityInfoPanel.add(divinityImageLabel);
        divinityImageLabel.repaint();
        divinityImageLabel.repaint();
        mainGamePanel.repaint();
    }

    public BoardComponent getBoardComponent() {
        return boardComponent;
    }

}
