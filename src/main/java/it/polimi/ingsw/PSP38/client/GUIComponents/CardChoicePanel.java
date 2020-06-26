package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameModeGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class CardChoicePanel implements ActionListener {
    private JPanel mainCardPanel;
    private JPanel titlePanel;
    private JPanel okButtonPanel;
    private JPanel cardButtonsPanel;
    private JLabel title;
    private GameModeGUI gameModeGUI;
    private Scanner divinitiesScanner;
    private ButtonGroup divintiyRadioButtonGroup = new ButtonGroup();
    private JButton okButton;

    public JPanel createMainCardPanel(GameModeGUI gmg){
        gameModeGUI=gmg;
        mainCardPanel = new JPanel(new BorderLayout());
        mainCardPanel.add(createTitlePanel(), BorderLayout.NORTH);
        //mainCardPanel.add(createCardButtonsPanel(), BorderLayout.CENTER);
        mainCardPanel.add(createOkButtonPanel(), BorderLayout.SOUTH);
        return mainCardPanel;
    }

    public JPanel createTitlePanel(){
        titlePanel = new JPanel();
        title = new JLabel("Please select one of the following cards");
        titlePanel.add(title);
        return titlePanel;
    }

    public JPanel createCardButtonsPanel(){
        cardButtonsPanel = new JPanel(new GridLayout(2,7));
        divinitiesScanner = new Scanner(gameModeGUI.getAvailableDivinities());
        while (divinitiesScanner.hasNextLine()){
            //salvare nome divinità e creare pulsante apposito
            createDivinityButton(divinitiesScanner.nextLine());
        }
        mainCardPanel.add(cardButtonsPanel, BorderLayout.CENTER);
        return cardButtonsPanel;
    }

    public JPanel createOkButtonPanel(){
        okButtonPanel = new JPanel(new FlowLayout());
        okButton = new JButton("OK");
        okButton.addActionListener(this);
        okButtonPanel.add(okButton);
        return okButtonPanel;
    }

    public void createDivinityButton(String divinityName){
        JPanel divinityButtonPanel = new JPanel(new BorderLayout());
        JPanel divinityCheckboxPanel = new JPanel(new FlowLayout());
        JRadioButton divinityCheckbox = new JRadioButton(divinityName);
        divinityCheckbox.setActionCommand(divinityName);
        divintiyRadioButtonGroup.add(divinityCheckbox);
        divinityCheckboxPanel.add(divinityCheckbox);
        divinityButtonPanel.add(divinityCheckboxPanel, BorderLayout.SOUTH);
        //inserire immagine
        cardButtonsPanel.add(divinityButtonPanel);
    }

    public JPanel getMainCardPanel() {
        return mainCardPanel;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==okButton){
            System.out.println(divintiyRadioButtonGroup.getSelection().getActionCommand());
            gameModeGUI.setStringRead(divintiyRadioButtonGroup.getSelection().getActionCommand());
        }
    }
}
