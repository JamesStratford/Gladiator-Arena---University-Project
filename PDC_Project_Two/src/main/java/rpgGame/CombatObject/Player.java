package rpgGame.CombatObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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
    *   New game player constructor
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
    *   Player constructor for loadGame
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
    *   @return _instance
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
    *   set instance to be a specific player object.Primarily used when loading a save file.
     * @param player
    */
    public static void set(Player player)
    {
        _instance = player;
    }

    /**
    *   @return name
    */
    public String getName()
    {
        return name;
    }

    /**
    *   @return XP
    */
    public int getXp()
    {
        return xp;
    }

    /**
    *   @return xpToNextLevel
    */
    public int getXpToNextLevel()
    {
        return xpToNextLevel;
    }

    /**
    *   @return level
    */
    public int getLevel()
    {
        return this.level;
    }

    /**
    *   @return availableSkillPoints
    */
    public int getAvailableSkillPoints()
    {
        return availableSkillPoints;
    }
    
    /**
    *   Adds xp to player character and levels player up if over the threshold, then doubles how much xp is
    *   needed for the next level.
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
    *   levels the player character up and awards 10 skill points.
    */
    public void levelUp()
    {
        level++;
        availableSkillPoints += 10;
        System.out.println("Level up! You are now level " + level + "!");
        System.out.println("You have " + availableSkillPoints + " skill points available to spend.");
    }

    /**
    *   @return String name
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
    *   manage player inventory, assign weapons and armour to be equipt.
    */
    public void manageInventory() throws IOException
    {
        viewInventory();
        System.out.println("Enter the corresponding integer to equip / dequip the item OR enter '0' to go back.");
        boolean back = false;
        while (!back)
        {
            try
            {
                int input = World.get().getScanner().nextInt();

                if (input == 0)
                {
                    back = true;
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
                    System.out.println("Enter the corresponding integer to equip / dequip the item OR enter '0' to go back.");
                }
            } catch (Exception e)
            {
                System.out.println("Invalid input");
                World.get().getScanner().nextLine();
            }
        }
    }
    
    /**
    *   prints stats
    */
    private void printStats()
    {
        System.out.println(this.name + " XP:" + this.xp + "/" + this.xpToNextLevel);
        System.out.println("Strength: " +this.stats.strength);
        System.out.println("Dexterity: " + this.stats.dexterity);
        System.out.println("Vitality: " + this.stats.vitality);
        System.out.println("Stamina: " + this.stats.stamina);
    }
    
    /**
    *   allocates skillpoints to specific stats
    */
    private boolean allocateStats() throws IOException
    {
        System.out.println("Which stat would you like to add 1 point to?");
        System.out.println("1. Strength");
        System.out.println("2. Dexterity");
        System.out.println("3. Vitality");
        System.out.println("4. Stamina");
        System.out.println("0. Back");

        boolean validInput = false;
        while(!validInput)
        {
            try
            {

                int input = World.get().getScanner().nextInt();

                switch (input)
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
                    case 0:
                        return false;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } catch(Exception e) { 
                System.out.println("Invalid input");
                World.get().getScanner().nextLine();
            }
        }
        availableSkillPoints--;
        
        return true;
    }

    /**
    *   manage player skill point allocations
    */
    public void manageStats() throws InterruptedException, IOException
    {
        printStats();
        System.out.println("You have " + this.availableSkillPoints + " stat points available for allocation.");
        
        if (this.availableSkillPoints > 0)
        {
            System.out.println("Would you like to allocate them now?");
            
            System.out.println("1. Yes");
            System.out.println("2. Back");
            
            boolean validInput = false;
            while (!validInput)
            try
            {
                int input = World.get().getScanner().nextInt();
                
                switch (input)
                {
                    case 1:
                        //alloc stats
                        if (availableSkillPoints > 0)
                        {
                            allocateStats();
                            printStats();
                            System.out.println("You have " + this.availableSkillPoints + " stat points available for allocation.");
                            System.out.println("Would you like to allocate them now?");
                            System.out.println("1. Yes");
                            System.out.println("2. Back");
                        }
                        break;
                    case 2:
                        // back
                        validInput = true;
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } catch(Exception e) { 
                System.out.println("Invalid input");
                World.get().getScanner().nextLine();
            }
        }
        else
        {
            System.out.println("Returning to menu");
            Thread.sleep(500);
        }
    }
}
