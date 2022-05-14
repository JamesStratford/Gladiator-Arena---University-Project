package rpgGame.CombatObject;

import java.util.Random;
import rpgGame.Items.CoinPurse;

/**
 *
 * @author James Stratford 21129223
 */
public class Loot
{
    private CoinPurse coins;
    
    public Loot(int tier)
    {
        coins = new CoinPurse(0, "Coin", 1, tier * (new Random().nextInt(100) + 1));
    }
    
    /*
    *   @returns coins
    */
    public int getLootAsCoins()
    {
        return this.coins.getCoins();
    }
}
