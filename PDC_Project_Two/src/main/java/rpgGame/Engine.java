package rpgGame;

import java.io.IOException;


/**
 *
 * @author James Stratford 21129223
 */
public class Engine
{
    private static Engine _instance = null;
    private World world;
    
    public Engine() throws IOException
    {
        world = World.get();
    }
    
    /*
    *   @returns _instance
    */
    public static Engine get() throws IOException
    {
        if (_instance == null)
        {
            _instance = new Engine();
        }
        return _instance;
    }
    
    /*
    *   shutsdown the program
    */
    public void shutdown() throws IOException
    {
        FileManager.get().saveGame();
        World.get().setQuit(true);
        System.out.println("Quitting");
        System.exit(0);
    }
    
    /*
    *   Start of program
    */
    public static void main(String[] args) throws IOException
    {
        Engine.get().world.mainMenu();
        
        System.out.println("Quitting.");
        System.exit(0);
    }
}
