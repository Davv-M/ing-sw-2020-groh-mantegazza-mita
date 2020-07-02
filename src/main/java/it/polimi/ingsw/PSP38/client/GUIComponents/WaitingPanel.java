package it.polimi.ingsw.PSP38.client.GUIComponents;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class contains the methods needed to generate a panel which signals the user that the client is waiting for
 * some feedback from the server or another client
 * @author Davide Mantegazza (10568661), Matteo Mita (10487862)
 */
public class WaitingPanel {
    private JPanel waitingPanel;
    private JPanel messagePanel;
    private JLabel messageLabel;
    private JPanel imagePanel;
    private JPanel hourglassPanel;
    private JLabel waitLabel;
    private JLabel hourglassLabel;

    /**
     * Constructor of <code>WaitingPanel</code> class
     * @param imageURL is the path with the name of the image that will be shown on the panel
     */

    /**
     * This method generates the waiting panel with the desired image
     * @return the waiting panel
     */
    public JPanel createWaitingPanel(){
        waitingPanel = new JPanel();
        waitingPanel.setLayout(new BorderLayout());
        waitingPanel.setBackground(Color.WHITE);
        waitingPanel.add(new LogoPanel().createImagePanel(), BorderLayout.NORTH);
        waitingPanel.add(createWaitImagePanel(), BorderLayout.CENTER);
        //waitingPanel.add(createHourglassPanel(), BorderLayout.SOUTH);
        waitingPanel.add(createMessagePanel(),BorderLayout.SOUTH);
        return waitingPanel;
    }

    /**
     * This method is used to create the panel containing the specified image
     * @return the panel with the image specified with <code>imageURL</code>
     */
    public JPanel createWaitImagePanel() {
        Image waitImage=null;
        Image waitImageScaled;
        imagePanel= new JPanel();
        imagePanel.setBackground(SantoriniColor.white);
        try {
            waitImage= ImageIO.read(getClass().getResource("/santorini-wait.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        waitImageScaled =waitImage.getScaledInstance(600,-1,Image.SCALE_SMOOTH);
        waitLabel = new JLabel(new ImageIcon(waitImageScaled));
        imagePanel.add(waitLabel);
        return imagePanel;
    }

    public JPanel createHourglassPanel(){
        Image hourglass = null;
        Image hourglassScaled;
        hourglassPanel= new JPanel();
        hourglassPanel.setBackground(SantoriniColor.white);
        try {
            hourglass = ImageIO.read(getClass().getResource("/hourglass.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hourglassScaled=hourglass.getScaledInstance(50,-1,Image.SCALE_DEFAULT);
        hourglassLabel = new JLabel(new ImageIcon(hourglassScaled));
        imagePanel.add(hourglassLabel);
        return hourglassPanel;


    }

    public JPanel createMessagePanel(){
        messagePanel = new JPanel(new FlowLayout());
        messagePanel.setBackground(Color.WHITE);
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("font message", Font.BOLD, 30));
        messageLabel.setForeground(SantoriniColor.blue);
        messagePanel.add(messageLabel);
        return messagePanel;
    }

    public void setMessage(String message){
        messageLabel.setText(message);
        messageLabel.repaint();
    }

}
