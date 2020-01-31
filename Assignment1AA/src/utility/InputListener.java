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
		observers.add(observer);
	}
	
	public InputListener(List<PropertyChangeListener> o)
	{
	    
	}

	@Override
	public void run() {

		try {
		    
			
			ois = new ObjectInputStream(socket.getInputStream());

		     //GUI will be the loop for the assignment
            while(true)
            {
                //Read input from client
                Object obj = ois.readObject();
                notifyListeners(obj);
                
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
	}
	
	
    public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void notifyListeners(Object obj)
    {
        for( PropertyChangeListener observer : observers )
        {
            observer.propertyChange(new PropertyChangeEvent(this, null, null, obj));
        }
    }

}






