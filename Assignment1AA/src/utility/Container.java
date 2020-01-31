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
    
    private Stage window;
    
//    private Message message1, message2;
    @FXML
    private Message message1;
    private String userName ="No user";
    
    int socketNum;
    
    
    //Methods
	@FXML
	public void handleClearButtonAction(ActionEvent event) {
		displayName.setText("");
		serverIP.setText("");
	}

	@FXML
	public void handleConnectButtonAction(ActionEvent event) throws IOException {
		client = new ClientGUI();
			client.connectServer(displayName.getText(), serverIP.getText());
		    setUserName(displayName.getText());

			FXMLLoader loader = new FXMLLoader(getClass().getResource("client/WaitingScreen.fxml"));		        
			loader.setLocation(getClass().getResource("/client/WaitingScreen.fxml"));
			Container container = loader.getController();
			client.setContainer(container);
			
		    
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("client/GameScreen.fxml"));		        
//			loader.setLocation(getClass().getResource("/client/GameScreen.fxml"));
			
			Parent gameViewParent = loader.load();
			Scene gameScene = new Scene(gameViewParent);
			WaitingContainer waitController = loader.getController();
		
			
//			controller.initializeMessage(newMessage);
//			controller.getClient(client);
			
//			controller.setPlayer1(getUserName());
//			controller.passUserName(getUserName());
//			

			// get existing window from loginScene
			window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			client.getWindow(window);
			window.setScene(gameScene);
			window.show();
			

			
			

		   
		
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Stage sendWindow() {
		return window;
	}
	
	

}
