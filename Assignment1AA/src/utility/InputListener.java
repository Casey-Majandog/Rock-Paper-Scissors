package utility;

import java.io.ObjectInputStream;
import java.net.Socket;

public class InputListener implements Runnable
{
    //attributes
    private Socket socket;
    private ObjectInputStream ois;
    private int number;
    
    public InputListener(Socket socket)
    {
        this.socket = socket;
    }
    
    public InputListener(int number, Socket socket)
    {
        this.number = number;
        this.socket = socket;
    }
    
    @Override
    public void run()
    {
        // TODO Auto-generated method stubasdsad
    	//karmans edit test
        
    }

}
