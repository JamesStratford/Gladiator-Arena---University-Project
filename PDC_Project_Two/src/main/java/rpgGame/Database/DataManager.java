package rpgGame.Database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import rpgGame.CombatObject.Player;
import rpgGame.Items.Armour;
import rpgGame.Items.Item;
import rpgGame.Items.Weapon;
import rpgGame.World;

/**
 *
 * @author James Stratford 21129223
 */
public class DataManager
{
    public static DataManager _instance = null;
    private SaveGame saveGame;
    
    public DataManager() throws IOException
    {
        saveGame = new SaveGame();
    }
    
    /**
    *   @returns _instance
    */
    public static DataManager get()
    {
        if (_instance == null)
        {
            try
            {
                _instance = new DataManager();
            } catch (IOException ex)
            {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return _instance;
    }
    
    /**
    *   writes player data to a savefile.
     * @throws java.io.IOException
    */
    public void saveGame() throws IOException
    {
        saveGame.saveGame();
    }
    
    public boolean loadGame(String playerName)
    {
        return saveGame.loadGameFromDatabase(playerName);
    }
    
    /**
     * Wrapper for private class importGame method.
     * @return 
     */
    public boolean importGame(String path)
    {
        return saveGame.importGame(path);
    }
    
    /**
     * Wrapper for private class exportGame method.
     * @return 
     */
    public boolean exportGame(String path)
    {
        return saveGame.exportGame(path);
    }

    /**
     * Deletes a character from embedded database.
     * @param name 
     */
    public void deleteGame(String name)
    {
        saveGame.deleteGame(name);
    }

    
    /**
    *   begins auto-save thread.
    */
    public void startGame()
    {
        Thread saveThread = new Thread(saveGame);
        saveThread.start();
    }
    
    /**
    *   creates master items and places them in the items HashSet
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     * @throws java.sql.SQLException
    */
    public void initialiseItemData() throws IOException, FileNotFoundException, SQLException
    {
        new ItemInitialiser().initialiseItemData();
    }
    
    /**
    *   Inner class responsible for reading in data about items and creating master collections.
    */
    private class ItemInitialiser
    {
        public void initialiseItemData() throws IOException, FileNotFoundException, SQLException
        {
            Item.items = new HashMap<>();
            try
            {
                initialiseWeaponData();
                initialiseArmourData();
            } catch (SQLException ex)
            {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            Weapon.weapons.forEach(weapon ->
            {
                Item.items.put(weapon.getItemId(), weapon);
            });
            Armour.armours.forEach(armour ->
            {
                Item.items.put(armour.getItemId(), armour);
            });
        }
        
        /**
         * Reads data from file and adds any weapons not already in the
         * database, then loads armour into Armour hash set.
         * @throws FileNotFoundException
         * @throws IOException
         * @throws SQLException
         */
        private void initialiseArmourData() throws FileNotFoundException, IOException, SQLException
        {
            Armour.armours = new HashSet<>();

            BufferedReader reader = new BufferedReader(new FileReader(new File("./resources/armour.txt")));
            
            ResultSet rs = MainDatabase.get().getConnection().createStatement().executeQuery("SELECT * FROM Armour");

            String string = reader.readLine();
            while (string != null)
            {
                StringTokenizer armourData = new StringTokenizer(string);

                int itemId = Integer.parseInt(armourData.nextToken(","));
                boolean inDatabase = false;
                while (rs.next())
                {
                    if (rs.getInt("itemId") == itemId)
                    {
                        inDatabase = true;
                        break;
                    }
                }
                if (!inDatabase)
                {
                    String name = armourData.nextToken();
                    int coinValue = Integer.parseInt(armourData.nextToken());
                    double weight = Double.parseDouble(armourData.nextToken());
                    int block = Integer.parseInt(armourData.nextToken());
                    float blockPercentage = Float.parseFloat(armourData.nextToken());
                    MainDatabase.get().getConnection().createStatement().executeUpdate("INSERT INTO Armour"
                            + " VALUES (" + itemId + ", \'" + name + "\', " + coinValue + ", " + weight + ", " + block + ", "+
                                    blockPercentage+")");
                }

                string = reader.readLine();
            }
            
            loadArmourFromDatabase();
        }
        
        /**
         * Loads in armour via the database
         * @throws SQLException
         */
        private void loadArmourFromDatabase() throws SQLException
        {
            ResultSet rs = MainDatabase.get().getConnection().createStatement().executeQuery("SELECT * FROM Armour");
            
            while (rs.next())
            {
                int itemId = rs.getInt("itemId");
                String name = rs.getString("itemName");
                int coinValue = rs.getInt("coinValue");
                double weight = rs.getDouble("weight");
                int block = rs.getInt("block");
                float blockPercentage = rs.getFloat("blockPercentage");
                
                Armour.armours.add(new Armour(itemId, name, coinValue, weight, block, blockPercentage));
            }
        }
    
        /**
         * Reads data from file and adds any weapons not already in the 
         * database, then loads weapons into Weapons hash set.
         * @throws FileNotFoundException
         * @throws IOException
         * @throws SQLException 
         */
        private void initialiseWeaponData() throws FileNotFoundException, IOException, SQLException
        {
            Weapon.weapons = new HashSet<>();

            BufferedReader reader = new BufferedReader(new FileReader(new File("./resources/weapons.txt")));
            
            ResultSet rs = MainDatabase.get().getConnection().createStatement().executeQuery("SELECT * FROM Weapons");

            String string = reader.readLine();
            while (string != null)
            {
                StringTokenizer weaponData = new StringTokenizer(string);

                int itemId = Integer.parseInt(weaponData.nextToken(","));
                boolean inDatabase = false;
                while (rs.next())
                {
                    if (rs.getInt("itemId") == itemId)
                    {
                        inDatabase = true;
                        break;
                    }
                }

                if (!inDatabase)
                {
                    String name = weaponData.nextToken();
                    int coinValue = Integer.parseInt(weaponData.nextToken());
                    double weight = Double.parseDouble(weaponData.nextToken());
                    int meanDamage = Integer.parseInt(weaponData.nextToken());
                    MainDatabase.get().getConnection().createStatement().executeUpdate("INSERT INTO Weapons"
                            + " VALUES (" + itemId + ", \'"+name+"\', "+coinValue+", "+weight+", "+meanDamage+")");
                }

                string = reader.readLine();
            }
            
            loadWeaponsFromDatabase();
        }
        
        /**
         * Loads in weapons via the database
         * @throws SQLException 
         */
        private void loadWeaponsFromDatabase() throws SQLException
        {
            ResultSet rs = MainDatabase.get().getConnection().createStatement().executeQuery("SELECT * FROM Weapons");

            while (rs.next())
            {
                int itemId = rs.getInt("itemId");
                String name = rs.getString("itemName");
                int coinValue = rs.getInt("coinValue");
                double weight = rs.getDouble("weight");
                int meanDamage = rs.getInt("meanDamage");

                Weapon.weapons.add(new Weapon(itemId, name, coinValue, weight, meanDamage));
            }
        }
    }
    
    /**
     * For use with the LoadGameFrame.
     * @return Returns an ArrayList of Strings that has all game save character names
     */
    public ArrayList<String> getSaveFilesAsArrayListOfStrings()
    {
        ArrayList<String> ret = new ArrayList<>();
        try
        {
            Statement statement = MainDatabase.get().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT name FROM GameSave");
            
            while (rs.next())
            {
                ret.add(rs.getString("name"));
            }
            
            return ret;
        } catch (SQLException ex)
        {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
    *   Inner class managing all savefile functionality.
    */
    private class SaveGame implements Runnable
    {
        private File save;
        private Player player;
        private BufferedReader reader;
        private BufferedWriter writer;
        private boolean quitGame;

        public SaveGame() throws IOException
        {
            save = new File("./resources/savegame.txt");
            save.setReadable(true);
        }
        
        /**
         * Sets path of desired save file to be used for importing and exporting save files.
         * @param path 
         */
        public void setSaveFilePath(String path)
        {
            try
            {
                save = new File(path);
            } catch (java.lang.NullPointerException e)
            {
                
            }
        }

        // <editor-fold defaultstate="collapsed" desc="File IO Writing and Reading methods">
        /*
        *   manages savefile write functions
        */
        public boolean writeOutPlayerData() throws IOException
        {
            save.setWritable(true);
            writer = new BufferedWriter(new FileWriter(save));

            writePlayerBasicInfoSave();
            writePlayerStatsInfoSave();
            writePlayerInventorySave();

            writer.close();
            return true;
        }
        
        /*
        *   writes players basic information to savefile.
        */
        public void writePlayerBasicInfoSave() throws IOException
        {
            String name = Player.get().getName();
            int level = Player.get().getLevel();
            int xp = Player.get().getXp();
            int xpToLevelUp = Player.get().getXpToNextLevel();
            int availableSkillPoints = Player.get().getAvailableSkillPoints();
            writer.append(name + "," + level + "," + xp + "," + xpToLevelUp + "," + availableSkillPoints + "\n");
        }
        
        /*
        *   writes player stats information to savefile.
        */
        public void writePlayerStatsInfoSave() throws IOException
        {
            int strength = Player.get().getStrength();
            int dexterity = Player.get().getDexterity();
            int vitality = Player.get().getVitality();
            int stamina = Player.get().getStamina();
            writer.append("stats" + "," + strength + "," + dexterity + "," + vitality + "," + stamina + "\n");
        }
        
        /*
        *   writes player inventory information to save file.
        */
        public void writePlayerInventorySave() throws IOException
        {
            int coins = Player.get().getCoins();
            ArrayList<Item> itemsAnonymous = new ArrayList<Item>()
            {
                @Override
                public String toString()
                {
                    Iterator<Item> it = iterator();
                    if (!it.hasNext())
                    {
                        return "-1";
                    }

                    StringBuilder sb = new StringBuilder();
                    for (;;)
                    {
                        Item e = it.next();
                        sb.append(e.getItemId());
                        if (!it.hasNext())
                        {
                            return sb.toString();
                        }
                        sb.append(',');
                    }
                }
            };

            ArrayList<Item> playerItems = Player.get().getInventory().getItems();
            itemsAnonymous.addAll(playerItems);
            int equiptWeapon;
            if (Player.get().getWieldedWeapon() != null)
            {
                equiptWeapon = Player.get().getWieldedWeapon().getItemId();
            } else
            {
                equiptWeapon = -1;
            }

            int equiptArmour;
            if (Player.get().getWieldedArmour() != null)
            {
                equiptArmour = Player.get().getWieldedArmour().getItemId();
            } else
            {
                equiptArmour = -1;
            }
            
            writer.append("inventory" + "," + coins + "," + itemsAnonymous.toString() + "\n" + equiptWeapon + "," + equiptArmour);
        }

        /*
        *   read in player data from savefile. Tokenizes strings and creates a player object
        *   @returns Player object
        */
        public Player readInPlayerData() throws IOException
        {
            reader = new BufferedReader(new FileReader(save));
            String string = reader.readLine();
            if (string != null)
            {
                StringTokenizer playerData = new StringTokenizer(string);

                // basic info
                String name = playerData.nextToken(",");
                int level = Integer.parseInt(playerData.nextToken());
                int xp = Integer.parseInt(playerData.nextToken());
                int xpToLevelUp = Integer.parseInt(playerData.nextToken());
                int availableSkillPoints = Integer.parseInt(playerData.nextToken());

                // stats
                string = reader.readLine();
                playerData = new StringTokenizer(string);
                playerData.nextToken(",");
                int strength = Integer.parseInt(playerData.nextToken());
                int dexterity = Integer.parseInt(playerData.nextToken());
                int vitality = Integer.parseInt(playerData.nextToken());
                int stamina = Integer.parseInt(playerData.nextToken());

                // inventory
                string = reader.readLine();
                playerData = new StringTokenizer(string);
                playerData.nextToken(",");
                int coins = Integer.parseInt(playerData.nextToken());
                ArrayList<Integer> inventoryList = new ArrayList<>();
                while (playerData.hasMoreTokens())
                {
                    inventoryList.add(Integer.parseInt(playerData.nextToken()));
                }
                string = reader.readLine();
                playerData = new StringTokenizer(string);
                int equiptWeapon = Integer.parseInt(playerData.nextToken(","));
                int equiptArmour = Integer.parseInt(playerData.nextToken());

                Player outPlayer = new Player(
                        name,
                        level,
                        xp,
                        xpToLevelUp,
                        availableSkillPoints,
                        strength,
                        dexterity,
                        vitality,
                        stamina,
                        coins,
                        inventoryList,
                        equiptWeapon,
                        equiptArmour);

                reader.close();
                return outPlayer;
            }
            reader.close();
            return null;
        }
        // </editor-fold>

        /**
         * Load save game via text file.
         * @return
         * @throws IOException 
         */
        public boolean importGame(String path)
        {
            if (path != null && !path.equals(""))
            {
                setSaveFilePath(path);
                if (save.exists())
                {
                    try
                    {
                        Player.set(readInPlayerData());
                        player = Player.get();
                        System.out.println("Welcome back " + player.getName());

                        return true;
                    } catch (IOException ex)
                    {
                        Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                } else
                {
                    System.out.println("Can't find savefile");
                    return false;
                }
            }
            return false;
        }

        /**
         * Export save file to path currently set.
         * @return 
         */
        public boolean exportGame(String path)
        {
            try
            {
                if (path != null && !path.equals(""))
                {
                    setSaveFilePath(path);
                    return writeOutPlayerData();
                }

            } catch (IOException ex)
            {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
        
        public void deleteGame(String name)
        {
            try
            {
                Statement statement = MainDatabase.get().getConnection().createStatement();
                statement.execute("DELETE FROM GameSave WHERE name = " + "\'"+name+"\'");
            } catch (SQLException ex)
            {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /**
         * Wrapper function which saves the game.
         * @return 
         */
        synchronized public boolean saveGame()
        {
            return saveToDatabase();
        }
        
        /**
         * Method to save player stats to the embedded database as an
         * alternative to fileIO
         */
        private boolean saveToDatabase()
        {
            boolean success = true;
            
            String name = Player.get().getName();
            name = name.substring(0, Math.min(name.length(), 10));
            int level = Player.get().getLevel();
            int xp = Player.get().getXp();
            int xpToLevelUp = Player.get().getXpToNextLevel();
            int availableSkillPoints = Player.get().getAvailableSkillPoints();
            int strength = Player.get().getStrength();
            int dexterity = Player.get().getDexterity();
            int vitality = Player.get().getVitality();
            int stamina = Player.get().getStamina();
            int coins = Player.get().getCoins();

            ArrayList<Item> itemsAnonymous = new ArrayList<Item>()
            {
                @Override
                public String toString()
                {
                    Iterator<Item> it = iterator();
                    if (!it.hasNext())
                    {
                        return "-1";
                    }

                    StringBuilder sb = new StringBuilder();
                    for (;;)
                    {
                        Item e = it.next();
                        sb.append(e.getItemId());
                        if (!it.hasNext())
                        {
                            return sb.toString();
                        }
                        sb.append(',');
                    }
                }
            };
            ArrayList<Item> playerItems = Player.get().getInventory().getItems();
            itemsAnonymous.addAll(playerItems);
            
            String inventory = itemsAnonymous.toString();
            int equiptWeapon;
            if (Player.get().getWieldedWeapon() != null)
            {
                equiptWeapon = Player.get().getWieldedWeapon().getItemId();
            } else
            {
                equiptWeapon = -1;
            }

            int equiptArmour;
            if (Player.get().getWieldedArmour() != null)
            {
                equiptArmour = Player.get().getWieldedArmour().getItemId();
            } else
            {
                equiptArmour = -1;
            }
            
            try
            {
                Statement statement = MainDatabase.get().getConnection().createStatement();
                if (statement.executeQuery("SELECT name FROM GameSave WHERE name = \'" + name+"\'").next())
                {
                    statement.executeUpdate("UPDATE GameSave "
                            + "SET level = " + level + ", xp = " + xp + ", xpToLevelUp = " + xpToLevelUp +", availableSkillPoints = " +
                            availableSkillPoints +", strength = " + strength + ", dexterity = " + dexterity+", vitality = " + vitality +
                            ", stamina = " + stamina + ", coins = " + coins +", inventory = \'"+ inventory+"\', equiptWeapon = " +
                            equiptWeapon + ", equiptArmour = " + equiptArmour +
                            "WHERE name = \'" + name+"\'");
                }
                else
                {
                    statement.addBatch("INSERT INTO GameSave "
                            + "VALUES (\'"+name+"\',"+level+","+xp+","+xpToLevelUp+","+availableSkillPoints+","+
                            strength+","+dexterity+","+vitality+","+stamina+","+coins+","+"\'"+inventory+"\'"+
                            ","+equiptWeapon+","+equiptArmour+")");
                    statement.executeBatch();
                }
                
                statement.close();
            } catch (SQLException ex)
            {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
                success = false;
            }
            
            return success;
        }
        
        public boolean loadGameFromDatabase(String playerName)
        {
            try
            {
                Statement statement = MainDatabase.get().getConnection().createStatement();
                ResultSet rs = statement.executeQuery("SELECT name, level, xp, xpToLevelUp, availableSkillPoints, "
                        + "strength, dexterity, vitality, stamina, coins, inventory, equiptWeapon, equiptArmour FROM GameSave "
                        + "WHERE name = '" + playerName + "'");
                if (rs.next())
                {
                    String name = rs.getString("name");
                    int level = rs.getInt("level");
                    int xp = rs.getInt("xp");
                    int xpToLevelUp = rs.getInt("xpToLevelUp");
                    int availableSkillPoints = rs.getInt("availableSkillPoints");
                    int strength = rs.getInt("strength");
                    int dexterity = rs.getInt("dexterity");
                    int vitality = rs.getInt("vitality");
                    int stamina = rs.getInt("stamina");
                    int coins = rs.getInt("coins");
                    String inventoryCSVType = rs.getString("inventory");
                    ArrayList<Integer> inventory = new ArrayList<>();
                    StringTokenizer token = new StringTokenizer(inventoryCSVType);
                    while (token.hasMoreTokens())
                    {
                        inventory.add(Integer.parseInt(token.nextToken(",")));
                    }
                    int equiptWeapon = rs.getInt("equiptWeapon");
                    int equiptArmour = rs.getInt("equiptArmour");

                    Player.set(new Player(name, level, xp, xpToLevelUp, availableSkillPoints, strength, dexterity,
                            vitality, stamina, coins, inventory, equiptWeapon, equiptArmour));

                    return true;
                } else
                {
                    System.out.println("no such player");

                    return false;
                }

            } catch (SQLException ex)
            {
            }
            return false;
        }

        /*
        *   Autosave thread every 30 seconds.
        */
        @Override
        public void run()
        {
            while (!quitGame)
            {
                quitGame = World.get().isQuit();
                long time = System.currentTimeMillis();
                while (!quitGame)
                {
                    long newTime = System.currentTimeMillis();
                    if (newTime - time > 30000)
                    {
                        saveGame();

                        break;
                    }
                    try
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex)
                    {
                        Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
