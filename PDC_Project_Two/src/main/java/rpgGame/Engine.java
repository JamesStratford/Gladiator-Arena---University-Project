package rpgGame;

import rpgGame.Database.DataManager;
import java.io.IOException;
import rpgGame.GUI.MainGUI;


/**
 *
 * @author James Stratford 21129223
 */
public class Engine
{
    private static Engine _instance = null;
    private World world;
    private MainGUI gui;
    private boolean gameStart;
    
    public Engine()
    {
        world = World.get();
        gui = new MainGUI();
        gui.setVisible(true);
        gameStart = false;
    }
    
    public void gameStarted()
    {
        gameStart = true;
    }
    
    /*
    *   @returns _instance
    */
    public static Engine get()
    {
        if (_instance == null)
        {
            _instance = new Engine();
        }
        return _instance;
    }
    
    public MainGUI getGUI()
    {
        return this.gui;
    }
    
    /*
    *   shutsdown the program
    */
    public void shutdown() throws IOException
    {
        if (gameStart)
            DataManager.get().saveGame();
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
