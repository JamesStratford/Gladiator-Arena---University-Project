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
public class ItemTest
{
    
    public ItemTest()
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
     * Test of getItemId method, of class Item.
     */
    @Test
    public void testGetItemId()
    {
        System.out.println("getItemId");
        Item instance = new Weapon(10, "test item", 0, 0, 0);
        int expResult = 10;
        int result = instance.getItemId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class Item.
     */
    @Test
    public void testGetName()
    {
        System.out.println("getName");
        Item instance = new Weapon(10, "test item", 0, 0, 0);
        String expResult = "test item";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class Item.
     */
    @Test
    public void testGetValue()
    {
        System.out.println("getValue");
        Item instance = new Weapon(10, "test item", 13, 0, 0);
        int expResult = 13;
        int result = instance.getValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWeight method, of class Item.
     */
    @Test
    public void testGetWeight()
    {
        System.out.println("getWeight");
        Item instance = new Weapon(10, "test item", 0, 13.0, 0);
        double expResult = 13.0;
        double result = instance.getWeight();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of toString method, of class Item.
     */
    @Test
    public void testToString()
    {
        System.out.println("toString");
        Item instance = new Weapon(10, "test item", 0, 0, 0);
        String expResult = "test item";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    public class ItemImpl extends Item
    {

        public ItemImpl()
        {
            super(0, "", 0, 0.0);
        }
    }
    
}
