/**
 * Created on Jul 4, 2006
 *
 * Project: demo03_BasicEchoClientandServerExercises
 */
package utility;

import java.io.*;
import java.util.*;

/**
 * @author dwatson, Casey, Karman
 * @version 1.1
 * Sep 8, 2008
 *
 * Class Description: A basic message class that can be transported across
 * the network.
 * 
 */
public class Message implements Serializable
{
	//Constants
	static final long serialVersionUID = 5488945625178844229L;
	//Attributes
	private String 			user;
	private String			msg;
	private Date			timeStamp;
	
	//Constructors
	public Message()
	{
	}
	
	public Message(String user, String msg, Date timeStamp)
	{
		this.user = user;
		this.msg = msg;
		this.timeStamp = timeStamp;
	}

	/**
	 * Gets the current user
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * Sets the current user
	 * @param user the user to set
	 */
	public void setUser(String user)
	{
		this.user = user;
	}

	/**
	 * Gets the message
	 * @return the msg
	 */
	public String getMsg()
	{
		return msg;
	}

	/**
	 * Sets the message
	 * @param msg the msg to set
	 */
	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	/**
	 * Gets the current time 
	 * @return the timeStamp
	 */
	public Date getTimeStamp()
	{
		return timeStamp;
	}

	/**
	 * Sets the current time
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Date timeStamp)
	{
		this.timeStamp = timeStamp;
	}
	
	//Operational Methods
	public String toString()
	{
		return "[" + getTimeStamp().getHours() + ":" + getTimeStamp().getMinutes() + ":"
				+ getTimeStamp().getSeconds() + "] " + getUser() + ": " + getMsg() + "\n";
	}
}
