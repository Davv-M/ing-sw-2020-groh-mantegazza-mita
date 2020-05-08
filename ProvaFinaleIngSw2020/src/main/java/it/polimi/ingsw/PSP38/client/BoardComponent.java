package it.polimi.ingsw.PSP38.client;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class BoardComponent extends JComponent {
    private static final int PREFERRED_WIDTH = 615;
    private static final int PREFERRED_HEIGHT = 615;
    private static final int CELL_OFFSET_X = 7;
    private static final int CELL_OFFSET_Y = 7;

    private static final ImageCollection CELL_IMAGES = new ImageCollection("images");

    private List<Byte> encodedBoard = null;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g0) {
        if(encodedBoard != null){
            Graphics2D g = (Graphics2D) g0;
            drawEmptyBoard(g);
            Iterator<Byte> encodedCellIterator = encodedBoard.iterator();

            int rows = encodedCellIterator.next();
            int columns = encodedCellIterator.next();

            for(int row = 0; row < rows; ++row){
                for(int col = 0; col < columns; ++col){
                    byte encodedCell = encodedCellIterator.next();
                    if(encodedCell != 0){
                        Image cellImage = CELL_IMAGES.image(encodedCell);
                        int x = CELL_OFFSET_X + col * cellImage.getWidth(null);
                        int y = CELL_OFFSET_Y + row * cellImage.getHeight(null);

                        g.drawImage(cellImage, x, y, null);
                    }
                }
            }

        }
    }

    public void setEncodedBoard(List<Byte> encodedBoard){
        this.encodedBoard = encodedBoard;
        this.repaint();
    }

    public void drawEmptyBoard(Graphics2D g){
        g.drawImage(CELL_IMAGES.image((byte) 0), 0, 0, null);
    }



}
