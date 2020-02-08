package utility;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import client.ClientGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * A controller that contains the methods for the GameScreen.fxml file
 * @author Casey, Karman
 * @version 1.0
 */
public class GameContainer implements Initializable {


    //FXML Attributes
	@FXML
	public TextArea chat, msg;
	@FXML
	public Text player1, player2;	
	@FXML
	public ImageView img1, img2;
	@FXML 
	public Button rock, paper, scissors;

	//Attributes
	private ClientGUI client;
	private Game type;
	private String user;

//---------------------------------METHODS-----------------------------------   	

	/**
	 * Prints the message in the players chat box and then sends that message
	 * to the opposing player
	 * @param e Event that triggered the method
	 * @throws IOException
	 */
	@FXML
	public void printMessage(ActionEvent e) throws IOException {
		Message message2 = new Message();
		message2.setUser(user);
		Date timeStamp = new Date();
		message2.setMsg(msg.getText());
		message2.setTimeStamp(timeStamp);
		String msgChat = message2.toString();
		chat.appendText(msgChat);
		msg.clear();
		client.writeMessage(message2);

	}

	/**
	 * Tells the opposing player the players game choice
	 * @param e Event that triggered the method
	 * @throws IOException
	 */
	@FXML
	public void clickGame(ActionEvent e) throws IOException {
		String id = ((Control) e.getSource()).getId();
		// https://stackoverflow.com/questions/24302636/better-way-for-getting-id-of-the-clicked-object-in-javafx-controller
		type = new Game(id);
		client.writeGame(type);

	}
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {


    }
	
	/**
	 * Appends the opposing player sent message to the players chat box
	 * @param msg The message sent from the opposing player
	 */
	public void appendMessage(Message msg) {
	        chat.appendText(msg.toString());

	    }
	
	/**
	 * Sets the display names on the games screen
	 */
	public void setDisplayNames() {
		player1.setText(user);
		client.writeUser(user);
	}
	
	/**
	 * Sets player 1's display name
	 * @param user1 Player 1's username
	 */
	public void displayPlayer1(String user1){
        player1.setText(user1);
    }
    
	/**
	 * Sets player 2's display name
	 * @param user2 Player 2's username
	 */
    public synchronized void displayPlayer2(String user2){
        player2.setText(user2);
    }
	
	/**
	 * Disables the game buttons on the game screen GUI
	 */
	public void hideButton()
	{
	    rock.setDisable(true);
	    paper.setDisable(true);
	    scissors.setDisable(true);
	}
	
	/**
	 * Enables the game buttons on the game screen GUI
	 */
	public void showButton()
    {
        rock.setDisable(false);
        paper.setDisable(false);
        scissors.setDisable(false);
    }
	
	/**
	 * Passes the username into the controller
	 * @param user A players username
	 */
	public void passUserName(String user) {
	        this.user = user;
	    }
	
//---------------------------------GETTERS AND SETTERS----------------------------------- 
	/**
	 * Get player 1
	 * @return player 1 username
	 */
	public Text getPlayer1() {
		return player1;
	}

	/**
	 * Get player 2
	 * @return player 2 username
	 */
	public Text getPlayer2() {
		return player2;
	}
	
	/**
	 * Sets player 1's username
	 * @param player1 text area on the GUI
	 */
	public void setPlayer1(Text player1){
        this.player1 = player1;
        player1.setText(user);
    }
	
	/**
	 * Sets player 1's username
	 * @param player2 player2 text area on the GUI
	 */
	public void setPlayer2(Text player2){
	    this.player2 = player2;   
	}
	
	/**
	 * Gets the client object
	 * @param newClient client object
	 */
	public void getClient(Object newClient) {
		this.client = (ClientGUI) newClient;
	}
	
	/**
	 * Gets player 1's image display
	 * @return player 1 image display
	 */
	public ImageView getImg1() {
		return img1;
	}

	/**
	 * Set player 1's image game choice on GUI
	 * @param pic contains the game image
	 */
	public void setImg1(Image pic) {
		img1.setImage(pic);
	}

	/**
	 *Gets player 1's image display
	 * @return player 2 image display
	 */
	public ImageView getImg2() {
		return img2;
	}

	/**
	 * Set player 2's image game choice on GUI
	 * @param pic pic contains the game image
	 */
	public void setImg2(Image pic) {
		img2.setImage(pic);
	
	}
	
	
}
