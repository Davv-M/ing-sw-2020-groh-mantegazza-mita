package it.polimi.ingsw.PSP38.client.GUIComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetNumOfPlayersComponent extends JComponent implements ActionListener {
    JButton twoPlayersButton;
    JButton threePlayersButton;
    int numOfPlayerChosen;

    public void createNumOfPlayers(){
        setLayout(new GridLayout(2,1));
        JLabel title = new JLabel("select num of players");
        add(title);
        add(createNumPlayerButton());
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
            numOfPlayerChosen=2;
        }
        else
        {
            numOfPlayerChosen=3;
        }
        System.out.println(numOfPlayerChosen);
        //setVisible(false);
    }
}
