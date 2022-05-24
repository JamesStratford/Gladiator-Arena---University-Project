/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

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
public class ButtonInputQueueTest
{
    
    public ButtonInputQueueTest()
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
     * Test of read method, of class ButtonInputQueue.
     */
    @Test
    public void testRead()
    {
        System.out.println("read");
        ButtonInputQueue instance = new ButtonInputQueue();
        instance.addInput(5);
        int expResult = 5;
        int result = instance.read();
        assertEquals(expResult, result);
    }

    /**
     * Test of clear method, of class ButtonInputQueue.
     */
    @Test
    public void testClear()
    {
        System.out.println("clear");
        ButtonInputQueue instance = new ButtonInputQueue();
        instance.clear();
        int expResult = -1;
        int result = instance.read();
        assertEquals(expResult, result);
    }

    /**
     * Test of addInput method, of class ButtonInputQueue.
     */
    @Test
    public void testAddInput()
    {
        System.out.println("addInput");
        int option = 99;
        ButtonInputQueue instance = new ButtonInputQueue();
        instance.addInput(option);
        int expResult = 99;
        int result = instance.read();
        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class ButtonInputQueue.
     */
    @Test
    public void testIsEmpty()
    {
        System.out.println("isEmpty");
        ButtonInputQueue instance = new ButtonInputQueue();
        boolean expResult = true;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
    }    
}
