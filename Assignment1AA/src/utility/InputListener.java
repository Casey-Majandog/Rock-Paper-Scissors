package utility;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InputListener implements Runnable {
	// attributes
	private Socket socket;
	private ObjectInputStream ois;
	private int number;
	private List<PropertyChangeListener> observers = new ArrayList<>();

	
	public InputListener(Socket socket, PropertyChangeListener observer) {
		this.socket = socket;
		observers.add(observer);
	}

	public InputListener(int number, Socket socket, PropertyChangeListener observer) {
		this.number = number;
		this.socket = socket;
	}
	
	public InputListener(List<PropertyChangeListener> o)
	{
	    
	}

	@Override
	public void run() {

		try {
		    
			ois = new ObjectInputStream(socket.getInputStream());
			System.out.println("Input listener Run");
			notifyListeners(new Message("User2", "Yeet", null));
		     //GUI will be the loop for the assignment
            while(true)
            {
                //comment
                //Read input from client
                Message msg = (Message)ois.readObject();
//                System.out.println(msg);
                //Echo message back to client with updated time stamp.
                msg.setTimeStamp(new Date());
                
     
                
                //Don't need to close because everything is dropped once connection is lost
   
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
	}
	
	
    private void notifyListeners(Message msg)
    {
        for( PropertyChangeListener observer : observers )
        {
            System.out.println("Notify listeners: " + msg);
            observer.propertyChange(new PropertyChangeEvent(this, null, null, msg));
        }
    }
	
	private void sendMessage()
	{
	    
	}
	
	private void readMessage()
	{
	    
	}


}






