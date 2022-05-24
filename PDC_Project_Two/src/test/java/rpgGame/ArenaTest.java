/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgGame;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rpgGame.CombatObject.Enemy;

/**
 *
 * @author jestr
 */
public class ArenaTest
{
    
    public ArenaTest()
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
     * Test of simulateBattle method, of class Arena.
     */
    @Test
    public void testArenaMain() throws Exception
    {
        System.out.println("simulateBattle");
        Arena instance = new Arena();
        instance.arenaMain();
    }
}
