package utility;

import java.io.Serializable;

public class Game implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 7660195669243432591L;
    private String type;

    public Game(String type)
    {
        super();
        this.type = type;
    }

    public Game() {
    	type = "";
    }
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
    
    @Override
    public String toString()
    {
        return type;
    }
    
}
