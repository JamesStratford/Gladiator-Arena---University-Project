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
public class ArmourTest
{
    
    public ArmourTest()
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
     * Test of getBlock method, of class Armour.
     */
    @Test
    public void testGetBlock()
    {
        System.out.println("getBlock");
        Armour instance = new Armour(11, "test armour", 0, 0, 20, 0);
        int expResult = 20;
        int result = instance.getBlock();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBlockPercentage method, of class Armour.
     */
    @Test
    public void testGetBlockPercentage()
    {
        System.out.println("getBlockPercentage");
        Armour instance = new Armour(11, "test armour", 0, 0, 0, 13.5f);
        float expResult = 13.5F;
        float result = instance.getBlockPercentage();
        assertEquals(expResult, result, 0.0);
    }
}
