package it.polimi.ingsw.PSP38.Client;

import it.polimi.ingsw.PSP38.virtualView.Protocol;
import it.polimi.ingsw.PSP38.virtualView.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    private static ObjectInputStream input;
    private static ObjectOutputStream output;

    public static void main(String[] args) {
        Socket serverSocket;
        //InetAddress ipServer=new InetAddress(192.168.1.5);
        try {
            serverSocket = new Socket(InetAddress.getLocalHost(), Server.SERVER_SOCKET_PORT);
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
                Protocol p = (Protocol) input.readObject();
                switch (p) {
                    case NOTIFY_MESSAGE:
                        notifyMessage();
                        break;
                    case ASK_INT:
                        askInt();
                        break;
                    case ASK_STRING:
                        askString();
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

    private static void askString() throws IOException {
        Scanner scanner = new Scanner(System.in);
        output.writeObject(scanner.nextLine());
    }

    private static void askInt() throws IOException {
        Scanner scanner = new Scanner(System.in);
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


}
