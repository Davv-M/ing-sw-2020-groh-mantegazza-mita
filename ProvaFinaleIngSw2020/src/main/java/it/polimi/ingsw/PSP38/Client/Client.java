package it.polimi.ingsw.PSP38.Client;

import it.polimi.ingsw.PSP38.virtualView.Protocol;
import it.polimi.ingsw.PSP38.virtualView.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

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
            ObjectInputStream input = new ObjectInputStream(serverSocket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(serverSocket.getOutputStream());

            while(true){
                Protocol p = (Protocol)input.readObject();
                switch (p){
                    case NOTIFY_GAME_FULL: System.out.println("Game is alredy started.. please try later");break;
                    case ASK_NUM_PLAYER: output.writeObject(askNumPlayer());break;
                    case ASK_NICKNAME: output.writeObject(askNickname());break;
                    case ASK_AGE: output.writeObject(askAge());break;
                    case ASK_NICKNAME_AGAIN: output.writeObject(askNicknameAgain());break;
                    default: System.out.println("protocol error"); break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    private static int askNumPlayer(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("benvenuto, sei il primo giocatore a connettersi, scegli il numero di giocatori tra 2-3");
        return scanner.nextInt();
    }

    private static String askNickname(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("inserisci un nickname ");
        return scanner.nextLine();
    }

    private static String askNicknameAgain(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("hai inserito un nickname già occupato, inseriscine uno nuovo ");
        return scanner.nextLine();
    }

    private static int askAge(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("inserisci la tua età ");
        int age = scanner.nextInt();
        while(age < 8){
            System.out.println("sei troppo piccolo ");
            age = scanner.nextInt();
        }
        return age;
    }





}
