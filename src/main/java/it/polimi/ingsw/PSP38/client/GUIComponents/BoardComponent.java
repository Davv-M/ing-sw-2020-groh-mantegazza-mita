package it.polimi.ingsw.PSP38.client.GUIComponents;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Component that is used to display the board when
 * playing the game in "GUI" mode.
 *
 * @author Maximilien Groh (10683107)
 */
public class BoardComponent extends JComponent{
    private static final int PREFERRED_WIDTH = 615;
    private static final int PREFERRED_HEIGHT = 615;
    public static final int CELL_OFFSET_X = 8;
    public static final int CELL_OFFSET_Y = 8;
    private final Map<Byte, ImageIcon> boardImages = new HashMap<>();
    private List<Byte> encodedBoard = null;
    public static final int CELL_WIDTH = 120;
    public static final int CELL_HEIGHT = 120;


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        drawEmptyBoard(g);
        if (encodedBoard != null) {
            Iterator<Byte> encodedCellIterator = encodedBoard.iterator();
            int rows = encodedCellIterator.next();
            int columns = encodedCellIterator.next();
            for (int row = 0; row < rows; ++row) {
                for (int col = 0; col < columns; ++col) {
                    byte encodedCell = encodedCellIterator.next();
                    if (encodedCell != 0) {
                        if(!boardImages.containsKey(encodedCell)){
                            boardImages.put(encodedCell, new ImageIcon(getClass().getResource("/boardImages/" + encodedCell + ".png")));
                        }
                        Image cellImage = boardImages.get(encodedCell).getImage();
                        int x = CELL_OFFSET_X + col * cellImage.getWidth(null);
                        int y = CELL_OFFSET_Y + row * cellImage.getHeight(null);

                        g.drawImage(cellImage, x, y, null);
                    }
                }
            }

        }
    }

    /**
     * Updates the board and repaints the component
     *
     * @param encodedBoard the encoded board to display
     */
    public void setEncodedBoard(List<Byte> encodedBoard) {
        this.encodedBoard = encodedBoard;
        this.repaint();
    }

    /**
     * Draws the empty board
     *
     * @param g the graphics
     */
    public void drawEmptyBoard(Graphics2D g) {
        ImageIcon emptyBoardImage = new ImageIcon(getClass().getResource("/boardImages/0.png"));
        g.drawImage(emptyBoardImage.getImage(), 0, 0, null);
    }

}
