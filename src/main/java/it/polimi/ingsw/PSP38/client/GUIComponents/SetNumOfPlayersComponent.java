package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameModeGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetNumOfPlayersComponent extends JComponent implements ActionListener {

    JButton twoPlayersButton;
    JButton threePlayersButton;
    GameModeGUI gmg;


    public SetNumOfPlayersComponent(GameModeGUI gmg){
        this.gmg=gmg;
        createSetNumPlayerWindow();

    }

    public void createSetNumPlayerWindow(){
        JLabel message = new JLabel("inserisci il numero di giocatori");
        gmg.getFrame().add(message);
        gmg.getFrame().add(createNumPlayerButton());
        gmg.getFrame().setVisible(true);
    }

    public JPanel createNumPlayerButton(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        twoPlayersButton = new JButton("2");
        twoPlayersButton.addActionListener(this);
        threePlayersButton = new JButton("3");
        threePlayersButton.addActionListener(this);
        buttonPanel.add(twoPlayersButton);
        buttonPanel.add(threePlayersButton);
        return buttonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==twoPlayersButton){
            gmg.setStringRead("2");
        }
        if(e.getSource()==threePlayersButton){
            gmg.setStringRead("3");
        }

    }
}
