package rpgGame.Items;

import java.util.HashSet;

/**
 *
 * @author James Stratford 21129223
 */
public class Weapon extends Item
{
    public static HashSet<Weapon> weapons;  // master list

    private int meanDamage;

    public Weapon(int itemId,  String itemName, int coinValue, double weight, int meanDamage)
    {
        super(itemId, itemName, coinValue, weight);
        this.meanDamage = meanDamage;
    }
    
    /**
     *  special constructor for creation of weapons based off of the master list
     * @param itemId 
     */
    private Weapon(int itemId)
    {
        super(itemId, Item.items.get(itemId).itemName, Item.items.get(itemId).coinValue, Item.items.get(itemId).weight);
        this.meanDamage = ((Weapon)Item.items.get(itemId)).getMeanDamage();
    }
    
    /**
     * wrapper for creation of weapons based on the master list
     * @param itemId
     * @return new Weapon
     */
    public static Weapon createWeapon(int itemId)
    {
        return new Weapon(itemId);
    }
    
    /**
     * @return the meanDamage
     */
    public int getMeanDamage()
    {
        return meanDamage;
    }
}
