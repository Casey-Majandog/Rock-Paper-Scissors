package utility;

import java.io.IOException;

import client.ClientGUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * A controller that contains the methods for the LoginScreen.fxml file
 * @author Casey, Karman
 * @version 1.0
 */
public class Container {
	
    //FXML Attributes
	@FXML
	private Button clearButton, connectButton, send;
	@FXML
	private TextField displayName, serverIP;
	@FXML
	private MenuItem quitMenu;
	@FXML
	private ClientGUI client;
	@FXML
    private Message message1;

	//Attributes
	private Stage window;
	private String userName = "No user";

	//Methods
	
	/**
	 * Clears the username and ip text fields
	 * @param event Event that triggered the method
	 */
	@FXML
	public void handleClearButtonAction(ActionEvent event) {
	    
		displayName.setText("");
		serverIP.setText("");
	}

	/**
	 * Connects the player to the server, gets their username and then sends them to the waiting screen
	 * @param event Event that triggered the method
	 * @throws IOException
	 */
	@FXML
	public void handleConnectButtonAction(ActionEvent event) throws IOException {
		client = new ClientGUI();
		client.connectServer(displayName.getText(), serverIP.getText());

		FXMLLoader loader = new FXMLLoader(getClass().getResource("client/WaitingScreen.fxml"));
		loader.setLocation(getClass().getResource("/client/WaitingScreen.fxml"));
		Container container = loader.getController();

		client.setContainer(container);
		client.setUserName(displayName.getText());

		Parent gameViewParent = loader.load();
		Scene gameScene = new Scene(gameViewParent);

		// get existing window from loginScene
		window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		client.setWindow(window);
		window.setScene(gameScene);
		window.show();

	}


	/**
	 * Sets the username parameter in the message object
	 * @param message message object
	 */
	public void initializeMessage(Message message) {
		message1 = new Message(message.getUser(), message.getMsg(), message.getTimeStamp());
	}

	/**
	 * Gets the players username
	 * @return players username
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the players username
	 * @param userName the players username
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
