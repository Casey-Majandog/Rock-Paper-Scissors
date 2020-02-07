package utility;

import java.io.Serializable;

/**
 * Class Description: The elements of the game that are sent
 * across the network
 * @author Casey, Karman
 * @version 1.0
 */
public class Game implements Serializable
{
    
    //Constants
    private static final long serialVersionUID = 7660195669243432591L;
    
    //Attributes
    private String type;
    
    //Constructors
    
    /**
     * Constructor that takes in the players choice
     * @param type players choice in game
     */
    public Game(String type)
    {
        super();
        this.type = type;
    }

    /**
     * Default constructor
     */
    public Game() {
    	type = "";
    }
    
    
    //Methods
    
    /*
     * Gets the type of rock, paper or scissors
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets the players choice
     * @param type The options rock, paper or scissors
     */
    public void setType(String type)
    {
        this.type = type;
    }
    
    
    //Operational Methods
    
    /**
     * Prints out the type
     */
    @Override
    public String toString()
    {
        return type;
    }
    
}
