package utility;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import client.ClientGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class GameContainer implements Initializable
{
    
    @FXML
    public TextArea chat, msg;
    
    private Message message1;
    
    ClientGUI client;
    

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
        String msgChat = message1.getUser() + ": " + message1.getMsg() + " @ " + message1.getTimeStamp() + "\n";
        chat.appendText(msgChat);
        client.writeMessage(message1);
        
           
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

    public void appendMessage(Message msg)
    {
       // String msgChat = msg.getUser() + ": " + msg.getMsg() + " @ " + msg.getTimeStamp() + "\n";
        //chat.appendText(msg.toString());
        chat.appendText("asfdzfhdgv");
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
