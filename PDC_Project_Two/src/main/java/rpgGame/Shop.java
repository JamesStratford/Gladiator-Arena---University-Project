package rpgGame;

import java.util.HashSet;
import java.util.Iterator;
import rpgGame.CombatObject.Player;
import rpgGame.Items.Armour;
import rpgGame.Items.Item;
import rpgGame.Items.Weapon;

/**
 *
 * @author James Stratford 21129223
 */
public class Shop
{
    private HashSet<Item> availableItems;
    private HashSet<Weapon> weapons;
    private HashSet<Armour> armour;
    
    public Shop()
    {
        availableItems = new HashSet<>();
        weapons = new HashSet<>();
        armour = new HashSet<>();
        availableItems.addAll(Item.items.values());
        for (Item x : Player.get().getInventory().getItems())
        {
            availableItems.remove(x);
        }
        
        for (Item x : availableItems)
        {
            if (x.getClass() == Weapon.class)
                weapons.add((Weapon) x);

            if (x.getClass() == Armour.class)
                armour.add((Armour) x);
        }
    }
    
    /**
     *  prints shop items
     */
    private void printAvailableItems()
    {
        System.out.println("Available Items:");
        int i = 1;
        for (Item x : availableItems)
        {
            if (x.getClass() == Weapon.class)
                System.out.println(i++ +". "+ x.getName() + ". Damage: " + ((Weapon)x).getMeanDamage() + ". Price: " + x.getValue());
            else if (x.getClass() == Armour.class)
                System.out.println(i++ +". "+x.getName() + ". Block: " + ((Armour)x).getBlock()+ ". Block Percentage: " + ((Armour)x).getBlockPercentage() +". Price: " + x.getValue());
        }
    }
    
    /**
     *  transfer items from shop to player inventory
     * @param x
     * @return 
     */
    private boolean playerBuyItem(Item x)
    {
        if (Player.get().getInventory().getCoins() >= x.getValue())
        {
            Player.get().getInventory().removeCoins(x.getValue());
            Player.get().getInventory().addItem(x);
            
            if (x.getClass() == Weapon.class)
                weapons.remove((Weapon)x);
            else if (x.getClass() == Armour.class)
                armour.remove((Armour)x);
            
            availableItems.remove(x);
            
            return true;
        }
        else
            return false;
    }
    
    /**
     *  prints players items available to sell
     */
    private void printPlayerAvailableItems()
    {
        System.out.println("Available Items:");
        int i = 1;
        for (Item x : Player.get().getInventory().getItems())
        {
            if (x.getClass() == Weapon.class)
            {
                System.out.println(i++ + ". " + x.getName() + ". Damage: " + ((Weapon) x).getMeanDamage() + ". Price: " + x.getValue());
            } else if (x.getClass() == Armour.class)
            {
                System.out.println(i++ + ". " + x.getName() + ". Block: " + ((Armour) x).getBlock() + ". Block Percentage: " + ((Armour) x).getBlockPercentage() + ". Price: " + x.getValue());
            }
        }
    }
    
    /**
     *  sells players item for coins
     * @param x
     * @return 
     */
    private boolean playerSellItem(Item x)
    {
        if (Player.get().getInventory().getItems().contains(x))
        {
            if (Player.get().getWieldedWeapon() == x)
                Player.get().setWieldedWeapon(null);
            
            if (Player.get().getWieldedArmour() == x)
                Player.get().setWieldedArmour(null);
            
            Player.get().getInventory().removeItem(x);
            if (x.getClass() == Weapon.class)
                weapons.add((Weapon)x);
            else if (x.getClass() == Armour.class)
                armour.add((Armour)x);
            
            availableItems.add(x);
            
            Player.get().getInventory().addCoins(x.getValue());
            
            return true;
        }
        else
            return false;
    }
    
    /**
     * shop introduction
     */
    private void printShopEntry()
    {
        System.out.println("\"Welcome to Gondeliar\'s emporium!, for all your weapon and armour needs!\"");
        System.out.println("You have " + Player.get().getInventory().getCoins() + " coins.");
        
        System.out.println("1. Buy");
        System.out.println("2. Sell");
        System.out.println("3. Back");
        System.out.println("4. Save and quit");
    }
    
    /**
     *  player queried for buy options
     */
    private void queryBuyPanel()
    {
        boolean validInput = false;
        while (!validInput)
        try
        {
            int input = World.get().getScanner().nextInt();
           
            if (input != 0)
            {
                Iterator<Item> it = availableItems.iterator();

                Item theItem = null;

                for (int i = 0; i < input; i++)
                {
                    theItem = it.next();
                }

                if (theItem != null)
                {
                    if (Player.get().getInventory().getCoins() >= theItem.getValue())
                    {
                        if (Player.get().getInventory().getAvailableWeight() >= theItem.getWeight())
                        {
                            playerBuyItem(theItem);
                            System.out.println("Successfully bought " + theItem.getName());
                            System.out.println();
                        }
                        else
                            System.out.println("Item is too heavy. Sell something first!");
                    }
                    else
                        System.out.println("You do not have enough coins!");
                    validInput = true;
                }
            }
            else
                validInput = true;
        } catch (Exception e) { 
            System.out.println("Invalid input");
            World.get().getScanner().nextLine();
        }
    }
    
    /**
     *  player queried for sell options
     */
    private void querySellPanel()
    {
        boolean validInput = false;
        while (!validInput)
        try
        {
            int input = World.get().getScanner().nextInt();

            if (input != 0)
            {
                Item theItem = null;
                if (input <= Player.get().getInventory().getItems().size())
                    theItem = Player.get().getInventory().getItems().get(input - 1);
                else
                    System.out.println("Invalid option");

                if (theItem != null)
                {
                    playerSellItem(theItem);
                    System.out.println("Successfully sold " + theItem.getName());
                    System.out.println();

                    validInput = true;
                }
            } else
            {
                validInput = true;
            }
        } catch (Exception e)
        {
            System.out.println("Invalid input");
        }
    }
    
    /**
     *  entrance method to run shop
     */
    public void shopMain()
    {
        printShopEntry();
        
        boolean validInput = false;
        while (!validInput)
        try
        {
            int input = World.get().getScanner().nextInt();
            
            switch (input)
            {
                case 1:
                    //buy
                    printAvailableItems();
                    System.out.println("Enter '0' to cancel.");
                    // query buy
                    queryBuyPanel();
                    validInput = true;
                    break;
                case 2:
                    //sell
                    printPlayerAvailableItems();
                    // query sell
                    System.out.println("Enter '0' to cancel.");
                    querySellPanel();
                    validInput = true;
                    break;
                case 3:
                    validInput = true;
                    break;
                case 4:
                    //save and quit
                    Engine.get().shutdown();
                    break;
                default:
                    System.out.println("Invalid selection, please try again.");
                    break;
            }
        } catch (Exception e) { System.out.println("Invalid input"); }
    }
}
