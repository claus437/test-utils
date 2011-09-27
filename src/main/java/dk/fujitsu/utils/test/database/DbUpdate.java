package dk.fujitsu.utils.test.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static dk.fujitsu.utils.test.database.DbUtil.close;

public class DbUpdate {

    public void insert(Connection connection, String table, Map<String, String> values) throws SQLException {
        PreparedStatement statement;
        StringBuffer sql;
        List<String> columns;
        String[] columnValues;

        columns = new ArrayList<String>(values.keySet());
        columnValues = new String[columns.size()];

        for (int i = 0; i < columns.size(); i++) {
            columnValues[i] = values.get(columns.get(i));
        }

        sql = new StringBuffer();
        sql.append("insert into ");
        sql.append(table);
        sql.append(" (");
        sql.append(removeBrackets(columns.toString()));
        sql.append(") values (");
        sql.append(getArgs(columns.size()));
        sql.append(")");

        statement = connection.prepareStatement(sql.toString());

        try {
            DbUtil.setArguments(statement, columnValues);
            statement.execute();
        } finally {
            close(statement);
        }
    }


    private String getArgs(int count) {
        char args[];

        args = new char[count];
        Arrays.fill(args, '?');

        return removeBrackets(Arrays.toString(args));
    }
    private String removeBrackets(String value) {
        return value.substring(1, value.length() - 1);
    }
}
