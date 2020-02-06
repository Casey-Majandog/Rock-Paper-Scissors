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
import javafx.application.Platform;
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
import utility.Person;
import utility.WaitingContainer;

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

	Scene loginScene, menuScreen, gameScreen;
	private Stage primaryStage;
	@FXML
	private Button clearButton, connectButton, send;
	@FXML
	private TextField displayName, serverIP;
	@FXML
	private MenuItem quitMenu;
	@FXML
	public TextArea chat, msg;

	public String userName, userName2, player1Pick, player2Pick;

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
		this.primaryStage = primaryStage;
		try {

			loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));

			Parent root = loader.load();

			loginScene = new Scene(root, 400, 200);
			// loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void disconnectServer() {

	}

	public void connectServer(String user, String ip) {

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
//			if (socket.isConnected()) {
//			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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

	public void setContainer(Container container) {
		this.container = container;
	}

	public void writeMessage(Object msg) throws IOException {
		oos.writeObject(msg);
	}

	public void writeGame(Object game) throws IOException {

		String type = game.toString();

		if (type.equals("rock")) {
			controller.setImg1(new Image("client/rock.png"));
			player1Pick = "rock";
		}
		if (type.equals("paper")) {
			controller.setImg1(new Image("client/paper.png"));
			player1Pick = "paper";
		}
		if (type.equals("scissors")) {
			controller.setImg1(new Image("client/scissors.jpg"));
			player1Pick = "scissors";
		}

		controller.hideButton();
		oos.writeObject(game);
	}

	public void writeUser(String user) {
		try {
			oos.writeObject(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void propertyChange(PropertyChangeEvent evt) {
		// check for instance of
		// New added line to append message object to other players GUI
		if (evt.getNewValue().toString().contains("WIN")) {
			int result = JOptionPane.showConfirmDialog(null, "You lose \n Do you want to play again?", "Info",
					JOptionPane.YES_NO_OPTION);
			try {
				playAgain(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (evt.getNewValue().toString().contains("DRAW")) {
			int result = JOptionPane.showConfirmDialog(null, "Draw! \n Do you want to play again?", "Info",
					JOptionPane.YES_NO_OPTION);
			try {
				playAgain(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (evt.getNewValue().toString().contains("PLAYAGAIN")) {
			controller.setImg1(null);
			player1Pick = null;
			controller.setImg2(null);
			player2Pick = null;
		} else if (evt.getNewValue().toString().contains("LOSE")) {
			int result = JOptionPane.showConfirmDialog(null, "You win! \n Do you want to play again?", "Info",
					JOptionPane.YES_NO_OPTION);
			try {
				playAgain(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (evt.getNewValue().getClass().isInstance(new Message())) {
			controller.appendMessage((Message) evt.getNewValue());
		} else if (evt.getNewValue().getClass().isInstance(new Game())) {
			String type = evt.getNewValue().toString();
			controller.showButton();
			controller.setImg2(new Image("client/unknown.png"));
			if (type.equals("rock")) {
				// controller.setImg2(new Image("client/rock.png"));
				player2Pick = "rock";
			}
			if (type.equals("paper")) {
				// controller.setImg2(new Image("client/paper.png"));
				player2Pick = "paper";
			}
			if (type.equals("scissors")) {
				// controller.setImg2(new Image("client/scissors.jpg"));
				player2Pick = "scissors";
			}
			System.out.println(player1Pick + " " + player2Pick);
			if (player1Pick != null) {
				updateImg(player1Pick, player2Pick);
			}
			// A method to check if both image are filled.
		} else if (evt.getNewValue().toString().contains("updateImg")) {

			if (player2Pick.equals("rock")) {
				controller.setImg2(new Image("client/rock.png"));
			} else if (player2Pick.equals("paper")) {
				controller.setImg2(new Image("client/paper.png"));
			} else {
				controller.setImg2(new Image("client/scissors.jpg"));
			}

		} else if (evt.getNewValue().getClass().isInstance(new Person())) {
			System.out.println("TEST1");
			Platform.runLater(() -> {
				// https://stackoverflow.com/questions/21083945/how-to-avoid-not-on-fx-application-thread-currentthread-javafx-application-th
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/GameScreen.fxml"));
					loader.setLocation(getClass().getResource("/client/GameScreen.fxml"));
					Parent gameViewParent = loader.load();
					gameScreen = new Scene(gameViewParent);
					setController(loader.getController());

					// To get the username from login
					controller.passUserName(userName);
					controller.getClient(this);
					primaryStage.setScene(gameScreen);
					primaryStage.show();
					controller.setDisplayNames();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} else if (evt.getNewValue().getClass().isInstance(new String())) {
			System.out.println("displayPlayer2" + evt.getNewValue().toString());
			controller.displayPlayer2(evt.getNewValue().toString());
			userName2 = evt.getNewValue().toString();
		} else if (evt.getNewValue().getClass().isInstance(new Integer(0))) {
			controller.hideButton();
		}

	}

	private void updateImg(String player1Pick, String player2Pick) {
		if (player2Pick.equals("rock")) {
			controller.setImg2(new Image("client/rock.png"));
		} else if (player2Pick.equals("paper")) {
			controller.setImg2(new Image("client/paper.png"));
		} else {
			controller.setImg2(new Image("client/scissors.jpg"));
		}
		try {
			oos.writeObject("updateImg");
			determineWinner(player1Pick, player2Pick);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void determineWinner(String player1Pick, String player2Pick) throws IOException {
		boolean win = false;
		boolean draw = false;

		switch (player1Pick) {
		case ("rock"):
			if (player2Pick.equals("rock"))
				draw = true;

			if (player2Pick.equals("paper"))
				;

			if (player2Pick.equals("scissors"))

				win = true;
			break;

		case ("paper"):
			if (player2Pick.equals("rock"))
				win = true;

			if (player2Pick.equals("paper"))
				draw = true;

			if (player2Pick.equals("scissors"))
				;

			break;

		case ("scissors"):
			if (player2Pick.equals("rock"))
				;

			if (player2Pick.equals("paper"))
				win = true;

			if (player2Pick.equals("scissors"))
				draw = true;
			break;
		}

		if (win) {
			oos.writeObject("WIN" + win);
			int result = JOptionPane.showConfirmDialog(null, "You win!\n Do you want to play again?", "Info",
					JOptionPane.YES_NO_OPTION);
			playAgain(result);

		} else if (draw) {
			oos.writeObject("DRAW" + draw);
			int result = JOptionPane.showConfirmDialog(null, "Draw!\n Do you want to play again?", "Info",
					JOptionPane.YES_NO_OPTION);
			playAgain(result);
		} else {
			oos.writeObject("LOSE" + win);
			int result = JOptionPane.showConfirmDialog(null, "You lose!\n Do you want to play again?", "Info",
					JOptionPane.YES_NO_OPTION);
			playAgain(result);
		}

	}

	public void playAgain(int choice) throws IOException {
		System.out.println("PLay Agin");
		if (choice == 0) {
			controller.setImg1(null);
			player1Pick = null;
			controller.setImg2(null);
			player2Pick = null;
			oos.writeObject("PLAYAGAIN");
		}
	}

	@FXML
	public void handleClearButtonAction(ActionEvent event) {
		displayName.setText("");
		serverIP.setText("");
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public void getWindow(Stage window) {
		this.primaryStage = window;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
