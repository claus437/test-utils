package dk.fujitsu.utils.test.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static dk.fujitsu.utils.test.database.DbUtil.*;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 27-09-11
 * Time: 15:01
 * To change this template use File | Settings | File Templates.
 */
public class DbExecute {

    public void execute(Connection connection, String sql, String... args) throws SQLException {
        PreparedStatement statement;

        statement = connection.prepareStatement(sql);

        try {
            statement.execute(sql);
            setArguments(statement, args);
        } catch (SQLException x) {
            throw new DataBaseException(x);
        } finally {
            close(statement);
        }
    }
}
