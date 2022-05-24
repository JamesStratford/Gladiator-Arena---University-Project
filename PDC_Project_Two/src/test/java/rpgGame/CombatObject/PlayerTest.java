/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgGame.CombatObject;

import java.util.LinkedHashSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jestr
 */
public class PlayerTest
{
    
    public PlayerTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of set method, of class Player.
     */
    @Test
    public void testSet()
    {
        System.out.println("set");
        Player player = new Player("test", 0, 0, 0, 0);
        Player.set(player);
    }

    /**
     * Test of getName method, of class Player.
     */
    @Test
    public void testGetName()
    {
        System.out.println("getName");
        Player instance = new Player("test", 0, 0, 0, 0);
        String expResult = "test";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getXp method, of class Player.
     */
    @Test
    public void testGetXp()
    {
        System.out.println("getXp");
        Player instance = new Player("test", 0, 0, 0, 0);
        int expResult = 0;
        int result = instance.getXp();
        assertEquals(expResult, result);
    }

    /**
     * Test of getXpToNextLevel method, of class Player.
     */
    @Test
    public void testGetXpToNextLevel()
    {
        System.out.println("getXpToNextLevel");
        Player instance = new Player("test", 0, 0, 0, 0);
        int expResult = 100;
        int result = instance.getXpToNextLevel();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLevel method, of class Player.
     */
    @Test
    public void testGetLevel()
    {
        System.out.println("getLevel");
        Player instance = new Player("test", 0, 0, 0, 0);;
        int expResult = 1;
        int result = instance.getLevel();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAvailableSkillPoints method, of class Player.
     */
    @Test
    public void testGetAvailableSkillPoints()
    {
        System.out.println("getAvailableSkillPoints");
        Player instance = new Player("test", 0, 0, 0, 0);;
        int expResult = 0;
        int result = instance.getAvailableSkillPoints();
        assertEquals(expResult, result);
    }

    /**
     * Test of addXp method, of class Player.
     */
    @Test
    public void testAddXp()
    {
        System.out.println("addXp");
        int xp = 50;
        Player instance = new Player("test", 0, 0, 0, 0);
        instance.addXp(xp);
        assertEquals(xp, instance.getXp());
    }

    /**
     * Test of levelUp method, of class Player.
     */
    @Test
    public void testLevelUp()
    {
        System.out.println("levelUp");
        int level = 2;
        Player instance = new Player("test", 0, 0, 0, 0);
        instance.levelUp();
        assertEquals(level, instance.getLevel());
    }   
}
