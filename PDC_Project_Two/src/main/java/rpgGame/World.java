package rpgGame;

import java.io.IOException;
import java.util.Scanner;
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
    private Scanner scanner;
    private boolean newGame;
    private boolean quitGame;

    public World()
    {
        player = Player.get();
        arena = new Arena();
        scanner = new Scanner(System.in);

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
        
        
        FileManager.get().initialiseItemData();
        System.out.println("==============================================================================");
        System.out.println("                                               GLADIATOR GAME");
        System.out.println("==============================================================================");

//        System.out.println("1. New game");
//        System.out.println("2. Load game");
//        System.out.println("3. Quit");
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
                            createPlayer();
                            startGame();
                            validInput = true;
                            break;
                        case 2:
                            if(!FileManager.get().loadGame())
                            {
                                System.out.println("Creating a new game...");
                                createPlayer();
                                startGame();
                            }
                            else
                            {
                                newGame = false;
                                startGame();
                            }
                            validInput = true;
                            break;
                        case 3:
                            quitGame = true;
                            validInput = true;
                            System.out.println("Quitting");
                            System.exit(0);
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
        if (newGame)
        {
            printIntroduction();
        }
        
        FileManager.get().startGame();
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
                                    arena.arenaMain();
                                    validInput = true;
                                    break;
                                case 2:
                                    // enter shop
                                    shop.shopMain();
                                    validInput = true;
                                    break;
                                case 3:
                                    // manage inventory
                                    Player.get().manageInventory();
                                    validInput = true;
                                    break;
                                case 4:
                                    // view stats
                                    Player.get().manageStats();
                                    validInput = true;
                                    break;
                                case 5:
                                    // save game and quit
                                    Engine.get().shutdown();
                                    validInput = true;
                                    break;
                                default:
                                    System.out.println("Invalid option");
                                    getButtonInputStream().clear();
                                    break;
                            }
                            World.get().getButtonInputStream().clear();
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

    /**
     * @return the scanner
     */
    public Scanner getScanner()
    {
        return scanner;
    }
    
    public ButtonInputQueue getButtonInputStream()
    {
        return Engine.get().getGUI().getButtonInputs();
    }
    
    public void createPlayer()
    {
        String playerName = "";
        while (playerName.equals(""))
        {
            playerName = Engine.get().getGUI().createPlayer();
            if (playerName.equals(""))
            {
                System.out.println("Please enter a name");
            }
        }
        
        Player.set(new Player(playerName, 5, 5, 5, 5));
    }
}
