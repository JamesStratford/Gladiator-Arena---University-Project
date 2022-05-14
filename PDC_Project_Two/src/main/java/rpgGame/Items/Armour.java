package rpgGame.Items;

import java.util.HashSet;


/**
 *
 * @author James Stratford 21129223
 */
public class Armour extends Item
{
    public static HashSet<Armour> armours;  // master list
    private int block;
    private float blockPercentage;

    public Armour(int itemId, String itemName, int coinValue, double weight, int block, float blockPercentage)
    {
        super(itemId, itemName, coinValue, weight);
        this.block = block;
        this.blockPercentage = blockPercentage;
    }
    
    /**
     *  special constructor used to create Armour objects based on armour in the masterlist
     * @param itemId 
     */
    private Armour(int itemId)
    {
        super(itemId, Item.items.get(itemId).itemName, Item.items.get(itemId).coinValue, Item.items.get(itemId).weight);
        this.block = ((Armour) Item.items.get(itemId)).getBlock();
        this.blockPercentage = ((Armour) Item.items.get(itemId)).getBlockPercentage();
    }

    /**
     *  wrapper for creating armour object from the Armour master list
     * @param itemId
     * @return new Armour
     */
    public static Armour createArmour(int itemId)
    {
        return new Armour(itemId);
    }

    /**
     * @return the block
     */
    public int getBlock()
    {
        return block;
    }

    /**
     * @return the blockPercentage
     */
    public float getBlockPercentage()
    {
        return blockPercentage;
    }
}
