package rpgGame;

import java.io.FileNotFoundException;
import rpgGame.Database.DataManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rpgGame.CombatObject.Player;
import utility.ButtonInputQueue;

/**
 *
 * @author James Stratford 21129223
 */
public class World
{
    private static World _instance = null;
    private Player player;
    private Arena arena;
    private Shop shop;
    private boolean newGame;
    private boolean quitGame;

    public World()
    {
        player = Player.get();
        arena = new Arena();

        newGame = true;
        quitGame = false;
    }

    /**
     *
     * @return _instance
     */
    public static World get()
    {
        if (_instance == null)
        {
            _instance = new World();
        }
        return _instance;
    }
    
    /**
     * Resets the World
     */
    public static void resetWorld()
    {
        _instance = null;
    }
    
    /**
     * 
     * @return Returns the arena.
     */
    public Arena getArena()
    {
        return arena;
    }

    /**
     *
     * @return quitGame
     */
    public boolean isQuit()
    {
        return quitGame;
    }
    
    /**
     *
     * @param status
     */
    public void setQuit(boolean status)
    {
        this.quitGame = status;
    }
    
    /**
     * @param newGame the newGame to set
     */
    public void setNewGame(boolean newGame)
    {
        this.newGame = newGame;
    }
    
    /**
     *  Main menu
     * @throws IOException
     */
    public void mainMenu() throws IOException
    {
        Engine.get().getGUI().mainMenu();
        
        try
        {
            DataManager.get().initialiseItemData();
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("=====================================================================");
        System.out.println("                                               GLADIATOR GAME");
        System.out.println("=====================================================================");
        System.out.println("Select an option at the bottom to begin.");

            boolean validInput = false;
            while (!validInput)
            {
                if (!getButtonInputStream().isEmpty())
                {
                    switch (getButtonInputStream().read())
                    {
                        case 1:
                            // player creation
                            if (createPlayer())
                            {
                                startGame();
                                validInput = true;
                            }
                            break;
                        case 2:
                            if (Engine.get().getGUI().loadGameDatabase())
                            {
                                startGame();
                                validInput = true;
                            }
                            break;
                        case 3:
                            quitGame = true;
                            validInput = true;
                            System.out.println("Quitting");
                            System.exit(0);
                            break;
                        case 6:
                            // import save file
                            String path = Engine.get().getGUI().getStringPrompt("Enter file path:");
                            if (DataManager.get().importGame(path))
                            {
                                validInput = true;
                                startGame();
                            }
                            break;
                        default:
                            System.out.println("Invalid option");
                            break;
                    }
                }
                try
                {
                    Thread.sleep(20);
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        quitGame = true;
    }

    /**
     *  prints introduction
     */
    public void printIntroduction()
    {
        System.out.println("You have awoken in a mysterious city and remember nothing of your past.\n"
                + "Having no food and shelter, you decided to look for a job. Upon passing a sign, your attention is caught: \n\n\'The Arena! Battle opponents for coins!\'\n\n"
                + "You decide to investigate...");
        try
        {
            Thread.sleep(500);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *  starts the game
     * @throws IOException 
     */
    public void startGame() throws IOException
    {
        Engine.get().gameStarted();
        quitGame = false;
        if (newGame)
        {
            printIntroduction();
        }
        
        DataManager.get().startGame();
        shop = new Shop();
        

        while (!quitGame)
        {
            getButtonInputStream().clear();
            try
            {
                Thread.sleep(500);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
            }
            Engine.get().getGUI().townCenter();
            player = Player.get();
            if (player.getAlive())
            {
                System.out.println("You are waiting in the town center, contemplating your next move.");

                try
                {
                    boolean validInput = false;
                    while (!validInput)
                    {
                        if (!getButtonInputStream().isEmpty())
                        {
                            switch (getButtonInputStream().read())
                            {
                                case 1:
                                    // enter arena
                                    if (arena.arenaMain())
                                    {
                                        Engine.get().mainMenu();
                                        quitGame = true;
                                    }
                                    validInput = true;
                                    break;
                                case 2:
                                    // enter shop
                                    if (shop.shopMain())
                                    {
                                        Engine.get().mainMenu();
                                        quitGame = true;                                        
                                    }
                                    validInput = true;
                                    break;
                                case 3:
                                    // manage inventory
                                    if (Player.get().manageInventory())
                                    {
                                        Engine.get().mainMenu();
                                        quitGame = true;
                                    }
                                    validInput = true;
                                    break;
                                case 4:
                                    // view stats
                                    if (Player.get().manageStats())
                                    {
                                        Engine.get().mainMenu();
                                        quitGame = true;
                                    }
                                    validInput = true;
                                    break;
                                case 5:
                                    // export save
                                    String path = Engine.get().getGUI().getStringPrompt("Enter file path:");
                                    if (DataManager.get().exportGame(path))
                                    {
                                        validInput = true;
                                        System.out.println("Successfully exported save file.");
                                    } else
                                    {
                                        System.out.println("Unable to export save file.");
                                    }
                                    break;
                                case 6:
                                    // main menu
                                    Engine.get().mainMenu();
                                    validInput = true;
                                    quitGame = true;
                                    break;
                                default:
                                    System.out.println("Invalid option");
                                    getButtonInputStream().clear();
                                    break;
                            }
                            getButtonInputStream().clear();
                        }
                        Thread.sleep(20);
                    }
                } catch (InterruptedException e)
                { }
            } else
            {
                Engine.get().getGUI().deadScreen();
                System.out.println("Game over man! Game over!");
                System.out.println("Slain enemies:");
                System.out.println(player.getDefeatedEnemies());
                quitGame = true;
                try
                {
                    int time = 5;
                    while (time >= 0)
                    {
                        System.out.println("Game will self-destruct in " + time--);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public ButtonInputQueue getButtonInputStream()
    {
        return Engine.get().getGUI().getButtonInputs();
    }
    
    public boolean createPlayer()
    {
        String playerName = null;
        while (playerName == null || playerName.equals(""))
        {
            playerName = Engine.get().getGUI().getStringPrompt("Enter a name:");
            if (playerName == null)
            {
                return false;
            }
            if (playerName.equals(""))
            {
                System.out.println("Please enter a name");
            }
        }
        
        Player.set(new Player(playerName, 5, 5, 5, 5));
        return true;
    }
}
