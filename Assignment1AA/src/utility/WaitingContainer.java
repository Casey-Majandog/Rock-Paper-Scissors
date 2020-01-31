package utility;

import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientGUI;
import javafx.fxml.Initializable;

public class WaitingContainer  implements Initializable {

	private ClientGUI client;
	private ObjectOutputStream oos;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void getClient(Object newClient) {
		this.client = (ClientGUI) newClient;
	}
	
	public void getOtherPlayer() {
		oos = client.getOos();
		
	}

	
}
