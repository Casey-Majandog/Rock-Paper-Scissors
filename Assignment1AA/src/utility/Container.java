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
    
    private ClientGUI client;
    
    private Message message;
    
    private String userName;
    
    
    //Methods
    @FXML
    private void printMessage(ActionEvent e)
    {
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

	@FXML
	public void handleConnectButtonAction(ActionEvent event) throws IOException {
		client = new ClientGUI();
		if (client.connectServer(displayName.getText(), serverIP.getText())) {
		    System.out.println(userName);
			Parent gameViewParent = FXMLLoader.load(getClass().getClassLoader().getResource("client/GameScreen.fxml"));
			Scene gameScene = new Scene(gameViewParent);

			// get existing window from loginScene
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(gameScene);
			window.show();
		} else {
			System.out.println("error");
		}
		userName = displayName.getText();
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

}
