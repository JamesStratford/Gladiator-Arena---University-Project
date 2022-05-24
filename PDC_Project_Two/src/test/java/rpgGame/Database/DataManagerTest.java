/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgGame.Database;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rpgGame.Engine;
import rpgGame.Items.Item;
import rpgGame.Items.Weapon;

/**
 *
 * @author jestr
 */
public class DataManagerTest
{
    
    public DataManagerTest()
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
     * Test of saveGame method, of class DataManager.
     */
    @Test
    public void testSaveGame() throws Exception
    {
        System.out.println("saveGame");
        DataManager instance = new DataManager();
        instance.saveGame();
    }

    /**
     * Test of loadGame method, of class DataManager.
     */
    @Test
    public void testLoadGame() throws IOException
    {
        System.out.println("loadGame");
        String playerName = "";
        DataManager instance = new DataManager();
        boolean expResult = false;
        boolean result = instance.loadGame(playerName);
        assertEquals(expResult, result);
    }

    /**
     * Test of importGame method, of class DataManager.
     */
    @Test
    public void testImportGame() throws IOException
    {
        System.out.println("importGame");
        String path = "./resources/savegame.txt";
        DataManager instance = new DataManager();
        boolean expResult = true;
        boolean result = instance.importGame(path);
        assertEquals(expResult, result);
    }

    /**
     * Test of exportGame method, of class DataManager.
     */
    @Test
    public void testExportGame() throws IOException
    {
        System.out.println("exportGame");
        String path = "";
        DataManager instance = new DataManager();
        boolean expResult = false;
        boolean result = instance.exportGame(path);
        assertEquals(expResult, result);
    }

    /**
     * Test of initialiseItemData method, of class DataManager.
     */
    @Test
    public void testInitialiseItemData() throws Exception
    {
        System.out.println("initialiseItemData");
        DataManager instance = new DataManager();
        instance.initialiseItemData();
        assertEquals(Item.items.get(1).getName(), Weapon.createWeapon(1).getName());
    }
}
