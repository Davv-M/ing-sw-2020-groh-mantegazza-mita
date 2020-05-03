package it.polimi.ingsw.PSP38.virtualView;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public final static int SERVER_SOCKET_PORT = 3457;

    public static void main(String[] args)
    {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(SERVER_SOCKET_PORT);
            do {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread threadClient = new Thread(clientHandler);

                threadClient.start();
            } while(true);


        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}