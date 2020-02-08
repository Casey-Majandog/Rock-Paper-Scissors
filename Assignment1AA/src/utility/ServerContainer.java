package utility;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import server.Server;

/**
 * A controller that contains the methods for the ServerScreen.fxml file
 * @author Casey, Karman
 * @version 1.0
 */
public class ServerContainer
{
    //FXML Attributes
    @FXML
    private Text status;
    
    //Attributes
    Thread t1 = new Thread(new Server());
    
    //Methods
    
    /**
     * Starts the server up
     * @param e event that triggered the method
     */
    @FXML
    public void startServer(ActionEvent e)
    {
        
        t1.start();
        status.setText("Server up");
    }
    
    /**
     * Shuts the server down
     * @param e event that triggered the method
     * @throws IOException
     */
    @FXML
    public void stopServer(ActionEvent e) throws IOException
    {
        Server.stopServer();
        status.setText("Server down");
    }
}
