/**
 * Created on Jul 4, 2006
 *
 * Project: demo03_BasicEchoClientandServerExercises
 */
package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import javax.swing.JOptionPane;

import utility.Message;

/**
 * @author dwatson
 * @version 1.0
 * 
 * To determine what your IP address in windows - open a command line dialog
 * box and type at the prompt "ipconfig".
 * 
 * in Linux type "ifconfig etho"
 * 
 */

public class EchoClientObj
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String text = "";
		String user = "";
		String logOff = "";
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		
		try
		{
			String ip = JOptionPane.showInputDialog("Enter ip address");
			//int pn = Integer.parseInt(JOptionPane.showInputDialog("Enter port number"));
			
			Socket socket = new Socket(ip,3333);
			
			//Create an object output stream to send the message to server.
            OutputStream os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);
            
            //Create an object input stream to catch the echo message from the server.
            InputStream is = socket.getInputStream();
            ois = new ObjectInputStream(is);
			
			user = JOptionPane.showInputDialog("Enter User Name");
			
			while(!logOff.equals("quit"))
			{
				text = JOptionPane.showInputDialog("Enter Message");
				
				Message msg = new Message(user,text,new Date());				
				
				oos.writeObject(msg);
				
				msg = (Message)ois.readObject();
				JOptionPane.showMessageDialog(null,msg.toString());
				
				logOff = JOptionPane.showInputDialog("Enter \"quit\" to end connection");
			}
			
			//close all sockets and streams
            socket.close();
            ois.close();
            oos.close();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
