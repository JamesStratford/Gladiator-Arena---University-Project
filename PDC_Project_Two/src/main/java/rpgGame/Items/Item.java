package rpgGame.Items;

import java.util.HashMap;

/**
 *
 * @author James Stratford 21129223
 */
public abstract class Item
{
    public static HashMap<Integer, Item> items; // master list
    protected int itemId;
    protected String itemName;
    protected int coinValue;
    protected double weight;

    public Item(int itemId, String itemName, int coinValue, double weight)
    {
        this.itemId = itemId;
        this.itemName = itemName;
        this.coinValue = coinValue;
        this.weight = weight;
    }
    
    /**
     * 
     * @return itemId
     */
    public int getItemId()
    {
        return this.itemId;
    }
    
    /**
     * 
     * @return itemName
     */
    public String getName()
    {
        return this.itemName;
    }
    
    /**
     *  
     * @return coinValue
     */
    public int getValue()
    {
        return this.coinValue;
    }

    /**
     * 
     * @return weight
     */
    public double getWeight()
    {
        return weight;
    }
    
    /**
     * 
     * @return itemName
     */
    @Override
    public String toString()
    {
        return "" + this.itemName;
    }
}
