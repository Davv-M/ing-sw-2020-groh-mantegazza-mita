package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.GameModeGUI;
import it.polimi.ingsw.PSP38.common.Message;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class contains the methods needed to create the panel used to choose the divinity cards that will be used during the game
 *
 * @author Davide Mantegazza (10568661)
 */
public class DivinityChoicePanel implements ActionListener {
    private JPanel mainDivinityPanel;
    private JPanel cardButtonsPanel;
    private JPanel hourglassPanel;
    private JLabel title;
    private GameModeGUI gameModeGUI;
    private final ButtonGroup divinityRadioButtonGroup = new ButtonGroup();
    private JButton okButton;

    /**
     * This method is used to generate the main divinity panel
     *
     * @param gmg is the instance of GameModeGUI
     * @return the main divinity panel
     */
    public JPanel createMainDivinityPanel(GameModeGUI gmg) {
        gameModeGUI = gmg;
        mainDivinityPanel = new JPanel(new BorderLayout());
        mainDivinityPanel.setBackground(SantoriniColor.white);
        mainDivinityPanel.add(createMessagePanel(), BorderLayout.NORTH);
        mainDivinityPanel.add(createOkButtonPanel(), BorderLayout.SOUTH);
        return mainDivinityPanel;
    }

    /*  */

    /**
     * This method is used to create the title panel
     *
     * @return the title panel
     *//*
    public JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        title = new JLabel("");
        title.setFont(new Font("font message", Font.BOLD, 30));
        titlePanel.setBackground(SantoriniColor.white);
        title.setForeground(SantoriniColor.blue);
        titlePanel.add(title);
        titlePanel.add(new HourglassPanel().createHourglassPanel());
        return titlePanel;
    }*/
    public JPanel createMessagePanel() {
        hourglassPanel = new HourglassPanel().createHourglassPanel();
        JPanel messagePanel = new JPanel(new FlowLayout());
        title = new JLabel("");
        title.setFont(new Font("font message", Font.BOLD, 30));
        title.setForeground(SantoriniColor.blue);
        messagePanel.setBackground(SantoriniColor.white);
        messagePanel.add(title);
        messagePanel.add(hourglassPanel);
        return messagePanel;
    }

    public void setMessage(String message) {
        title.setText(message);
        title.repaint();
    }

    public void setVisibleHourglass(boolean visible) {
        hourglassPanel.setVisible(visible);
        hourglassPanel.repaint();
    }

    /**
     * This method is used to generate the panel that holds all of the divinity buttons
     *
     * @return the panel that contains the divinity buttons
     */
    public JPanel createDivinitiesButtonsPanel() {
        cardButtonsPanel = new JPanel(new GridLayout(2, 7));
        cardButtonsPanel.setBackground(SantoriniColor.white);
        Scanner divinitiesScanner = new Scanner(gameModeGUI.getAvailableDivinities());
        while (divinitiesScanner.hasNextLine()) {
            createDivinityButton(divinitiesScanner.nextLine());
        }
        mainDivinityPanel.add(cardButtonsPanel, BorderLayout.CENTER);
        return cardButtonsPanel;
    }

    /**
     * This method is used to generate a single divinity button (composed of an image and a radio button)
     *
     * @param divinityName is the name of the divinity whose button is going to be generate through this method
     */
    public void createDivinityButton(String divinityName) {
        JPanel divinityButtonPanel = new JPanel(new BorderLayout());
        divinityButtonPanel.setBackground(SantoriniColor.white);
        JPanel divinityCheckboxPanel = new JPanel(new FlowLayout());
        divinityCheckboxPanel.setBackground(SantoriniColor.white);
        JRadioButton divinityRadioButton = new JRadioButton(divinityName);
        divinityRadioButton.setBackground(SantoriniColor.white);
        divinityRadioButton.setActionCommand(divinityName);
        divinityRadioButtonGroup.add(divinityRadioButton);
        divinityCheckboxPanel.add(divinityRadioButton);
        divinityButtonPanel.add(divinityCheckboxPanel, BorderLayout.SOUTH);
        divinityButtonPanel.add(createDivinityImagePanel(divinityName), BorderLayout.CENTER);
        cardButtonsPanel.add(divinityButtonPanel);
    }

    /**
     * This method is used to generate the image part of the single divinity button
     *
     * @param divinityName is the name of the divinity whose button is going to be generate through this method
     */
    public JPanel createDivinityImagePanel(String divinityName) {
        Image divinityImage = null;
        Image divinityImageScaled;
        JPanel divinityImagePanel = new JPanel();
        divinityImagePanel.setBackground(SantoriniColor.white);
        try {
            divinityImage = ImageIO.read(getClass().getResourceAsStream("/divinities/" + divinityName.toLowerCase() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        divinityImageScaled = divinityImage.getScaledInstance(100, -1, Image.SCALE_SMOOTH);
        JLabel divinityImageLabel = new JLabel(new ImageIcon(divinityImageScaled));
        divinityImagePanel.add(divinityImageLabel);
        return divinityImagePanel;
    }

    /**
     * This mehod is used to generate the panel that contains the submit button
     *
     * @return the panel containing the submit button
     */
    public JPanel createOkButtonPanel() {
        JPanel okButtonPanel = new JPanel(new FlowLayout());
        okButtonPanel.setBackground(SantoriniColor.white);
        okButton = new JButton("OK");
        okButton.addActionListener(this);
        okButtonPanel.add(okButton);
        return okButtonPanel;
    }

    public JPanel getMainDivinityPanel() {
        return mainDivinityPanel;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            if (gameModeGUI.isMyTurn()) {
                System.out.println(divinityRadioButtonGroup.getSelection().getActionCommand());
                gameModeGUI.setStringRead(divinityRadioButtonGroup.getSelection().getActionCommand());
            } else {
                gameModeGUI.decodeMessage(Message.WAIT);
            }

        }
    }
}
