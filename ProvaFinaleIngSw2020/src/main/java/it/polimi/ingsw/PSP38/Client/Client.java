package it.polimi.ingsw.PSP38.Client;

import it.polimi.ingsw.PSP38.virtualView.Protocol;
import it.polimi.ingsw.PSP38.virtualView.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static ObjectInputStream input;
    private static ObjectOutputStream output;

    public static void main(String[] args)
    {
        Socket serverSocket;
        try {
            serverSocket = new Socket(InetAddress.getLocalHost(), Server.SERVER_SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");
        try {
            input = new ObjectInputStream(serverSocket.getInputStream());
            output = new ObjectOutputStream(serverSocket.getOutputStream());

            while(true){
                Protocol p = (Protocol)input.readObject();
                switch (p){
                    case WELCOME_MESSAGE: welcomeMessage(); break;
                    case NOTIFY_MESSAGE: notifyMessage();break;
                    case ASK_NUM_PLAYER: askNumPlayer();break;
                    case ASK_NICKNAME: askNickname();break;
                    case ASK_AGE: askAge();break;
                    case ASK_DIVINITY_CARD: askYoungestPlayerDivinityCards();break;
                    default: System.out.println("protocol error"); break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private static void welcomeMessage(){
        System.out.println("Welcome to SANTORINI");
    }

    private static void notifyMessage()throws IOException{
        try {
            String message = (String)input.readObject();
            System.out.println(message);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static void askNumPlayer()throws IOException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("You are the first match's player, insert players' number ( 2-3 ) ");
        output.writeObject(scanner.nextInt());
    }

    private static void askNickname()throws IOException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose your nickname");
        output.writeObject(scanner.nextLine());
    }


    private static void askAge()throws IOException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("How old are you? (8+)");
        int age = scanner.nextInt();
        while(age < 8){
            System.out.println("You are too young!");
            System.out.println("How old are you? (8+)");
            age = scanner.nextInt();
        }
        output.writeObject(age);
    }


    private static void askYoungestPlayerDivinityCards() throws IOException{
        Scanner scanner = new Scanner(System.in);
        try {
            int numOfPlayer = (int)input.readObject();
            List<String> availableDivinityCards = (List<String>) input.readObject();
            System.out.println("select " + numOfPlayer + " divinities who you prefer ");
            System.out.println("these are available's cards:");
            for(String avc: availableDivinityCards){
                System.out.println(avc);
            }
            output.writeObject(scanner.nextLine());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
