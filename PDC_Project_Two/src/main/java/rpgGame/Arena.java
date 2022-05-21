package rpgGame;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import rpgGame.CombatObject.Enemy;
import rpgGame.CombatObject.Player;

/**
 *
 * @author James Stratford 21129223
 */
public class Arena
{
    private Enemy enemy;
    private Player player;
    
    /**
     *  entrance method to run arena
     * @throws InterruptedException 
     */
    public void arenaMain() throws InterruptedException
    {
        Engine.get().getGUI().arena();
        enemy = Enemy.generateRandomEnemy();
        player = Player.get();
        printArenaWelcome();
        
        System.out.println(enemy.toString()  + " HP: "  + enemy.getHealthPoints() + " Fatigue: " 
                + enemy.getFatiguePoints() + "\nStats:\n" + enemy.getStatsString());
        
        // running battle simulator
        simulateBattle();
        
        // add enemy to defeated list
        if (player.getAlive())
            player.getDefeatedEnemies().add(enemy);
        
        // give player enemies loot
        if (player.getAlive())
        {
            player.getInventory().addCoins(enemy.getLoot().getLootAsCoins());
            player.addXp(enemy.getXpGiven());
            System.out.println("You found " +enemy.getLoot().getLootAsCoins()+ " coins on your enemies corpse.");
            System.out.println("You gained " +enemy.getXpGiven()+ " xp.\n");
        }
    }
    
    /**
     *  prints arena welcome message with enemy name
     */
    private void printArenaWelcome()
    {
        System.out.println("Welcome to the Arena! Today you will be fighting... A " + enemy.getType().name);
        System.out.println();
    }
    
    /**
     *  player is queried with fighting options
     */
    private void queryBattleOptions()
    {
        boolean validInput = false;
//        System.out.println("1. Attack\n"
//                + "2. Dodge\n"
//                + "3. Rest\n"
//                + "4. Save and quit");
        while (!validInput)
        {
            if (!World.get().getButtonInputStream().isEmpty())
            {
                switch (World.get().getButtonInputStream().read())
                {
                    case 1:
                        player.attack(enemy);
                        validInput = true;
                        break;
                    case 2:
                        player.dodge();
                        validInput = true;
                        break;
                    case 3:
                        player.rest();
                        validInput = true;
                        break;
                }
            }
            try
            {
                Thread.sleep(50);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Arena.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * simulates enemy turn
     */
    public void enemyTurn()
    {
        switch (new Random().nextInt(3) + 1)
        {
            case 1:
            case 2:
                enemy.attack(player);
                break;
            case 3: 
                enemy.dodge();
                break;
        }
    }
    
    /**
     *  runs the battle between the player and enemy
     * @throws InterruptedException 
     */
    public void simulateBattle() throws InterruptedException
    {
        while (player.getAlive() && enemy.getAlive())
        {
            System.out.println("\nPlayers turn...");
            Thread.sleep(1000);
            System.out.println(player.toString() + " HP: " + player.getHealthPoints() + ". Fatigue: " + player.getFatiguePoints());
            System.out.println();
            queryBattleOptions();
            System.out.println();
            if (enemy.getAlive())
            {
                System.out.println(enemy.toString() + "s turn...");
                Thread.sleep(1000);
                System.out.println(enemy.toString() + " HP: " + enemy.getHealthPoints() + ". Fatigue: " + enemy.getFatiguePoints());
                enemyTurn();
            }
        }
        
        if (player.getAlive())
        {
            player.resetHealthResources();
        }
    }
}
