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
import utility.Container;
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

	private ClientGUI client;

	private Message message;

	public String userName;

	Container container;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
			// BorderPane root = new BorderPane();
			loginScene = new Scene(root, 400, 200);
			// loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(loginScene);
			window.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleConnectButtonAction(ActionEvent event) throws IOException {
		if (connectServer(displayName.getText(), serverIP.getText())) {
			System.out.println("DISPLAY NAME TEST: "+displayName.getText());
			userName = displayName.getText();
			Parent gameViewParent = FXMLLoader.load(getClass().getClassLoader().getResource("client/GameScreen.fxml"));
			Scene gameScene = new Scene(gameViewParent);

			// get existing window from loginScene
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(gameScene);
			window.show();
		} else {
			System.out.println("error");
		}

		System.out.println(userName);
	}

	public static void disconnectServer() {

	}

	public boolean connectServer(String user, String ip) {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		InputListener lis;

		try {

			Socket socket = new Socket(ip, 3333);
			userName = user;

			// Create an object output stream to send the message to server.
			OutputStream os = socket.getOutputStream();
			oos = new ObjectOutputStream(os);
			lis = new InputListener(0, socket, this);
			new Thread(lis).start();

			// gui is the listener
//
//			// Create an object input stream to catch the echo message
//			// from the server
//			InputStream is = socket.getInputStream();
//			ois = new ObjectInputStream(is);

//			while (!logOff.equals("quit")) {
//
//				text = JOptionPane.showInputDialog("Enter Message");
//
//				Message msg = new Message(user, text, new Date());
//
//				oos.writeObject(msg);
//
//				msg = (Message) ois.readObject();
//				JOptionPane.showMessageDialog(null, msg.toString());
//
//				logOff = JOptionPane.showInputDialog("Enter \"quit\" to end connection");
//			}
//
//			// close all sockets and streams
//			socket.close();
//			ois.close();
//			oos.close();
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void printMessage(ActionEvent e) {
		Date timeStamp = new Date();
		System.out.println(userName);
		message = new Message(userName, msg.getText(), timeStamp);

		chat.appendText(message + "\n");
	}

	@FXML
	public void handleClearButtonAction(ActionEvent event) {
		displayName.setText("");
		serverIP.setText("");
	}
}
