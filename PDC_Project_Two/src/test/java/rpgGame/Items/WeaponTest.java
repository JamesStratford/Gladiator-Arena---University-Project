/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgGame.Items;

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
public class WeaponTest
{
    
    public WeaponTest()
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
     * Test of getMeanDamage method, of class Weapon.
     */
    @Test
    public void testGetMeanDamage()
    {
        System.out.println("getMeanDamage");
        Weapon instance = new Weapon(1, "Test weapon", 0, 0, 50);
        int expResult = 50;
        int result = instance.getMeanDamage();
        assertEquals(expResult, result);
    }
}
