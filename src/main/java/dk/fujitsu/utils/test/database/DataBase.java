package dk.fujitsu.utils.test.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static dk.fujitsu.utils.test.database.DbUtil.close;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 27-09-11
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
public class DataBase {
    private String driver;
    private String url;
    private String user;
    private String password;
    private DbQuery dbQuery;
    private DbExecute dbExecute;
    private DbUpdate dbUpdate;

    public DataBase(Properties properties) {
        this(properties.getProperty("driver"), properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
    }

    public DataBase(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;

        dbQuery = new DbQuery();
        dbExecute = new DbExecute();
        dbUpdate = new DbUpdate();
    }

    public static DataBase open(Properties properties) {
        return new DataBase(properties);
    }

    public static DataBase open(String driver, String url, String user, String password) {
        return new DataBase(driver, url, user,  password);
    }

    public Map<String, String> queryForRecord(String sql, String... args) {
        Connection connection;

        connection = getConnection();

        try {
            return dbQuery.queryForRecord(connection, sql, args);
        } catch (SQLException x) {
            throw new DataBaseException(x);
        } finally {
            close(connection);
        }
    }

    public List<Map<String, String>> queryForList(String sql, String... args) {
        Connection connection;

        connection = getConnection();

        try {
            return dbQuery.queryForList(connection, sql, args);
        } catch (SQLException x) {
            throw new DataBaseException(x);
        } finally {
            close(connection);
        }
    }

    public void insert(String table, Map<String, String> values) {
        Connection connection;

        connection = getConnection();
        try {
            dbUpdate.insert(connection, table, values);
        } catch (SQLException x) {
            throw new DataBaseException(x);
        } finally {
            close(connection);
        }
    }

    public void execute(String sql, String... args) {
        Connection connection;

        connection = getConnection();

        try {
            dbExecute.execute(connection, sql, args);
        } catch (SQLException x) {
            throw new DataBaseException(x);
        } finally {
            close(connection);
        }
    }

    public void destroy() {
    }

    private Connection getConnection() {
        Connection connection;

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException x) {
            throw new RuntimeException("database drive " + driver + " not found in classpath");
        }

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException x) {
            throw new RuntimeException("failed opening sql connection " + url + " " + user + ":" + password);
        }

        return connection;
    }

}
