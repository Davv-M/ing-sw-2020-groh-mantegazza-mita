package it.polimi.ingsw.PSP38.client.GUIComponents;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LogoPanel {

    /**
     * This method is used to generate the panel containing the header image of the startup panel
     *
     * @return the panel with the title image
     */
    public JPanel createImagePanel() {
        Image santoriniLogo = null;
        Image santoriniLogoScaled;
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(SantoriniColor.white);
        try {
            santoriniLogo = ImageIO.read(getClass().getResource("/santorini-logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        santoriniLogoScaled = santoriniLogo.getScaledInstance(450, -1, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(santoriniLogoScaled));
        imagePanel.add(logoLabel);
        return imagePanel;
    }
}
