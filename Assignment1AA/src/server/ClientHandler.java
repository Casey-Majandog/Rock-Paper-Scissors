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
import utility.Person;

/**
 * Takes the sockets created in the server and instantiates input listeners and object output streams.
 * Communication between the two GUI's are handled here.
 * @author Casey, Karman
 * @version 1.0
 */
public class ClientHandler implements Runnable, PropertyChangeListener
{
    //Attributes
    private Socket socket1;
    private Socket socket2;
    private ObjectOutputStream oos1;
    private ObjectOutputStream oos2;
    private InputListener lis1;
    private InputListener lis2;
  
    //Methods
    
    /**
     * Takes the sockets from the server and creates object output streams and input listeners
     * @param socket1 First socket from server
     * @param socket2 Second socket from server
     */
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
     * Start the threads and chooses a random player two go first
     */
    @Override
    public void run()
    {
        
        new Thread(lis1).start();
        new Thread(lis2).start();
        lis1.notifyListeners(new Person());
        lis2.notifyListeners(new Person());
        
        int number = (int)(Math.round(Math.random()+1));
       
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        if (number == 1)
        {
            lis1.notifyListeners(number);
        }
        else
        {
            lis2.notifyListeners(number);
        }
 
       
    }
    
    /**
     * Where communication between the two players occur. Sends message to the opposing player.
     */
    public synchronized void propertyChange(PropertyChangeEvent event)
    {
       InputListener lis = (InputListener) event.getSource();
       if (lis.getNumber() == 1) {
    	   try {
			oos2.writeObject(event.getNewValue());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   
       } else {
    	   try {
			oos1.writeObject(event.getNewValue());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
    }

}

