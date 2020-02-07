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

import java.util.*;

/**
 * The ClientGUI opens a login screen and prompts the user for a user name and ip address to connect to a game.
 * If the player is the first person to connect to the game, they are redirected to a waiting screen. If they
 * are the second person to connect, both players will be directed to the game screen.
 * @author Casey, Karman
 * @version 1.0
 */

public class ClientGUI extends Application implements PropertyChangeListener {

	//FXML Attributes
	@FXML
	private Button clearButton, connectButton, send;
	@FXML
	private TextField displayName, serverIP;
	@FXML
	private MenuItem quitMenu;
	@FXML
	public TextArea chat, msg;

	//Attributes
	public String userName, userName2, player1Pick, player2Pick;
    private Stage primaryStage;
	private String ip;
	private Scene loginScene, gameScreen;
	private Container container;
	private GameContainer controller;
	private Socket socket;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	private InputListener lis;
	private FXMLLoader loader;

	public static void main(String[] args) {
		launch(args);

	}

//---------------------------------REGULAR METHODS-----------------------------------	
	/**
	 * Loads up the login screen
	 * @param primaryStage Stage where the GUI will be set in
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {

			loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));

			Parent root = loader.load();

			loginScene = new Scene(root, 400, 200);
			primaryStage.setScene(loginScene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * Clears the username and ip textfields
     * @param event The event that was triggered when the clear button was pressed
     */
    @FXML
    public void handleClearButtonAction(ActionEvent event) {
        displayName.setText("");
        serverIP.setText("");
    }

	/**
	 * Connecter the user to the server using the username and ip that they inputed in
	 * the login screen
	 * @param user The username that was given in the login screen
	 * @param ip The ip that was given in the login screen
	 */
	public void connectServer(String user, String ip) {
		this.ip = ip;
		try {

			socket = new Socket(ip, 3333);

			// Create an object output stream to send the message to server.
			OutputStream os = socket.getOutputStream();
			oos = new ObjectOutputStream(os);
			lis = new InputListener(0, socket, this);
			new Thread(lis).start();

			System.out.println("CONNECTED");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Takes the message that the player wants to send and writes it to the other player
	 * @param msg The message that was sent to the other player
	 * @throws IOException
	 */
	public void writeMessage(Object msg) throws IOException {
		oos.writeObject(msg);
	}

	/** 
	 * Sends the choice the player has made to the other player
	 * @param game A game object that holds the game information
	 * @throws IOException
	 */
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

//---------------------------------SYNCHRONIZED METHODS-----------------------------------
	
	/**
	 * Writes the username to the other player
	 * @param user current players username
	 */
	public synchronized void writeUser(String user) {
		try {
			Thread.sleep(300);
			oos.writeObject(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Whenever a player triggers an event that the other player needs to see, this method is called. Depending on
	 * what is sent or triggered, an action will occur on the other players screen.
	 * @param evt PropertyChangeEvent object 
	 */
	@Override
	public synchronized void propertyChange(PropertyChangeEvent evt) {
		// check for instance of
		// New added line to append message object to other players GUI
		if (evt.getNewValue().toString().contains("WIN")) {
			int result = JOptionPane.showConfirmDialog(null, "You lose \n Do you want to play again?", "Info",
					JOptionPane.YES_NO_OPTION);
			controller.hideButton();
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
				e.printStackTrace();
			}

		} else if (evt.getNewValue().toString().contains("PLAYAGAIN")) {
			controller.setImg1(null);
			player1Pick = null;
			controller.setImg2(null);
			player2Pick = null;
		} else if (evt.getNewValue().toString().contains("ENDGAME")) {
			try {
				System.out.println("ENDGAME");
				controller.hideButton();
				controller.setPlayer2(null);
				socket.close();
				oos.close();

				socket = new Socket(ip, 3333);

				// Create an object output stream to send the message to server.
				OutputStream os = socket.getOutputStream();
				oos = new ObjectOutputStream(os);
				lis = new InputListener(0, socket, this);
				new Thread(lis).start();

				controller.passUserName(userName);
				controller.setDisplayNames();

			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		} else if (evt.getNewValue().toString().contains("LOSE")) {
			int result = JOptionPane.showConfirmDialog(null, "You win! \n Do you want to play again?", "Info",
					JOptionPane.YES_NO_OPTION);
			controller.showButton();
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
				player2Pick = "rock";
			}
			if (type.equals("paper")) {
				player2Pick = "paper";
			}
			if (type.equals("scissors")) {
				player2Pick = "scissors";
			}

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
			
			Platform.runLater(() -> {
				// https://stackoverflow.com/questions/21083945/how-to-avoid-not-on-fx-application-thread-currentthread-javafx-application-th
				try {		

					if (userName2 == null) {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/GameScreen.fxml"));
						loader.setLocation(getClass().getResource("/client/GameScreen.fxml"));
						Parent gameViewParent = loader.load();
						gameScreen = new Scene(gameViewParent);
						setController(loader.getController());
					}
					
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
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			controller.displayPlayer2(evt.getNewValue().toString());
			userName2 = evt.getNewValue().toString();
		} else if (evt.getNewValue().getClass().isInstance(new Integer(0))) {
			controller.hideButton();
		}

	}

	/**
	 * Updates the opposing player's GUI image with the other player's game choice
	 * @param player1Pick Player one's game choice
	 * @param player2Pick Player two's game choice
	 */
	private synchronized void updateImg(String player1Pick, String player2Pick) {
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
			e.printStackTrace();
		}
	}

	/**
	 * Determines the winner of the game based on each players choice of rock, paper or scissors.
	 * Prompts the user whether or not they want to play again.
	 * @param player1Pick Player one's game choice
	 * @param player2Pick Player two's game choice
	 * @throws IOException
	 */
	private synchronized void determineWinner(String player1Pick, String player2Pick) throws IOException {
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

	/**
	 * 
	 * Takes the players choice of whether or not they want to play again and either
	 * lets them continue playing or closes their GUI
	 * 
	 * @param choice An integer of either 0 for yes and 1 for no
	 * @throws IOException
	 */
	public synchronized void playAgain(int choice) throws IOException {
		System.out.println("PLay Agin");
		if (choice == 0) {
			controller.setImg1(null);
			player1Pick = null;
			controller.setImg2(null);
			player2Pick = null;

			oos.writeObject("PLAYAGAIN");
			socket.close();
			oos.close();
			
			socket = new Socket(ip, 3333);

			// Create an object output stream to send the message to server.
			OutputStream os = socket.getOutputStream();
			oos = new ObjectOutputStream(os);
			lis = new InputListener(0, socket, this);
			new Thread(lis).start();

		} else {
			oos.writeObject("ENDGAME");
			System.exit(0);

		}
	}

	
//-----------------------------------SETTERS AND GETTERS----------------------------------
	
	   /**
     * Returns the GameContainer controller
     * @return the instance of the GameContainer controller 
     */
    public GameContainer getController() {
        return controller;
    }

    /**
     * Sets the GameContainer controller
     * @param controller The controller used to access methods in the GameContainer class
     */
    public void setController(GameContainer controller) {
        this.controller = controller;
    }

    /**
     * Sets the container
     * @param container Where the controller is loaded into
     */
    public void setContainer(Container container) {
        this.container = container;
    }

	
	/**
	 * Set the primary stage to the desired window
	 * @param window The current window
	 */
	public void setWindow(Stage window)
	{
	    this.primaryStage = window;
	}

	/**
	 * Gets the current players username
	 * @return The players username
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the current players username
	 * @param userName The players username
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}



}
