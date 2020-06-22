package it.polimi.ingsw.PSP38.client.GUIComponents;

import it.polimi.ingsw.PSP38.client.ImageCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class WaitingPanel {
    private JPanel waitingPanel;
    private JPanel imagePanel;
    private JPanel hourglassPanel;
    private JLabel waitLabel;
    private JLabel hourglassLabel;

    public JPanel createWaitingPanel(String imageURL){
        waitingPanel = new JPanel();
        waitingPanel.setLayout(new BorderLayout());
        waitingPanel.setBackground(Color.WHITE);
        waitingPanel.add(createWaitImagePanel(imageURL), BorderLayout.CENTER);
        waitingPanel.add(createHourglassPanel(), BorderLayout.SOUTH);
        return waitingPanel;
    }
    public JPanel createWaitImagePanel(String imageURL) {
        Image waitImage=null;
        Image waitImageScaled;
        imagePanel= new JPanel();
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
