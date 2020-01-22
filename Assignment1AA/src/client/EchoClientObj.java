/**
 * Created on Jul 4, 2006
 *
 * Project: demo03_BasicEchoClientandServerExercises
 */
package client;

import java.io.*;
import java.net.*;
import javax.swing.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

public class EchoClientObj extends Application {

	Stage window;
	Scene loginScene, menuScreen;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
			// BorderPane root = new BorderPane();
			loginScene = new Scene(root, 400, 400);
			//loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(loginScene);
			window.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void disconnectServer() {
		
	}
	public static boolean connectServer(String user, String ip) {
		String text = "";
		String logOff = "";
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		InputListener lis;

		try {
			// int pn = Integer.parseInt(JOptionPane.showInputDialog("Enter port number"));

			Socket socket = new Socket(ip, 3333);

			// Create an object output stream to send the message to server.
			OutputStream os = socket.getOutputStream();
			oos = new ObjectOutputStream(os);
			
			
			//gui is the listener
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
}
