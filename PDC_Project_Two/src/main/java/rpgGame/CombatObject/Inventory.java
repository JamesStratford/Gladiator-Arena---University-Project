package rpgGame.CombatObject;

import java.util.ArrayList;
import rpgGame.Items.CoinPurse;
import rpgGame.Items.Item;

/**
 *
 * @author James Stratford 21129223
 */
public class Inventory
{
    private ArrayList<Item> items;
    private double currentWeight;
    private double maxWeight;
    private CoinPurse coins;
    
    public Inventory(double maxWeight)
    {
        items = new ArrayList<>();
        currentWeight = 0;
        this.maxWeight = maxWeight;
        coins = new CoinPurse(0, "Coin", 1, 0);
    }
    
    /**
     *  adds item to inventory
     * @param item
     * @return item is added
     */
    public boolean addItem(Item item)
    {
        double newWeight = currentWeight + item.getWeight();
        
        if (newWeight > maxWeight)
            return false;

        currentWeight = newWeight;
        items.add(item);
        return true;
    }
    
    /**
     *  removes item from inventory
     * @param item
     * @return item is removed
     */
    public boolean removeItem(Item item)
    {
        if (items.contains(item))
            currentWeight -= item.getWeight();
        
        return items.remove(item);
    }
    
    /**
     *  adds coins to CoinPurse
     * @param amount
     * @return coins is added
     */
    public boolean addCoins(int amount)
    {
        return coins.addCoins(amount);
    }
    
    /**
     *  removes coins from CoinPurse
     * @param amount
     * @return coins is removed
     */
    public boolean removeCoins(int amount)
    {
        return coins.removeCoins(amount);
    }
    
    /**
     * 
     * @return coins
     */
    public int getCoins()
    {
        return this.coins.getCoins();
    }
    
    /**
     * 
     * @return items
     */
    public ArrayList<Item> getItems()
    {
        return this.items;
    }
    
    /**
     *  returns available weight for use in the shop. This allows purchasing weapons and armour given the weight
     * @return availableWeight
     */
    public double getAvailableWeight()
    {
        return this.maxWeight - currentWeight;
    }
    
    /**
     * 
     * @return string of weight capacity
     */
    public String getWeightCapacityString()
    {
        return "" + this.currentWeight +"/"+ this.maxWeight;
    }
}
