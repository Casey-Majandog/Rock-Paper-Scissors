package utility;

import java.io.IOException;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Container {

	@FXML
	private Button clearButton, connectButton;
	@FXML
	private TextField displayName, serverIP;
	@FXML
	private MenuItem quitMenu;

	@FXML
	public void handleClearButtonAction(ActionEvent event) {
		displayName.setText("");
		serverIP.setText("");
	}

	@FXML
	public void handleConnectButtonAction(ActionEvent event) throws IOException {
		ClientGUI client = new ClientGUI();
		if (client.connectServer(displayName.getText(), serverIP.getText())) {

			Parent gameViewParent = FXMLLoader.load(getClass().getClassLoader().getResource("client/GameScreen.fxml"));
			Scene gameScene = new Scene(gameViewParent);

			// get existing window from loginScene
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(gameScene);
			window.show();
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

}
