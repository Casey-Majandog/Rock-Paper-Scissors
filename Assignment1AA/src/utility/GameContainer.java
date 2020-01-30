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
import javafx.scene.text.Text;

public class GameContainer implements Initializable
{
    
    @FXML
    public TextArea chat, msg;
    
    @FXML
    public Text player1, player2;
    
    public int socketNum;
    

    private Message message1;
    
    
    ClientGUI client;
    
    Game type;
    
    


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // TODO Auto-generated method stub
        
    }
    
    @FXML
    public void printMessage(ActionEvent e) throws IOException
    {
        
        Date timeStamp = new Date();
        message1.setMsg(msg.getText());
        message1.setTimeStamp(timeStamp);
        @SuppressWarnings("deprecation")
        String msgChat = "["+ timeStamp.getHours() + ":" + timeStamp.getMinutes() + ":" + timeStamp.getSeconds() + "] " +message1.getUser() + ": " + message1.getMsg() + "\n";
        chat.appendText(msgChat);
        msg.clear();
        client.writeMessage(message1);
        
           
    }
    

  @FXML
  public void clickGame(ActionEvent e) throws IOException 
  {
      String id = ((Control)e.getSource()).getId();
      //https://stackoverflow.com/questions/24302636/better-way-for-getting-id-of-the-clicked-object-in-javafx-controller
      type = new Game(id);
      client.writeGame(type);
      
  }
     
    
    public TextArea getChat()
    {
        return chat;
    }

    public void setChat(TextArea chat)
    {
        this.chat = chat;
    }

    public Message getMessage1()
    {
        return message1;
    }

    public void setMessage1(Message message1)
    {
        this.message1 = message1;
    }
    
    public void setPlayer1(String username)
    {
        this.player1.setText(username);
    }
    
    public void setPlayer2(String username)
    {
        this.player2.setText(username);
    }
  
    
    public int getSocketNum()
    {
        return socketNum;
    }

    public void setSocketNum(int socketNum)
    {
        this.socketNum = socketNum;
    }
    
    
    
    public void appendMessage(Message msg)
    {
        String msgChat = "["+ msg.getTimeStamp().getHours() + ":" + msg.getTimeStamp().getMinutes() + ":" + msg.getTimeStamp().getSeconds() + "] " +msg.getUser() + ": " + msg.getMsg() + "\n";
        chat.appendText(msgChat);
        
    }
    
    public void initializeMessage(Message message)
    {
        message1 = new Message(message.getUser(), message.getMsg(), message.getTimeStamp());
    }
    
    public void getClient(Object newClient)
    {
        this.client = (ClientGUI) newClient;
    }
    
    

}
