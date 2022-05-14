package rpgGame.Items;

/**
 *
 * @author James Stratford 21129223
 */
public class CoinPurse extends Item
{
    private int balance;

    public CoinPurse(int itemId, String itemName, int coinValue, int balance)
    {
        super(itemId, itemName, coinValue, 0.0);
        this.balance = balance;
    }
    
    /**
     *  adds coins to coinpurse
     * @param amount
     * @return true
     */
    public boolean addCoins(int amount)
    {
        balance += amount;
        return true;
    }
    
    /**
     *  removes coins from coinpurse
     * @param amount
     * @return 
     */
    public boolean removeCoins(int amount)
    {
        int newBalance = balance - amount;
        
        if (newBalance < 0)
            return false;
        
        balance -= amount;
        return true;
    }
    
    /**
     * 
     * @return coins
     */
    public int getCoins()
    {
        return this.balance;
    }
}
