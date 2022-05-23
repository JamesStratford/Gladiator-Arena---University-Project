package rpgGame.Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import rpgGame.CombatObject.Player;

/**
 *
 * @author James Stratford 21129223
 */
public class rpgGameDatabase
{
    private static rpgGameDatabase _instance = null;
    private Connection conn = null;
//    private String url = "jdbc:derby:RPGGameDB;create=true";
    private String url = "jdbc:derby://localhost:1527/RPGGameDB;create=true";

    private String dbusername = "main";
    private String dbpassword = "main";
    
    public rpgGameDatabase()
    {
        dbsetup();
    }
    
    public Connection getConnection()
    {
        return conn;
    }
    
    public static rpgGameDatabase get()
    {
        if (_instance == null)
            _instance = new rpgGameDatabase();
        
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
            String tableName = "GameSave";

            if (!checkTableExisting(tableName))
            {
                statement.executeUpdate("CREATE TABLE " + tableName + " (name VARCHAR(12), level INT, xp INT, "
                        + "xpToLevelUp INT, availableSkillPoints INT, strength INT, dexterity INT, vitality INT, "
                        + "stamina INT, coins INT, inventory VARCHAR(20), equiptWeapon INT, equiptArmour INT,"
                        + "PRIMARY KEY (name))");
            }
            statement.close();

        } catch (Throwable e)
        {
            System.out.println(e);
        }
    }
}
