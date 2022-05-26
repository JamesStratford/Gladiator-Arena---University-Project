package rpgGame.Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James Stratford 21129223
 */
public class MainDatabase
{
    private static MainDatabase _instance = null;
    private Connection conn = null;
    private String url = "jdbc:derby:RPGGameDB;create=true";
//    private String url = "jdbc:derby://localhost:1527/RPGGameDB;create=true";

    private String dbusername = "main";
    private String dbpassword = "main";
    
    public MainDatabase()
    {
        dbsetup();
    }
    
    public Connection getConnection()
    {
        return conn;
    }
    
    public static MainDatabase get()
    {
        if (_instance == null)
            _instance = new MainDatabase();
        
        return _instance;
    }
    
    private boolean checkTableExisting(String newTableName)
    {
        boolean flag = false;
        try
        {
            String[] types =
            {
                "TABLE"
            };
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);
            while (rsDBMeta.next())
            {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0)
                {
                    flag = true;
                }
            }
            if (rsDBMeta != null)
            {
                rsDBMeta.close();
            }
        } catch (SQLException ex)
        {
        }
        return flag;
    }
    
    public void dbsetup()
    {
        try
        {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();
            String[] tableNames = {"GameSave", "Weapons", "Armour"};
            
            if (!checkTableExisting(tableNames[0]))
            {
                statement.executeUpdate("CREATE TABLE " + tableNames[0] + " (name VARCHAR(20), level INT, xp INT, "
                        + "xpToLevelUp INT, availableSkillPoints INT, strength INT, dexterity INT, vitality INT, "
                        + "stamina INT, coins INT, inventory VARCHAR(20), equiptWeapon INT, equiptArmour INT,"
                        + "PRIMARY KEY (name))");
            }
            
            if (!checkTableExisting(tableNames[1]))
            {
                statement.executeUpdate("CREATE TABLE " + tableNames[1] + " (itemId INT, itemName VARCHAR(20), "
                        + "coinValue INT, weight DOUBLE, meanDamage INT, "
                        + "PRIMARY KEY (itemId))");
            }
            
            if (!checkTableExisting(tableNames[2]))
            {
                statement.executeUpdate("CREATE TABLE " + tableNames[2] + " (itemId INT, itemName VARCHAR(20), "
                        + "coinValue INT, weight DOUBLE, block INT, blockPercentage FLOAT, "
                        + "PRIMARY KEY (itemId))");
            }
            statement.close();

        } catch (SQLException ex)
        {
            Logger.getLogger(MainDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
