package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameModeGUI;
import it.polimi.ingsw.PSP38.client.ImageCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * This class contains the methods needed to take care of the game interface
 */
public class GamePanel implements ActionListener {
    private GameModeGUI gameModeGUI;
    private JPanel mainGamePanel;
    private JPanel controlPanel;
    private JPanel divinityInfoPanel;
    private JPanel updateDivinityInfoPanel;
    private JLabel divinityImageLabel;
    private JPanel inputPanel;
    private JPanel messagePanel;
    private JLabel messageLabel;
    private BoardComponent boardComponent;
    private JButton submit;
    private JLabel cellLabel;
    private JTextField columnField;
    private JTextField rowField;


    /**
     * This method is used to generate the main game panel
     * @return the main game panel
     */
    public JPanel createMainGamePanel(GameModeGUI gmg){
        gameModeGUI=gmg;
        mainGamePanel = new JPanel();
        mainGamePanel.setLayout(new BorderLayout());
        boardComponent = new BoardComponent();

        //mainGamePanel.add(createMessagePanel(), BorderLayout.NORTH);
        mainGamePanel.add(boardComponent, BorderLayout.WEST);
        mainGamePanel.add(createControlPanel(), BorderLayout.CENTER);
        return mainGamePanel;
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
        controlPanel = new JPanel(new GridLayout(3,1));
        controlPanel.add(createMessagePanel());
        controlPanel.add(createDivinityInfoPanel());
        controlPanel.add(createInputPanel());
        return controlPanel;
    }

    public JPanel createInputPanel(){
        inputPanel = new JPanel(new GridLayout(6,1));
        cellLabel = new JLabel("");
        inputPanel.add(cellLabel);
        JLabel columnLabel = new JLabel("Column:");
        inputPanel.add(columnLabel);
        columnField = new JTextField();
        inputPanel.add(columnField);
        JLabel rowLabel = new JLabel("Row:");
        inputPanel.add(rowLabel);
        rowField = new JTextField();
        inputPanel.add(rowField);

        JPanel submitPanel = new JPanel(new FlowLayout());
        submit = new JButton("");
        submit.addActionListener(this);
        submitPanel.add(submit);
        inputPanel.add(submitPanel);
        return inputPanel;
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

    /*public JPanel createBuildControlPanel(){
        buildControlPanel = new JPanel(new GridLayout(3,1));
        JPanel buildTitle = new JPanel(new FlowLayout());
        JLabel buildLabel = new JLabel("Build");
        buildTitle.add(buildLabel);
        buildControlPanel.add(buildTitle);
        JPanel buildCoordsPanel = new JPanel(new FlowLayout());
        JTextField buildColumn = new JTextField("Column");
        buildCoordsPanel.add(buildColumn);
        JTextField buildRow = new JTextField("Row");
        buildCoordsPanel.add(buildRow);
        buildControlPanel.add(buildCoordsPanel);
        JPanel submitBuildingPanel = new JPanel(new FlowLayout());
        submitBuilding = new JButton("Move");
        submitBuildingPanel.add(submitBuilding);
        buildControlPanel.add(submitBuildingPanel);
        return buildControlPanel;
    }*/

    public BoardComponent getBoardComponent() {
        return boardComponent;
    }

    public JLabel getCellLabel() {
        return cellLabel;
    }

    public JButton getSubmit() {
        return submit;
    }

    public void setModifiablePanel(){
        inputPanel.setVisible(true);
    }

    public void setUnmodifiablePanel(){
        inputPanel.setVisible(false);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==submit){
            if (columnField.getText().isEmpty() || rowField.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"tutti i campi sono obbligatori","Login error ",JOptionPane.WARNING_MESSAGE);
            }else {
                gameModeGUI.setColumnSelected(columnField.getText());
                gameModeGUI.setRowSelected(rowField.getText());
                columnField.setText("");
                rowField.setText("");
                gameModeGUI.setCoordinateReady(true);
            }
        }
    }
}
