package it.polimi.ingsw.PSP38.client.GUIComponents;

import javax.swing.*;
import java.awt.*;

public class HourglassPanel {

    public JPanel createHourglassPanel() {
        ImageIcon hourglass;
        JPanel hourglassPanel = new JPanel();
        hourglassPanel.setBackground(SantoriniColor.white);
        hourglass = new ImageIcon(getClass().getResource("/hourglass.gif"));
        hourglass = new ImageIcon(hourglass.getImage().getScaledInstance(50, -1, Image.SCALE_DEFAULT));
        JLabel hourglassLabel = new JLabel(hourglass);
        hourglassPanel.add(hourglassLabel);
        hourglassPanel.setVisible(false);
        return hourglassPanel;
    }

}
