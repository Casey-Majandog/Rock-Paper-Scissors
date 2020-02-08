package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * An interface that allows the user to start and stop the server for the game
 * @author Casey, Karman
 * @version 1.0
 */
public class ServerGUI extends Application
{
    //Attributes
    Scene serverScene;
    FXMLLoader loader;
    
    //Methods
    public static void main(String[] args)
    {
        launch(args);
        
    }

    /**
     * Loads up the server screen
     * @param primaryStage Stage where the GUI will be set in
     */
    @Override
    public void start(Stage primaryStage) {
        try {

            loader = new FXMLLoader(getClass().getResource("ServerScreen.fxml"));

            Parent root = loader.load();

            serverScene = new Scene(root, 350, 200);
            primaryStage.setScene(serverScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
}
