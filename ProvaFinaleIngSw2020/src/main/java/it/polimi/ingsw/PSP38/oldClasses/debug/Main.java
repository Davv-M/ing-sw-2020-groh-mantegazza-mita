package it.polimi.ingsw.PSP38.oldClasses.debug;

import it.polimi.ingsw.PSP38.client.BoardComponent;
import it.polimi.ingsw.PSP38.common.WorkerColor;
import it.polimi.ingsw.PSP38.server.controller.BoardEncoder;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {
    private static BoardComponent sc;
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        Board board = new Board();
        Cell cell = new Cell(0,1);
        board = board.withWorker(new Worker(WorkerColor.BLACK, cell));
        List<Byte> encodedBoard = BoardEncoder.bytesForBoard(board);
        createUI();
        sc.setEncodedBoard(encodedBoard);
    }

    public static void createUI(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sc = new BoardComponent();
        frame.add(sc);
        frame.getContentPane().setPreferredSize(sc.getPreferredSize());
        frame.pack();
        frame.setVisible(true);
        sc.requestFocusInWindow();
    }

}

