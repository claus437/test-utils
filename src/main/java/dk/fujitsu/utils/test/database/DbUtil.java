package dk.fujitsu.utils.test.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 27-09-11
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */
public class DbUtil {
    private static final String DATE_PATTERN = "yyyy.MM.dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    static void close(Statement statement) {
        try {
            statement.close();
        } catch (SQLException x) {
        }
    }

    static void close(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException x) {
        }
    }

    static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException x) {
        }
    }


    public static void setArguments(PreparedStatement statement, String[] args) throws SQLException {
        if (args == null || args.length == 0) {
            return;
        }

        for (int i = 0; i < args.length; i++) {
            statement.setObject(i + 1, transform(args[i]));
        }
    }

    static Object transform(String value) {
        if (value == null) {
            return null;
        }

        if (value.startsWith("^L")) {
            try {
                return new Date(new SimpleDateFormat(DATETIME_PATTERN).parse(value.substring(2)).getTime());
            } catch (ParseException x) {
                throw new DataBaseException("unparseable date " + value + " (" + DATETIME_PATTERN + ")");
            }
        }

        if (value.startsWith("^D")) {
            try {
                return new Date(new SimpleDateFormat(DATE_PATTERN).parse(value.substring(2)).getTime());
            } catch (ParseException x) {
                throw new DataBaseException("unparseable date " + value + " (" + DATE_PATTERN + ")");
            }
        }

        if (value.startsWith("^T")) {
            try {
                return new Date(new SimpleDateFormat(TIME_PATTERN).parse(value.substring(2)).getTime());
            } catch (ParseException x) {
                throw new DataBaseException("unparseable date " + value + " (" + TIME_PATTERN + ")");
            }
        }

        return value;
    }

    public static String toString(java.util.Date date) {
        return new SimpleDateFormat(DATETIME_PATTERN).format(date);
    }

}
