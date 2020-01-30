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

public class Container {
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
    private String userName ="No user";
    
    
    //Methods
	@FXML
	public void handleClearButtonAction(ActionEvent event) {
		displayName.setText("");
		serverIP.setText("");
	}

	@FXML
	public void handleConnectButtonAction(ActionEvent event) throws IOException {
		client = new ClientGUI();
		String getUsername = null;
		if (client.connectServer(displayName.getText(), serverIP.getText())) {
		    getUsername = displayName.getText();
		    
		    Date timeStamp = new Date();
		    Message newMessage = new Message(getUsername, null, timeStamp); 
		    
			FXMLLoader loader = new FXMLLoader(getClass().getResource("client/GameScreen.fxml"));		        
			loader.setLocation(getClass().getResource("/client/GameScreen.fxml"));
			
			Parent gameViewParent = loader.load();
			Scene gameScene = new Scene(gameViewParent);
			GameContainer controller = loader.getController();
			
			controller.initializeMessage(newMessage);
			controller.getClient(client);
			controller.setPlayer1(getUsername);
			client.setController(controller);

			// get existing window from loginScene
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(gameScene);
			window.show();

		} else {
			System.out.println("Server not up");
		}
		   
		
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
	    message1 = new Message(message.getUser(), message.getMsg(), message.getTimeStamp());
	    System.out.println("Message has been set: " + message1.toString());
	}

}
