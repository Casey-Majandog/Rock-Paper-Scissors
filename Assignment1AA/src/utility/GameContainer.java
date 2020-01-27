package utility;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class GameContainer implements Initializable
{
    
    @FXML
    public TextArea chat, msg;
    
    private Message message1;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // TODO Auto-generated method stub
        
    }
    
    @FXML
    private void printMessage(ActionEvent e)
    {
        Date timeStamp = new Date();
       message1.setMsg(msg.getText());
       // message2 = new Message(message2.getUser(), msg.getText(), timeStamp); 
        String msgChat = message1.getUser() + ": " + message1.getMsg() + " @ " + message1.getTimeStamp() + "\n";
//        String msgChat = message1.getUser();
        chat.appendText(msgChat);
    }
    
    public void initializeMessage(Message message)
    {
//      message1.setMsg(message.getMsg());
//      message1.setTimeStamp(message.getTimeStamp());
//      message1.setUser(message.getUser());
        message1 = new Message(message.getUser(), message.getMsg(), message.getTimeStamp());
        System.out.println("Message has been set: " + message1.toString());
    }

}
