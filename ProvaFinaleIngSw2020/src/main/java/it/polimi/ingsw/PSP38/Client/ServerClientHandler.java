package it.polimi.ingsw.PSP38.Client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerClientHandler {

    public ServerClientHandler(){
        int porta = 4567;
        welcomeMessage();
        System.out.println("Try server connect");
        try {
            Socket s = new Socket(InetAddress.getLocalHost(), porta);
            System.out.println("Server connected");
            System.out.println(" ");

            //ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
            //output.writeObject(askNumOfPlayer());



        } catch (IOException e) {
            System.err.println("Connection failed :( ");
        }


    }

    public void welcomeMessage(){
        System.out.println("-----------------------------------------------------");
        System.out.println("---------------WELCOME TO SANTORINI------------------");
        System.out.println("-----------------------------------------------------");
        System.out.println(" ");
        System.out.println(" ");

    }


    public int askNumOfPlayer(){
        Scanner s = new Scanner (System.in);
        System.out.println("Please enter the number of players ( between 2 and 3 )");
        int numOfPlayer;
        do{
            numOfPlayer = s.nextInt();
        }while(numOfPlayer > 3 || numOfPlayer < 2 );

        return numOfPlayer;
    }



    public static void main( String[] args) {
        ServerClientHandler cv = new ServerClientHandler();




    }

}