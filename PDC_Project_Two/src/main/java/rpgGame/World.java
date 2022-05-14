package rpgGame;

import java.io.IOException;
import java.util.Scanner;
import rpgGame.CombatObject.Player;

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
     *  Main menu
     * @throws IOException
     */
    public void mainMenu() throws IOException
    {
        FileManager.get().initialiseItemData();
        System.out.println("==============================================================================");
        System.out.println("                              GLADIATOR GAME");
        System.out.println("==============================================================================");

        System.out.println("1. New game");
        System.out.println("2. Load game");
        System.out.println("3. Quit");

        try
        {
            boolean validInput = false;
            while (!validInput)
            {
                switch (getScanner().nextInt())
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
        } catch (Exception e)
        {
            System.out.println("Invalid input");
            getScanner().next();
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
            player = Player.get();
            if (player.getAlive())
            {
                System.out.println(""
                        + "1. Fight in the arena\n"
                        + "2. Visit the shop\n"
                        + "3. Manage inventory\n"
                        + "4. View stats and assign skill points\n"
                        + "5. Save and quit");

                try
                {
                    boolean validInput = false;
                    while (!validInput)
                    {
                        switch (getScanner().nextInt())
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
                                break;
                        }
                    }
                } catch (Exception e)
                {
                    System.out.println("Invalid input DEBUG: startGame: " + e.toString());
                    getScanner().next();
                }
            } else
            {
                System.out.println("Game over man! Game over!");
                System.out.println("Slain enemies:");
                System.out.println(player.getDefeatedEnemies());
                quitGame = true;
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
    
    public void createPlayer()
    {
        String playerName;
        System.out.println("What is your name? (A-z and one word only)");
        
        while (!getScanner().hasNext("[A-Za-z]+"))
        {
            System.out.println("Please enter only letters!");
            getScanner().next();
        }
        playerName = getScanner().next();
        getScanner().nextLine();
        
        Player.set(new Player(playerName, 5, 5, 5, 5));
    }
}
