package rpgGame.CombatObject;

import java.util.Random;
import rpgGame.Items.Armour;
import rpgGame.Items.Weapon;

/**
 *
 * @author James Stratford 21129223
 */
public class Enemy extends CombatObject
{
    private EnemyType type;
    private int enemyTier;
    private Loot loot;
    private int xpGiven;

    public Enemy(EnemyType type, int strength, int dexterity, int vitality, int stamina)
    {
        super(strength, dexterity, vitality, stamina);
        
        enemyTier = new Random().nextInt(Player.get().getLevel()) + 1;
        
        this.stats.strength *= enemyTier;
        this.stats.dexterity *= enemyTier;
        this.stats.vitality *= enemyTier;
        this.stats.stamina *= enemyTier;
        this.healthPoints += (enemyTier * this.stats.vitality);
        this.fatiguePoints += (enemyTier * this.stats.stamina);
        
        xpGiven = 50 * enemyTier;
        
        if (enemyTier > 3)
        {
            this.wieldedWeapon = Weapon.createWeapon(new Random().nextInt(6) + 1);
            this.wieldedArmour = Armour.createArmour(new Random().nextInt(3) + 8);
        }
        
        this.type = type;
        loot = new Loot(enemyTier);
    }
    
    /**
     * @return the type
     */
    public EnemyType getType()
    {
        return type;
    }

    /**
     * 
     * @return the loot
     */
    public Loot getLoot()
    {
        return loot;
    }

    /**
     * 
     * @return xpGiven
     */
    public int getXpGiven()
    {
        return xpGiven;
    }
    
    /**
     *  static method used for generation of randomized enemy object
     * @return enemy
     */
    public static Enemy generateRandomEnemy()
    {
        Random rand = new Random();
        
        Enemy enemy = null;
        
        switch (rand.nextInt(5))
        {
            case 0:
                enemy = new Enemy(EnemyType.HUMAN, rand.nextInt(4) + 1, rand.nextInt(6) + 1, rand.nextInt(5) + 1, rand.nextInt(7) + 1);
                break;
            case 1:
                enemy = new Enemy(EnemyType.GOBLIN, rand.nextInt(3) + 1, rand.nextInt(8) + 1, rand.nextInt(4) + 1, rand.nextInt(5) + 1);
                break;
            case 2:
                enemy = new Enemy(EnemyType.ORC, rand.nextInt(10) + 1, rand.nextInt(5) + 1, rand.nextInt(8) + 1, rand.nextInt(4) + 1);
                break;
            case 3:
                enemy = new Enemy(EnemyType.ELF, rand.nextInt(4) + 1, rand.nextInt(10) + 1, rand.nextInt(3) + 1, rand.nextInt(8) + 1);
                break;
            case 4:
                enemy = new Enemy(EnemyType.UNDEAD, rand.nextInt(4) + 1, rand.nextInt(3) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1);
                break;
        }
        
        return enemy;
    }
    
    /**
     * 
     * @return name
     */
    @Override
    public String toString()
    {
        return this.getType().name;
    }
}
