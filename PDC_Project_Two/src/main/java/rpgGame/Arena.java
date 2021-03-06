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
    private static Arena _instance = null;
    private Enemy enemy;
    private Player player;
    private boolean exit;
    
    public static Arena get()
    {
        if (_instance == null)
        {
            _instance = new Arena();
        }
        
        return _instance;
    }
    
    /**
     *  entrance method to run arena
     * @return exit value, true means quit to main menu
     * @throws InterruptedException 
     */
    public boolean arenaMain() throws InterruptedException
    {
        World.get().getButtonInputStream().clear();
        enemy = Enemy.generateRandomEnemy();
        player = Player.get();
        Engine.get().getGUI().setHpBars();
        Engine.get().getGUI().arena();
        exit = false;
        
        printArenaWelcome();
        
        System.out.println(enemy.toString()  + " HP: "  + enemy.getHealthPoints() + " Fatigue: " 
                + enemy.getFatiguePoints() + "\nStats:\n" + enemy.getStatsString());
        
        // running battle simulator
        simulateBattle();
        if (exit)
            return true;
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
        
        return false;
    }
    
    public Enemy getEnemy()
    {
        return this.enemy;
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
        World.get().getButtonInputStream().clear();
        boolean validInput = false;

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
                    case 6:
                        // main menu
                        exit = true;
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
        while (player.getAlive() && enemy.getAlive() && !exit)
        {
            Engine.get().getGUI().setHpBars();
            System.out.println("\n\nPlayers turn...");
            System.out.println(player.toString() + " HP: " + player.getHealthPoints() + ". Fatigue: " + player.getFatiguePoints());
            Thread.sleep(500);
            queryBattleOptions();
            if (exit)
                return;
            System.out.println();
            if (enemy.getAlive())
            {
                System.out.println("\n\n" + enemy.toString() + "s turn...");
                System.out.println(enemy.toString() + " HP: " + enemy.getHealthPoints() + ". Fatigue: " + enemy.getFatiguePoints());
                Thread.sleep(500);
                enemyTurn();
            }
        }
        
        
        Engine.get().getGUI().setHpBars();
        
        if (player.getAlive())
        {
            player.resetHealthResources();
        }
    }
}
