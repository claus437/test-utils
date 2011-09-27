package dk.fujitsu.utils.test.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dk.fujitsu.utils.test.database.DbUtil.close;
import static dk.fujitsu.utils.test.database.DbUtil.setArguments;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 27-09-11
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public class DbQuery {

    Map<String, String> queryForRecord(Connection connection, String sql, String... args) throws SQLException {
        PreparedStatement statement;
        ResultSet rs;
        Map<String, String> result;

        statement = connection.prepareStatement(sql);

        try {
            setArguments(statement, args);
            rs = statement.executeQuery(sql);

            try {
                result = rs.next() ? mapRow(getColumnNames(rs.getMetaData()), rs) : null;
            } finally {
                close(rs);
            }
        } finally {
            close(statement);
        }

        return result;
    }

    List<Map<String, String>> queryForList(Connection connection, String sql, String... args) throws SQLException {
        PreparedStatement statement;
        ResultSet rs;
        List<Map<String, String>> result;

        statement = connection.prepareStatement(sql);

        try {
            setArguments(statement, args);
            rs = statement.executeQuery();

            try {
                result = mapList(getColumnNames(rs.getMetaData()), rs);
            } finally {
                close(rs);
            }
        } finally {
            close(statement);
        }

        return result;
    }

    private List<Map<String, String>> mapList(List<String> columnNames, ResultSet rs) throws SQLException {
        List<Map<String, String>> resultList;

        resultList = new ArrayList<Map<String, String>>();

        while (rs.next()) {
            resultList.add(mapRow(columnNames, rs));
        }

        return resultList;
    }

    private Map<String, String> mapRow(List<String> columnNames, ResultSet rs) throws SQLException {
        Map<String, String> result;

        result = new HashMap<String, String>();
        for (String columnName : columnNames) {
            result.put(columnName, rs.getString(columnName));
        }

        return result;
    }

    private List<String> getColumnNames(ResultSetMetaData metaData) throws SQLException {
        int columns;
        List<String> columnNames;

        columnNames = new ArrayList<String>();

        columns = metaData.getColumnCount();
        for (int i = 0; i < columns; i++) {
            columnNames.add(metaData.getColumnName(i + 1));
        }

        return columnNames;
    }





}
