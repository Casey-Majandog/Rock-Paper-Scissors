package server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import utility.InputListener;
import utility.Message;

public class ClientHandler implements Runnable, PropertyChangeListener
{
    //attributes
    private Socket socket1;
    private Socket socket2;
    private ObjectOutputStream oos1;
    private ObjectOutputStream oos2;
    private InputListener lis1;
    private InputListener lis2;

    
//Two sockets come from the server
    public ClientHandler(Socket socket1, Socket socket2)
    {
        this.socket1 = socket1;
        this.socket2 = socket2;
        
        try
        {
            oos1 = new ObjectOutputStream(this.socket1.getOutputStream());
            oos2 = new ObjectOutputStream(this.socket2.getOutputStream());
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        lis1 = new InputListener(1, this.socket1, this);
        lis2 = new InputListener(2, this.socket2, this);

        
    }
/**
 * Reading and writing occurs in the run code
 */
    @Override
    public void run()
    {
       
        
        new Thread(lis1).start();
        new Thread(lis2).start();
 
       
    }
    
    public void propertyChange(PropertyChangeEvent evt)
    {
        
    }

}

















