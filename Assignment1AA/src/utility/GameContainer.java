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

public class GameContainer implements Initializable {

	@FXML
	public TextArea chat, msg;

	@FXML
	public Text player1, player2;
	
	@FXML
	public ImageView img1, img2;

	public int socketNum;

	private Message message1;

	ClientGUI client;

	Game type;
	
	String user;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void printMessage(ActionEvent e) throws IOException {
		Message message2 = new Message();
		message2.setUser(user);
		Date timeStamp = new Date();
		message2.setMsg(msg.getText());
		message2.setTimeStamp(timeStamp);
		@SuppressWarnings("deprecation")
		String msgChat = message2.toString();
		chat.appendText(msgChat);
		msg.clear();
		client.writeMessage(message2);

	}

	@FXML
	public void clickGame(ActionEvent e) throws IOException {
		String id = ((Control) e.getSource()).getId();
		// https://stackoverflow.com/questions/24302636/better-way-for-getting-id-of-the-clicked-object-in-javafx-controller
		type = new Game(id);
		client.writeGame(type);

	}

	public TextArea getChat() {
		return chat;
	}

	public void setChat(TextArea chat) {
		this.chat = chat;
	}

	public Message getMessage1() {
		return message1;
	}

	public void setMessage1(Message message1) {
		this.message1 = message1;
	}


	
	
	public Text getPlayer1() {
		return player1;
	}

	public Text getPlayer2() {
		return player2;
	}
	
	public void setPlayer1(Text player1){
        this.player1 = player1;
        player1.setText(user);
    }
	
	public void setPlayer2(Text player2){
	    this.player2 = player2;   
	}
	
	public void displayPlayer1(String user1){
        player1.setText(user1);
    }
	
	public void displayPlayer2(String user2){
        player2.setText(user2);
    }
	
	public int getSocketNum() {
		return socketNum;
	}

	public void setSocketNum(int socketNum) {
		this.socketNum = socketNum;
	}

	public void appendMessage(Message msg) {
		chat.appendText(msg.toString());

	}

	public void initializeMessage(Message message) {
		message1 = new Message(message.getUser(), message.getMsg(), message.getTimeStamp());
	}

	public void getClient(Object newClient) {
		this.client = (ClientGUI) newClient;
	}
	
	public void passUserName(String user) {
		this.user = user;
	}

	public ImageView getImg1() {
		return img1;
	}

	public void setImg1(Image pic) {
		img1.setImage(pic);
	}

	public ImageView getImg2() {
		return img2;
	}

	public void setImg2(Image pic) {
		img2.setImage(pic);
	}
	
	
	public void loadGUI() {
		
	}

   
	
	
	
}
