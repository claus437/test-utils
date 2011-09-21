package dk.fujitsu.utils.test.table;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 21-09-11
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 */
public class DataBase {
    private Map<String, TableDataProvider> tables;
    private String resource;

    public DataBase(String resource) {
        tables = new HashMap<String, TableDataProvider>();
        this.resource = resource;
    }

    public <T> TableDataProvider<T> getTable(Class<T> type, String table) {
        TableDataProvider<T> provider;

        provider = tables.get(table);
        if (provider == null) {
            provider = new TableDataProvider<T>(this, type, table, getInputStream(resource));
            tables.put(table, provider);
        }

        return provider;
    }

    private InputStream getInputStream(String resource) {
        return getClass().getClassLoader().getResourceAsStream(resource);
    }
}
