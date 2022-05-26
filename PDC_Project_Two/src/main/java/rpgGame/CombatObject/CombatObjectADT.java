/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgGame.CombatObject;

import rpgGame.Items.Armour;
import rpgGame.Items.Weapon;

/**
 *  Interface for CombatObject abstract classes!
 * @author James Stratford 21129223
 */
public interface CombatObjectADT
{
    /**
     * @return the healthPoints
     */
    public int getHealthPoints();
    /**
     *
     * @return max HP
     */
    public int getMaxHealthPoints();

    /**
     * @return the fatiguePoints
     */
    public int getFatiguePoints();

    /**
     *
     * @return max fatigue
     */
    public int getMaxFatiguePoints();

    /**
     * resets hp and fatigue after a battle.
     */
    public void resetHealthResources();

    /**
     *
     * @return alive
     */
    public boolean getAlive();

    /**
     *
     * @return strength
     */
    public int getStrength();

    /**
     *
     * @return dexterity
     */
    public int getDexterity();

    /**
     *
     * @return vitality
     */
    public int getVitality();

    /**
     *
     * @return stamina
     */
    public int getStamina();

    /**
     *
     * @return coins
     */
    public int getCoins();

    /**
     *
     * @return inventory
     */
    public Inventory getInventory();

    /**
     *
     * @return wieldedWeapon
     */
    public Weapon getWieldedWeapon();

    /**
     *
     * @param wieldedWeapon
     */
    public void setWieldedWeapon(Weapon wieldedWeapon);

    /**
     *
     * @return wieldedArmour
     */
    public Armour getWieldedArmour();

    /**
     *
     * @param wieldedArmour
     */
    public void setWieldedArmour(Armour wieldedArmour);

    /**
     * attacks target - used for both player and enemy subclasses
     *
     * @param target
     */
    public void attack(CombatObject target);

    /**
     * dodge option
     */
    public void dodge();
    
    /**
     * rest / forced-rest option
     */
    public void rest();

    /**
     * prints inventory, coins, and carry weight
     */
    public void viewInventory();
}
