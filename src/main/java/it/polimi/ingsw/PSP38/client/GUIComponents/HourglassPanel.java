package it.polimi.ingsw.PSP38.client.GUIComponents;

import javax.swing.*;
import java.awt.*;

public class HourglassPanel {

    private static JPanel hourglassPanel;
    private JLabel hourglassLabel;

    public JPanel createHourglassPanel(){
        ImageIcon hourglass;
        hourglassPanel= new JPanel();
        hourglassPanel.setBackground(SantoriniColor.white);
        hourglass = new ImageIcon(getClass().getResource("/hourglass.gif"));
        hourglass = new ImageIcon(hourglass.getImage().getScaledInstance(50,-1, Image.SCALE_DEFAULT));
        hourglassLabel = new JLabel(hourglass);
        hourglassPanel.add(hourglassLabel);
        hourglassPanel.setVisible(false);
        return hourglassPanel;
    }

}
