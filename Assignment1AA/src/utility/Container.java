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
    public TextArea chat, msg;
    @FXML
    private ClientGUI client;
    
//    private Message message1, message2;
    @FXML
    private Message message1;
    @FXML
    private String userName ="No user";
    
    
    //Methods
    @FXML
    private void printMessage(ActionEvent e)
    {
        Date timeStamp = new Date();
       
       // message2 = new Message(message2.getUser(), msg.getText(), timeStamp); 
      //  String msgChat = message1.getUser() + ": " + message1.getMsg() + " @ " + message1.getTimeStamp() + "\n";
        String msgChat = message1.getUser();
        chat.appendText(msgChat);
    } 

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
		    System.out.println(getUsername);
		    
		    Date timeStamp = new Date();
		    
		    Message newMessage = new Message(getUsername, null, timeStamp); 
		    
		    System.out.println(newMessage.toString());
		    
		    
		    
//		    initializeMessage(newMessage);
		    
			FXMLLoader loader = new FXMLLoader(getClass().getResource("client/GameScreen.fxml"));		        
			loader.setLocation(getClass().getResource("/client/GameScreen.fxml"));
			
			Parent gameViewParent = loader.load();
			Scene gameScene = new Scene(gameViewParent);
			
			GameContainer controller = loader.getController();
			controller.initializeMessage(newMessage);

			// get existing window from loginScene
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(gameScene);
			window.show();
			System.out.println("Username after loop: " + newMessage.getUser());
		} else {
			System.out.println("error");
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
//	    message1.setMsg(message.getMsg());
//	    message1.setTimeStamp(message.getTimeStamp());
//	    message1.setUser(message.getUser());
	    message1 = new Message(message.getUser(), message.getMsg(), message.getTimeStamp());
	    System.out.println("Message has been set: " + message1.toString());
	}

}
