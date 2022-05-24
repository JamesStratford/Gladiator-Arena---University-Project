/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgGame.CombatObject;

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
public class EnemyTest
{
    
    public EnemyTest()
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
     * Test of getType method, of class Enemy.
     */
    @Test
    public void testGetType()
    {
        System.out.println("getType");
        Enemy instance = new Enemy(EnemyType.ELF, 1, 1, 1, 1);
        EnemyType expResult = EnemyType.ELF;
        EnemyType result = instance.getType();
        assertEquals(expResult, result);
    }
}
