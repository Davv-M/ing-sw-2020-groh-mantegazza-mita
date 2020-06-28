package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.ImageCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * This class contains the methods needed to generate a panel which signals the user that the client is waiting for
 * some feedback from the server or another client
 * @author Davide Mantegazza (10568661), Matteo Mita
 */
public class WaitingPanel {
    private JPanel waitingPanel;
    private JPanel imagePanel;
    private JPanel hourglassPanel;
    private JLabel waitLabel;
    private JLabel hourglassLabel;
    private String imageURL;
    private Color panelColor = new Color(0,0,0,0);

    /**
     * Constructor of <code>WaitingPanel</code> class
     * @param imageURL is the path with the name of the image that will be shown on the panel
     */
    public WaitingPanel(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * This method generates the waiting panel with the desired image
     * @return the waiting panel
     */
    public JPanel createWaitingPanel(){
        waitingPanel = new JPanel();
        waitingPanel.setLayout(new BorderLayout());
        waitingPanel.setBackground(Color.WHITE);
        waitingPanel.add(createWaitImagePanel(), BorderLayout.CENTER);
        waitingPanel.add(createHourglassPanel(), BorderLayout.SOUTH);
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
        imagePanel.setBackground(panelColor);
        File dir = null;
        try {
            dir = new File(Objects.requireNonNull(ImageCollection.class.getClassLoader()
                    .getResource(imageURL)).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            waitImage= ImageIO.read(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        waitImageScaled =waitImage.getScaledInstance(500,-1,Image.SCALE_SMOOTH);
        waitLabel = new JLabel(new ImageIcon(waitImageScaled));
        imagePanel.add(waitLabel);
        return imagePanel;
    }

    public JPanel createHourglassPanel(){
        Image hourglass = null;
        Image hourglassScaled;
        hourglassPanel= new JPanel();
        hourglassPanel.setBackground(panelColor);
        File dir = null;
        try {
            dir = new File(Objects.requireNonNull(ImageCollection.class.getClassLoader()
                    .getResource("hourglass.gif")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            hourglass = ImageIO.read(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hourglassScaled=hourglass.getScaledInstance(100,-1,Image.SCALE_DEFAULT);
        hourglassLabel = new JLabel(new ImageIcon(hourglassScaled));
        imagePanel.add(hourglassLabel);
        return hourglassPanel;
    }

}
