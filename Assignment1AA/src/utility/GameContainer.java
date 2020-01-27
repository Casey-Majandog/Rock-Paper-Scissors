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
    public void printMessage(ActionEvent e)
    {
        Date timeStamp = new Date();
        message1.setMsg(msg.getText());
        message1.setTimeStamp(timeStamp);
        String msgChat = message1.getUser() + ": " + message1.getMsg() + " @ " + message1.getTimeStamp() + "\n";
        chat.appendText(msgChat);
        
    }
    
    public void initializeMessage(Message message)
    {
        message1 = new Message(message.getUser(), message.getMsg(), message.getTimeStamp());;
    }

}
