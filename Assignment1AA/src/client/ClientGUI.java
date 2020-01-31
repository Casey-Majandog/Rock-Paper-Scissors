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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utility.Container;
import utility.Game;
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

	Container container;

	GameContainer controller;

	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	InputListener lis;

	FXMLLoader loader;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		try {

			loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));

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

	public boolean connectServer(String user, String ip) {

		try {

			Socket socket = new Socket(ip, 3333);

			// Create an object output stream to send the message to server.
			OutputStream os = socket.getOutputStream();
			oos = new ObjectOutputStream(os);
			lis = new InputListener(0, socket, this);
			new Thread(lis).start();

//			System.out.println("ConnectServer user: " +user);
//			oos.writeObject(user);
//			controller = getController();

//			if(controller.getSocketNum() ==2) {
//				oos.writeObject(user);
//			}
//			
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

	public GameContainer getController() {
		return controller;
	}

	public void setController(GameContainer controller) {
		this.controller = controller;
	}

	public void writeMessage(Object msg) throws IOException {
		oos.writeObject(msg);
	}

	public void writeGame(Object game) throws IOException {
		
		String type = game.toString();
		
		if (type.equals("rock")) {
			controller.setImg1(new Image("client/rock.png"));
		}
		if (type.equals("paper")) {
			controller.setImg1(new Image("client/paper.png"));
		}
		if (type.equals("scissors")) {
			controller.setImg1(new Image("client/scissors.jpg"));
		}
		oos.writeObject(game);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// check for instance of
		// New added line to append message object to other players GUI

		GameContainer controller = getController();
		if (evt.getNewValue().getClass().isInstance(new Message())) {
			System.out.println((Message) evt.getNewValue());
			controller.appendMessage((Message) evt.getNewValue());
		} else if (evt.getNewValue().getClass().isInstance(new Game())) {
			String type = evt.getNewValue().toString();
			if (type.equals("rock")) {
				controller.setImg2(new Image("client/rock.png"));
			}
			if (type.equals("paper")) {
				controller.setImg2(new Image("client/paper.png"));
			}
			if (type.equals("scissors")) {
				controller.setImg2(new Image("client/scissors.jpg"));
			}
		}
//	    else if(evt.getNewValue().getClass().isInstance(s))
//	    {
//	        System.out.println("In if Srrgnng" + evt.getNewValue().getClass());
//	        controller.setPlayer2((String)evt.getNewValue());
//	    }

	}

	@FXML
	public void handleClearButtonAction(ActionEvent event) {
		displayName.setText("");
		serverIP.setText("");
	}
}
