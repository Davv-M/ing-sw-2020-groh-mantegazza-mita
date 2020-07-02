package it.polimi.ingsw.PSP38.client.GUIComponents;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class contains the methods needed to generate a panel which signals the user that the client is waiting for
 * some feedback from the server or another client
 *
 * @author Davide Mantegazza (10568661), Matteo Mita (10487862)
 */
public class WaitingPanel {
    private JPanel waitingPanel;
    private JPanel messagePanel;
    private JLabel messageLabel;
    private JPanel imagePanel;
    private JLabel waitLabel;
    private JPanel southPanel;
    private JPanel hourglassPanel;

    /**
     * Constructor of <code>WaitingPanel</code> class
     * @param imageURL is the path with the name of the image that will be shown on the panel
     */

    /**
     * This method generates the waiting panel with the desired image
     *
     * @return the waiting panel
     */
    public JPanel createWaitingPanel() {
        waitingPanel = new JPanel();
        waitingPanel.setLayout(new BorderLayout());
        waitingPanel.setBackground(Color.WHITE);
        waitingPanel.add(new LogoPanel().createImagePanel(SantoriniColor.white), BorderLayout.NORTH);
        waitingPanel.add(createWaitImagePanel(), BorderLayout.CENTER);
        waitingPanel.add(createSouthPanel(), BorderLayout.SOUTH);
        return waitingPanel;
    }

    /**
     * This method is used to create the panel containing the specified image
     *
     * @return the panel with the image specified with <code>imageURL</code>
     */
    public JPanel createWaitImagePanel() {
        Image waitImage = null;
        Image waitImageScaled;
        imagePanel = new JPanel();
        imagePanel.setBackground(SantoriniColor.white);
        try {
            waitImage = ImageIO.read(getClass().getResource("/santorini-wait.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        waitImageScaled = waitImage.getScaledInstance(600, -1, Image.SCALE_SMOOTH);
        waitLabel = new JLabel(new ImageIcon(waitImageScaled));
        imagePanel.add(waitLabel);
        return imagePanel;
    }

    public JPanel createSouthPanel() {
        southPanel = new JPanel(new BorderLayout());
        hourglassPanel = new HourglassPanel().createHourglassPanel();
        southPanel.add(hourglassPanel, BorderLayout.NORTH);
        southPanel.add(createMessagePanel(), BorderLayout.CENTER);
        return southPanel;
    }


    public JPanel createMessagePanel() {
        messagePanel = new JPanel(new FlowLayout());
        messagePanel.setBackground(Color.WHITE);
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("font message", Font.BOLD, 30));
        messageLabel.setForeground(SantoriniColor.blue);
        messagePanel.add(messageLabel);
        return messagePanel;
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
        messageLabel.repaint();
    }


    public void setVisibleHourglass(boolean visible) {
        hourglassPanel.setVisible(visible);
        hourglassPanel.repaint();
    }

}
