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

/**
 * The subject of observation for the client GUI and notifies the client GUI
 * of any changes that occur during the run of the program.
 * @author Casey, Karman
 * @version 1.0
 */
public class InputListener implements Runnable {
    
	//Attributes
	private Socket socket;
	private ObjectInputStream ois;
	private int number;
	private List<PropertyChangeListener> observers = new ArrayList<>();

    //Methods
	
	/**
	 * Constructor for Inputlistener that takes in the socket and observer
	 * @param socket players socket
	 * @param observer Inputlistener's observer
	 */
	public InputListener(Socket socket, PropertyChangeListener observer) {
		this.socket = socket;
		observers.add(observer);
	}

	/**
	 * Constructor for Inputlistener that takes in the number, socket and observer
	 * @param number players number
	 * @param socket players socket
	 * @param observer Inputlistener's observer
	 */
	public InputListener(int number, Socket socket, PropertyChangeListener observer) {
		this.number = number;
		this.socket = socket;
		observers.add(observer);
	}
	
	/**
	 * Reads the input from the client
	 */
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
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } 
	}
	
	/**
	 * Get the users number
	 * @return users number
	 */
    public int getNumber() {
		return number;
	}

    /**
     * Set the users number
     * @param number users number
     */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Notifies the observer when an object is written
	 * @param obj any object sent to the opposing player
	 */
	public void notifyListeners(Object obj)
    {
        for( PropertyChangeListener observer : observers )
        {
            observer.propertyChange(new PropertyChangeEvent(this, null, null, obj));
        }
    }

}






