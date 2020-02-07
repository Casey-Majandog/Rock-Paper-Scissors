/**
 * Created on Jul 4, 2006
 *
 * Project: demo03_BasicEchoClientandServerExercises
 */
package server;

import java.io.*;
import java.net.*;
import java.util.*;

import utility.Message;

/**
 * 
 * The Server creates an array list that has the size of 2 and will wait for 2 players to connect
 * in order to start and new thread and game
 * @author Casey, Karman
 * @version 1.0
 */
public class Server implements Runnable
{
    //Attributes
    static ServerSocket serverSocket = null;
    static Socket socket = null;

    //Methods
    
    /**
     * Starts the server up and waits for 2 players to connect
     */
    @Override
    public void run()
    {
        
        ArrayList<Socket> sockets = new ArrayList<>(2);
        
        try
        {
            serverSocket = new ServerSocket(3333);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("Server up and running!!!!!!!!!!");
        
        while(true)
        {
            try
            {
                socket = serverSocket.accept();
                System.out.println("Accepted a client connection.");
                sockets.add(socket);
                
                if(sockets.size() == 2)
                {
                    new Thread(new ClientHandler(sockets.get(0), sockets.get(1))).start();
                    sockets.clear();
                }
                    
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
    }
    
    /**
     * Stops the server (Broken)
     * @throws IOException
     */
    public static void stopServer() throws IOException
    {
        serverSocket.close();
    }
}




