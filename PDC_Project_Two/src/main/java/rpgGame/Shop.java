package rpgGame;

import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        System.out.println("What would you like to do?");
    }
    
    /**
     * 
     * @return if to return to the mainMenu
     */
    private boolean queryBuyPanel()
    {
        Engine.get().getGUI().shopBuy();
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
                            // Buy an item:
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
                                    if (input <= this.availableItems.size() && input > 0)
                                        validInput = true;
                                    else
                                    {
                                        System.out.println("Please enter a corresponding number to the item.");
                                        validInput = false;
                                    }
                                    break;
                            }
                            break;
                        case 2:
                            input = 0;
                            validInput = true;
                            break;
                        case 6:
                            mainMenu = true;
                            return mainMenu;
                        default:
                            validInput = false;
                            break;
                    }
                if (input != 0 && validInput)
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
                                try
                                {
                                    Thread.sleep(500);
                                } catch (InterruptedException ex)
                                {
                                    Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else
                            {
                                System.out.println("Item is too heavy. Sell something first!");
                                try
                                {
                                    Thread.sleep(500);
                                } catch (InterruptedException ex)
                                {
                                    Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                        else
                        {
                            System.out.println("You do not have enough coins!");
                            try
                            {
                                Thread.sleep(500);
                            } catch (InterruptedException ex)
                            {
                                Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }

            try
            {
                Thread.sleep(50);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mainMenu;
    }
    
    /**
     * 
     * @return if game is to go to the main menu
     */
    private boolean querySellPanel()
    {
        Engine.get().getGUI().shopSell();
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
                        // Sell an item:
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
                        input = 0;
                        validInput = true;
                        break;
                    case 6:
                        mainMenu = true;
                        return mainMenu;
                    default:
                        validInput = false;
                        break;
                }
                if (input != 0 && validInput)
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
                    }
                }
            }
            try
            {
                Thread.sleep(50);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return mainMenu;
    }
    
    /**
     *  entrance method to run shop
     * @return if game is to go to mainMenu or not
     */
    public boolean shopMain()
    {
        Engine.get().getGUI().shop();
        World.get().getButtonInputStream().clear();
        printShopEntry();
        
        boolean mainMenu = false;
        boolean validInput = false;
        while (!validInput)
        {   
            if (!World.get().getButtonInputStream().isEmpty())
            {
                switch (World.get().getButtonInputStream().read())
                {
                    case 1:
                        //buy
                        printAvailableItems();
                        //System.out.println("Enter '0' to cancel.");
                        // query buy
                        if (queryBuyPanel())
                        {
                            mainMenu = true;
                            return mainMenu;
                        }
                        validInput = true;
                        break;
                    case 2:
                        //sell
                        printPlayerAvailableItems();
                        // query sell
                        //System.out.println("Enter '0' to cancel.");
                        if (Player.get().getInventory().getItems().size() > 0)
                        {
                            if (querySellPanel())
                            {
                                mainMenu = true;
                                return mainMenu;
                            }
                        }
                        else
                            System.out.println("You have no items to sell.");
                        validInput = true;
                        break;
                    case 3:
                        validInput = true;
                        break;
                    case 6:
                        mainMenu = true;
                        return mainMenu;
                }
                World.get().getButtonInputStream().clear();
            }
            try
            {
                Thread.sleep(50);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return mainMenu;
    }
}
