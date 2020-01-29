package utility;

import java.io.IOException;
import java.util.Date;

import javax.swing.JOptionPane;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginContainer {
    //Attributes
	@FXML
	private Button clearButton, connectButton, send;
	@FXML
	private TextField displayName, serverIP;
    @FXML
    private MenuItem quitMenu;
    @FXML
    private ClientGUI client;
    
//    private Message message1, message2;
    @FXML
    private Message message1;
    
	@FXML
    private String userName;
    FXMLLoader loader;
    GameContainer gameContainer;
    
    
    //Methods
	@FXML
	public void handleClearButtonAction(ActionEvent event) {
		displayName.setText("");
		serverIP.setText("");
	}

	@FXML
	public void handleConnectButtonAction(ActionEvent event) throws IOException {
		String getUsername = null;
		client = initializeClient();
		if (client.connectServer(displayName.getText(), serverIP.getText(), client)) {
		    setUserName(displayName.getText());
		    
		    Date timeStamp = new Date();
		    
		    Message newMessage = new Message(getUsername, null, timeStamp); 
		    
			FXMLLoader loader = new FXMLLoader(getClass().getResource("client/GameScreen.fxml"));		        
			loader.setLocation(getClass().getResource("/client/GameScreen.fxml"));
			
			Parent gameViewParent = loader.load();
			Scene gameScene = new Scene(gameViewParent);
			
			gameContainer = loader.getController();
			gameContainer.initializeMessage(newMessage);
			gameContainer.setClient(client);
			client.setGameContainer(gameContainer);

			// get existing window from loginScene
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(gameScene);
			window.show();

		} else {
			System.out.println("error");
		}
		   
		
	}
	
	public ClientGUI initializeClient() {
		ClientGUI client = new ClientGUI();
		return client;
	}

	@FXML
	public void handleQuitMenuAction(ActionEvent event) {
		Platform.exit();
		// http://www.learningaboutelectronics.com/Articles/How-to-add-functionality-to-a-menu-in-JavaFX.php
	}
	
	@FXML
	public void handleDisconnectButtonAction(ActionEvent event) {
		Platform.exit();
		// http://www.learningaboutelectronics.com/Articles/How-to-add-functionality-to-a-menu-in-JavaFX.php
	}
	
	public void initializeMessage(Message message)
	{
//	    message1.setMsg(message.getMsg());
//	    message1.setTimeStamp(message.getTimeStamp());
//	    message1.setUser(message.getUser());
	    message1 = new Message(message.getUser(), message.getMsg(), message.getTimeStamp());
	    System.out.println("Message has been set: " + message1.toString());
	}

	public FXMLLoader getLoader() {
		return loader;
	}

	public void setLoader(FXMLLoader loader) {
		this.loader = loader;
	}
	
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
