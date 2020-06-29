package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameModeGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class contains the methods needed to take care of the game interface
 */
public class GamePanel implements ActionListener {
    private GameModeGUI gameModeGUI;
    private JPanel mainGamePanel;
    private JPanel divinityInfoPanel;
    private JPanel controlPanel;
    /*private JPanel moveControlPanel;
    private JPanel buildControlPanel;*/
    private BoardComponent boardComponent;
    private JButton submit;
    //private JButton submitBuilding;
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
        mainGamePanel.add(boardComponent, BorderLayout.CENTER);
        mainGamePanel.add(createControlPanel(), BorderLayout.SOUTH);
        return mainGamePanel;
    }


    public JPanel createControlPanel(){
        controlPanel = new JPanel(new GridLayout(6,1));
        cellLabel = new JLabel("");
        controlPanel.add(cellLabel);

        JLabel columnLabel = new JLabel("Column:");
        controlPanel.add(columnLabel);
        columnField = new JTextField();
        controlPanel.add(columnField);
        JLabel rowLabel = new JLabel("Row:");
        controlPanel.add(rowLabel);
        rowField = new JTextField();
        controlPanel.add(rowField);

        JPanel submitPanel = new JPanel(new FlowLayout());
        submit = new JButton("");
        submit.addActionListener(this);
        submitPanel.add(submit);
        controlPanel.add(submitPanel);
        return controlPanel;
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
                System.out.println(columnField.getText());
                System.out.println(rowField.getText());
            }
        }
    }
}
