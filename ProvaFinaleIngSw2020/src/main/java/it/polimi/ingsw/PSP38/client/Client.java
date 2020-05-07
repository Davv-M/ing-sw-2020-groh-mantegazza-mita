package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class Client {
    public final static int SERVER_SOCKET_PORT = 3457;

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
                System.out.println("Please insert an integer");
                scanner.nextLine();
            }
        } while (true);
        output.writeObject(number);
    }

    private static void displayBoard() throws IOException{
        try {
            int rows = Byte.toUnsignedInt((byte) input.readObject());
            int columns = Byte.toUnsignedInt((byte) input.readObject());
            List<Byte> encodedBoard = new ArrayList<>(rows * columns);
            for(int row = 0; row < rows; ++row){
                for( int col = 0; col < columns; ++col){
                    encodedBoard.add((byte) input.readObject());
                }
            }
            BoardPrinter printer = new BoardPrinter(rows, columns);
            printer.printBoard(encodedBoard);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
