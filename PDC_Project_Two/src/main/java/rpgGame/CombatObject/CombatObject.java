package rpgGame.CombatObject;

import java.util.Random;
import rpgGame.Items.Armour;
import rpgGame.Items.Weapon;

/**
 *
 * @author James Stratford 21129223
 */
public abstract class CombatObject implements CombatObjectADT
{
    protected int healthPoints;
    protected int fatiguePoints;
    protected int maxHP;
    protected int maxFatiguePoints;
    protected float dodgeChance;
    protected Stats stats;
    protected Inventory inventory;
    protected Weapon wieldedWeapon;
    protected Armour wieldedArmour;
    protected boolean alive;
    
    public CombatObject(int strength, int dexterity, int vitality, int stamina)
    {
        stats = new Stats();
        stats.strength = strength;
        stats.dexterity = dexterity;
        stats.vitality = vitality;
        stats.stamina = stamina;
        
        maxHP= 10 + (5 * vitality);
        maxFatiguePoints = 10 + (5 * stamina);
        
        healthPoints = maxHP;
        fatiguePoints = maxFatiguePoints;
        dodgeChance = 0f;
        
        inventory = new Inventory(25 + ((strength + dexterity + vitality + stamina) / 4));
        alive = true;
    }
    
    /**
     * @return the healthPoints
     */
    @Override
    public int getHealthPoints()
    {
        return healthPoints;
    }
    
    /**
     * 
     * @return max HP
     */
    @Override
    public int getMaxHealthPoints()
    {
        return maxHP;
    }

    /**
     * @return the fatiguePoints
     */
    @Override
    public int getFatiguePoints()
    {
        return fatiguePoints;
    }
    
    /**
     * 
     * @return max fatigue
     */
    @Override
    public int getMaxFatiguePoints()
    {
        return maxFatiguePoints;
    }
    
    /**
     *  resets hp and fatigue after a battle.
     */
    @Override
    public void resetHealthResources()
    {
        this.healthPoints = maxHP;
        this.fatiguePoints = maxFatiguePoints;
        this.dodgeChance = 0f;
    }
    
    /**
     * 
     * @return alive
     */
    @Override
    public boolean getAlive()
    {
        return alive;
    }
    
    
    /**
     * 
     * @return strength
     */
    @Override
    public int getStrength()
    {
        return this.stats.strength;
    }
    
    /**
     * 
     * @return dexterity
     */
    @Override
    public int getDexterity()
    {
        return this.stats.dexterity;
    }
    
    /**
     * 
     * @return vitality
     */
    @Override
    public int getVitality()
    {
        return this.stats.vitality;
    }
    
    /**
     * 
     * @return stamina
     */
    @Override
    public int getStamina()
    {
        return this.stats.stamina;
    }
    
    /**
     * 
     * @return coins
     */
    @Override
    public int getCoins()
    {
        return this.inventory.getCoins();
    }
    
    /**
     * 
     * @return inventory
     */
    @Override
    public Inventory getInventory()
    {
        return this.inventory;
    }
    
    /**
     * 
     * @return wieldedWeapon
     */
    @Override
    public Weapon getWieldedWeapon()
    {
        return this.wieldedWeapon;
    }

    /**
     * 
     * @param wieldedWeapon 
     */
    @Override
    public void setWieldedWeapon(Weapon wieldedWeapon)
    {
        this.wieldedWeapon = wieldedWeapon;
    }

    /**
     * 
     * @return wieldedArmour
     */
    @Override
    public Armour getWieldedArmour()
    {
        return this.wieldedArmour;
    }

    /**
     * 
     * @param wieldedArmour 
     */
    @Override
    public void setWieldedArmour(Armour wieldedArmour)
    {
        this.wieldedArmour = wieldedArmour;
    }
    
    /**
     *  attacks target - used for both player and enemy subclasses
     * @param target 
     */
    @Override
    public void attack(CombatObject target)
    {
        //System.out.println("DEBUG: Attacking");
        int damage = 0;

        if (getFatiguePoints() > 10)
        {
            if (new Random().nextFloat() > target.dodgeChance)
            {
                target.dodgeChance = 0f;
                if (wieldedWeapon != null)
                {
                    damage = (wieldedWeapon.getMeanDamage() + ((new Random().nextInt(3) + 1) * this.stats.strength));
                } else
                {
                    damage = (new Random().nextInt(new Random().nextInt(3) + 1) * this.stats.strength) + this.stats.strength;
                }

                if (target.wieldedArmour != null)
                {
                    float negatedPercentageDamage = target.wieldedArmour.getBlockPercentage() / 100.f;
                    damage *= 1 - negatedPercentageDamage;
                    damage -= target.wieldedArmour.getBlock();
                }

                if (damage <= 0)
                {
                    damage = 1;
                }

                target.healthPoints -= damage;

                if (target.getHealthPoints() < 1)
                {
                    target.alive = false;
                }

                System.out.println(target.toString() + " hit for " + damage + "! New HP = " + target.getHealthPoints());

                if (target.alive == false)
                {
                    System.out.println(target.toString() + " has died!");
                }
            } else
            {
                System.out.println(target.toString() + " has dodged the attack! 0 damage.");
                target.dodgeChance = 0f;
            }
            fatiguePoints -= 10;
        }
        else
        {
            System.out.println("Out of fatigue. Resting.");
            rest();
        }
    }

    /**
     *  dodge option
     */
    @Override
    public void dodge()
    {
        if (getFatiguePoints() > 10)
        {
            System.out.println(this.toString() + " prepares to dodge...");

            dodgeChance = 0.2f;
            dodgeChance += ((this.stats.dexterity * (new Random().nextFloat() + 1f)) * 2) / 100f;
            System.out.println("Dodge Chance: " + (dodgeChance * 100) + "%");

            fatiguePoints -= 10;
        } 
        else
        {
            System.out.println("Out of fatigue. Resting.");
            rest();
        }
    }
    
    /**
     *  rest / forced-rest option
     */
    @Override
    public void rest()
    {
        fatiguePoints += 15;
    }
    
    /**
     *  prints inventory, coins, and carry weight
     */
    @Override
    public void viewInventory()
    {
        System.out.println("Carry weight: " + this.inventory.getWeightCapacityString());
        System.out.println("Coins: " + this.inventory.getCoins());
        for (int i = 0; i < this.inventory.getItems().size(); i++)
        {
            if (this.inventory.getItems().get(i).getClass() == Weapon.class)
            {
                Weapon weapon = (Weapon)this.inventory.getItems().get(i);
                System.out.println(i + 1 + ". " + weapon.getName() + ". Damage: " + weapon.getMeanDamage());
            }
            else if (this.inventory.getItems().get(i).getClass() == Armour.class)
            {
                Armour armour = (Armour) this.inventory.getItems().get(i);
                System.out.println(i + 1 + ". " + armour.getName() + ". Base block: " + armour.getBlock() + 
                        ". Block percentage:" + armour.getBlockPercentage() +"%");
            }
            else
                System.out.println(i + 1 + ". " + this.inventory.getItems().get(i).getName());
        }
        System.out.println("Equipt weapon: " + this.wieldedWeapon);
        System.out.println("Equipt armour: " + this.wieldedArmour);
        System.out.println();
    }
    
    /**
     * 
     * @return stats.toString()
     */
    public String getStatsString()
    {
        return this.stats.toString();
    }
    
    /**
     *  Inner class holding stat information
     */
    protected class Stats
    {
        protected int strength;
        protected int dexterity;
        protected int vitality;
        protected int stamina;
        
        @Override
        public String toString()
        {
            return this.strength + " strength,\n" + this.dexterity + " dexterity,\n" + this.vitality + " vitality,\n" + this.stamina + " stamina.";
        }
    }
}
