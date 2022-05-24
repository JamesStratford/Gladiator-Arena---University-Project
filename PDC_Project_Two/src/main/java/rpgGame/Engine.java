package rpgGame;

import rpgGame.Database.DataManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rpgGame.GUI.MainGUI;


/**
 *
 * @author James Stratford 21129223
 */
public class Engine
{
    private static Engine _instance = null;
    private boolean exit;
    private World world;
    private MainGUI gui;
    private boolean gameStart;
    
    public Engine()
    {
        exit = true;
        world = World.get();
        gui = new MainGUI();
        gui.setVisible(true);
        gameStart = false;
    }
    
    public void setExit(boolean exit)
    {
        this.exit = exit;
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
    
    public void mainMenu()
    {
        try
        {
            setExit(false);
            DataManager.get().saveGame();
            World.resetWorld();
        } catch (IOException ex)
        {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        do
        {
            Engine.get().world.mainMenu();
        } while (!Engine.get().exit);
        
        System.out.println("Quitting.");
        System.exit(0);
    }
}
