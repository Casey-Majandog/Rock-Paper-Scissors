/**
 * Created on Jul 4, 2006
 *
 * Project: demo03_BasicEchoClientandServerExercises
 */
package client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.LoginContainer;
import utility.GameContainer;
import utility.InputListener;
import utility.Message;

import java.util.*;

/**
 * @author dwatson
 * @version 1.0
 * 
 *          To determine what your IP address in windows - open a command line
 *          dialog box and type at the prompt "ipconfig".
 * 
 *          in Linux type "ifconfig etho"
 * 
 */

public class ClientGUI extends Application implements PropertyChangeListener {

	Stage window;
	Scene loginScene, menuScreen;
	@FXML
	private Button clearButton, connectButton, send;
	@FXML
	private TextField displayName, serverIP;
	@FXML
	private MenuItem quitMenu;
	@FXML
	public TextArea chat, msg;

	public String userName;

	LoginContainer loginContainer;
	GameContainer gameContainer;
	
	ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    InputListener lis;
	FXMLLoader loader;
    GameContainer controller;
    ClientGUI client;
    
    public ClientGUI getClient() {
		return client;
	}

	public void setClient(ClientGUI client) {
		this.client = client;
	}


	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		try {

		    loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));               
            
            Parent root = loader.load();

			loginScene = new Scene(root, 400, 200);
			// loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(loginScene);
			window.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void disconnectServer() {

	}

	public boolean connectServer(String user, String ip, ClientGUI client) {
		this.client = client;
		try {
			Socket socket = new Socket(ip, 3333);
			userName = user;

			// Create an object output stream to send the message to server.
			OutputStream os = socket.getOutputStream();
			oos = new ObjectOutputStream(os);
			lis = new InputListener(0, socket, this);
			new Thread(lis).start();
			

			System.out.println("CONNECTED");
			if (socket.isConnected()) {
				
			
				return true;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

//		 catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
	}
	
	public void writeMessage(Object msg) throws IOException
	{
	    System.out.println(msg);
	    oos.writeObject(msg);
	}
	
	public GameContainer getGameContainer(GameContainer gameContainer) {
		return gameContainer;
		
	}
	
	public void setGameContainer(GameContainer gameContainer) {
		this.gameContainer = gameContainer;
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("b4 property change in clientGUI");
		System.out.println(((Message)evt.getNewValue()).toString());
		//New added line to append message object to other players GUI
		gameContainer.appendMessage((Message)evt.getNewValue());
		//controller.getChat().appendText(((Message)evt.getNewValue()).toString());
        
	}

	@FXML
	public void handleClearButtonAction(ActionEvent event) {
		displayName.setText("");
		serverIP.setText("");
	}
	
	
}
