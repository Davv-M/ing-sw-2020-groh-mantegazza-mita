package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.Protocol;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class Client {
    public final static int SERVER_SOCKET_PORT = 3457;
    private static BoardComponent sc;

    private static ObjectInputStream input;
    private static ObjectOutputStream output;

    public static void main(String[] args) {
        Socket serverSocket;
        try {
            serverSocket = new Socket(InetAddress.getLocalHost(), SERVER_SOCKET_PORT);
            System.out.println(InetAddress.getLocalHost());
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");
        try {
            input = new ObjectInputStream(serverSocket.getInputStream());
            output = new ObjectOutputStream(serverSocket.getOutputStream());
            createUI();
            while (true) {
                final Scanner scanner = new Scanner(System.in);
                Protocol p = (Protocol) input.readObject();
                switch (p) {
                    case NOTIFY_MESSAGE:
                        notifyMessage();
                        break;
                    case ASK_INT:
                        askInt(scanner);
                        break;
                    case ASK_STRING:
                        askString(scanner);
                        break;
                    case DISPLAY_BOARD:
                        displayBoard();
                        break;
                    default:
                        System.out.println("protocol error");
                        break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private static void notifyMessage() throws IOException {
        try {
            String message = (String) input.readObject();
            System.out.println(message);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void askString(Scanner scanner) throws IOException {
        output.writeObject(scanner.nextLine());
    }

    private static void askInt(Scanner scanner) throws IOException {
        int number;
        do {
            try {
                number = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                String error = scanner.nextLine();
                System.out.println(error + " is not an Integer");
                System.out.println("Please insert an integer (between 2 and 3)");
            }
        } while (true);
        output.writeInt(number);
        output.flush();
    }

    private static void displayBoard() throws IOException {
        byte rows = input.readByte();
        byte columns = input.readByte();
        List<Byte> encodedBoard = new LinkedList<>();
        encodedBoard.add(rows);
        encodedBoard.add(columns);
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < columns; ++col) {
                byte b = input.readByte();
                encodedBoard.add(b);
            }
        }
        sc.setEncodedBoard(encodedBoard);
    }

    public static void createUI() {
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
