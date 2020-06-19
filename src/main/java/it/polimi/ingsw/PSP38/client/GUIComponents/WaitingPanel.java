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

    public JPanel createWaitingPanel(){
        waitingPanel = new JPanel();
        waitingPanel.setLayout(new BorderLayout());
        waitingPanel.add(createWaitImagePanel(), BorderLayout.CENTER);
        return waitingPanel;
    }
    public JPanel createWaitImagePanel() {
        Image waitImage=null;
        imagePanel= new JPanel();
        //imagePanel.setBackground(panelColor);
        File dir = null;
        try {
            dir = new File(Objects.requireNonNull(ImageCollection.class.getClassLoader()
                    .getResource("santorini-hold-on.png")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            waitImage= ImageIO.read(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel logoLabel = new JLabel(new ImageIcon(waitImage));
        imagePanel.add(logoLabel);
        return imagePanel;
    }

}
