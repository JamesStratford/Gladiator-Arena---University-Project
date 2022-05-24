package rpgGame.CombatObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import rpgGame.Engine;
import rpgGame.Items.Armour;
import rpgGame.Items.Item;
import rpgGame.Items.Weapon;
import rpgGame.World;

/**
 *
 * @author James Stratford 21129223
 */
public class Player extends CombatObject
{

    private static Player _instance = null;
    private String name;
    private int level;
    private int xp;
    private int xpToNextLevel;
    private int availableSkillPoints;
    private LinkedHashSet<Enemy> defeatedEnemies;

    /**
     * New game player constructor
     */
    public Player(String name, int strength, int dexterity, int vitality, int stamina)
    {
        super(strength, dexterity, vitality, stamina);
        this.name = name;
        level = 1;
        xp = 0;
        xpToNextLevel = 100;
        availableSkillPoints = 0;
        defeatedEnemies = new LinkedHashSet<>();
    }

    /**
     * Player constructor for loadGame
     *
     * @param name
     * @param level
     * @param xp
     * @param xpToNextLevel
     * @param availableSkillPoints
     * @param strength
     * @param dexterity
     * @param vitality
     * @param stamina
     * @param coins
     * @param inventory
     * @param equiptWeapon
     * @param equiptArmour
     */
    public Player(String name, int level, int xp, int xpToNextLevel, int availableSkillPoints, int strength,
            int dexterity, int vitality, int stamina, int coins, ArrayList<Integer> inventory,
            int equiptWeapon, int equiptArmour)
    {
        super(strength, dexterity, vitality, stamina);
        this.name = name;
        this.level = level;
        this.xp = xp;
        this.xpToNextLevel = xpToNextLevel;
        this.availableSkillPoints = availableSkillPoints;
        this.inventory.addCoins(coins);
        inventory.forEach(item ->
        {
            if (item != -1)
            {
                this.inventory.addItem(Item.items.get(item));
            }
        });
        if (equiptWeapon != -1)
        {
            this.wieldedWeapon = (Weapon) Item.items.get(equiptWeapon);
        }
        if (equiptArmour != -1)
        {
            this.wieldedArmour = (Armour) Item.items.get(equiptArmour);
        }

        defeatedEnemies = new LinkedHashSet<>();
    }

    /**
     * @return _instance
     */
    public static Player get()
    {
        if (_instance == null)
        {
            _instance = new Player("Bob", 5, 5, 5, 5);
        }
        return _instance;
    }

    /**
     * set instance to be a specific player object.Primarily used when loading a
     * save file.
     *
     * @param player
     */
    public static void set(Player player)
    {
        _instance = player;
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return XP
     */
    public int getXp()
    {
        return xp;
    }

    /**
     * @return xpToNextLevel
     */
    public int getXpToNextLevel()
    {
        return xpToNextLevel;
    }

    /**
     * @return level
     */
    public int getLevel()
    {
        return this.level;
    }

    /**
     * @return availableSkillPoints
     */
    public int getAvailableSkillPoints()
    {
        return availableSkillPoints;
    }

    /**
     * Adds xp to player character and levels player up if over the threshold,
     * then doubles how much xp is needed for the next level.
     *
     * @param xp
     */
    public void addXp(int xp)
    {
        this.xp += xp;

        if (this.xp >= xpToNextLevel)
        {
            levelUp();
            this.xp -= xpToNextLevel;
            xpToNextLevel *= 2;
        }
    }

    /**
     * levels the player character up and awards 10 skill points.
     */
    public void levelUp()
    {
        level++;
        availableSkillPoints += 10;
        System.out.println("Level up! You are now level " + level + "!");
        System.out.println("You have " + availableSkillPoints + " skill points available to spend.");
    }

    /**
     * @return String name
     */
    @Override
    public String toString()
    {
        return this.name;
    }

    /**
     * @return the defeatedEnemies
     */
    public LinkedHashSet<Enemy> getDefeatedEnemies()
    {
        return defeatedEnemies;
    }

    /**
     * manage player inventory, assign weapons and armour to be equipt.
     */
    public boolean manageInventory() throws IOException
    {
        Engine.get().getGUI().manageInventory();
        viewInventory();
        System.out.println("Enter the corresponding integer to equip / dequip the item.");
        boolean validInput = false;
        boolean back = false;
        boolean mainMenu = false;
        while (!back)
        {
            int input = -1;

            while (!validInput)
            {
                if (!World.get().getButtonInputStream().isEmpty())
                {
                    switch (World.get().getButtonInputStream().read())
                    {
                        case 1:
                            // Choose an item to equip
                            input = Engine.get().getGUI().integerQuery();
                            switch (input)
                            {
                                case -1:
                                    validInput = false;
                                    break;
                                case 0:
                                    validInput = false;
                                    break;
                                default:
                                    if (input <= Player.get().getInventory().getItems().size() && input > 0)
                                    {
                                        validInput = true;
                                    } else
                                    {
                                        System.out.println("Please enter a corresponding number to the item.");
                                        validInput = false;
                                    }
                                    break;
                            }
                            break;
                        case 2:
                            validInput = true;
                            back = true;
                            break;
                        case 6:
                            mainMenu = true;
                            return mainMenu;
                    }
                }
                try
                {
                    Thread.sleep(50);
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (validInput && back)
            {
                break;
            } else if (input <= this.getInventory().getItems().size())
            {
                if (this.inventory.getItems().get(input - 1).getClass() == Weapon.class)
                {
                    if (this.wieldedWeapon != null)
                    {
                        if (!this.wieldedWeapon.equals((Weapon) this.inventory.getItems().get(input - 1)))
                        {
                            this.wieldedWeapon = (Weapon) this.inventory.getItems().get(input - 1);
                        } else
                        {
                            this.wieldedWeapon = null;
                        }
                    } else
                    {
                        this.wieldedWeapon = (Weapon) this.inventory.getItems().get(input - 1);
                    }
                } else if (this.inventory.getItems().get(input - 1).getClass() == Armour.class)
                {
                    if (this.wieldedArmour != null)
                    {
                        if (!this.wieldedArmour.equals((Armour) this.inventory.getItems().get(input - 1)))
                        {
                            this.wieldedArmour = (Armour) this.inventory.getItems().get(input - 1);
                        } else
                        {
                            this.wieldedArmour = null;
                        }
                    } else
                    {
                        this.wieldedArmour = (Armour) this.inventory.getItems().get(input - 1);
                    }
                }
                viewInventory();
                try
                {
                    Thread.sleep(500);
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
                validInput = false;
            }
        }
        return mainMenu;
    }

    /**
     * prints stats
     */
    private void printStats()
    {
        System.out.println(this.name + " XP:" + this.xp + "/" + this.xpToNextLevel);
        System.out.println("Strength: " + this.stats.strength);
        System.out.println("Dexterity: " + this.stats.dexterity);
        System.out.println("Vitality: " + this.stats.vitality);
        System.out.println("Stamina: " + this.stats.stamina);
    }

    /**
     * allocates skillpoints to specific stats
     */
    private boolean allocateStats() throws IOException
    {
        // gui alloc Stats
        System.out.println("Which stat would you like to add 1 point to?");
        System.out.println("1. Strength");
        System.out.println("2. Dexterity");
        System.out.println("3. Vitality");
        System.out.println("4. Stamina");
        System.out.println("0. Back");

        boolean mainMenu = false;
        boolean validInput = false;
        while (!validInput)
        {

            int input = -1;
            if (!World.get().getButtonInputStream().isEmpty())
            {
                switch (World.get().getButtonInputStream().read())
                {
                    case 1:
                        this.stats.strength++;
                        validInput = true;
                        break;
                    case 2:
                        this.stats.dexterity++;
                        validInput = true;
                        break;
                    case 3:
                        this.stats.vitality++;
                        validInput = true;
                        break;
                    case 4:
                        this.stats.stamina++;
                        validInput = true;
                        break;
                    case 5:
                        return false;
                    case 6:
                        mainMenu = true;
                        return mainMenu;
                    default:
                        System.out.println("Invalid option");
                        return false;
                }
            }
            try
            {
                Thread.sleep(50);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        availableSkillPoints--;

        return mainMenu;
    }

    /**
     * manage player skill point allocations
     * @return if game is to go the main menu
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public boolean manageStats() throws InterruptedException, IOException
    {
        Engine.get().getGUI().manageStats();
        World.get().getButtonInputStream().clear();
        printStats();
        System.out.println("You have " + this.availableSkillPoints + " stat points available for allocation.");

        boolean mainMenu = false;
        if (this.availableSkillPoints > 0)
        {
            System.out.println("Would you like to allocate them now?");

            System.out.println("1. Yes");
            System.out.println("2. Back");

            boolean validInput = false;
            while (!validInput)
            {
                if (!World.get().getButtonInputStream().isEmpty())
                {
                    switch (World.get().getButtonInputStream().read())
                    {
                        case 1:
                            //alloc stats
                            if (availableSkillPoints > 0)
                            {
                                Engine.get().getGUI().allocStats();
                                
                                if (allocateStats())
                                {
                                    mainMenu = true;
                                    return mainMenu;
                                }
                                Engine.get().getGUI().manageStats();
                                printStats();
                                System.out.println("You have " + this.availableSkillPoints + " stat points available for allocation.");
                                System.out.println("Would you like to allocate them now?");
                            }
                            break;
                        case 2:
                            // back
                            validInput = true;
                            break;
                        case 6:
                            mainMenu = true;
                            return mainMenu;
                        default:
                            System.out.println("Invalid option");
                            break;
                    }
                }
                Thread.sleep(50);
            }
        } else
        {
            System.out.println("Returning to menu");
            Thread.sleep(500);
        }
        
        return mainMenu;
    }
}
