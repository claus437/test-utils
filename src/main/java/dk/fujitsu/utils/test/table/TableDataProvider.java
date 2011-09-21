package dk.fujitsu.utils.test.table;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TableDataProvider<T> {
    private List<T> objectList;


    public TableDataProvider(DataBase database, Class<T> type, String tableName, InputStream resource) {
        TableReader reader;
        ObjectMapper<T> mapper;

        mapper = new ObjectMapper<T>(database, type);
        reader = new TableReader(mapper);

        reader.read(tableName, resource);

        objectList = mapper.getObjectList();
    }

    public T readObject(int row) {
        return objectList.get(row);
    }

    public List<T> readList() {
        return objectList;
    }

    public List<T> readList(int... rows) {
        List<T> subList;

        subList = new ArrayList<T>();
        for (int row : rows) {
            subList.add(objectList.get(row));
        }

        return subList;
    }
}
