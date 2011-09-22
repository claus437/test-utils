package dk.fujitsu.utils.test.table;

import dk.fujitsu.utils.test.converter.Converter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectMapper<T> implements CellReader {
    private static final Pattern OBJECT_REFERENCE = Pattern.compile("\\W*\\$\\{(.*)\\[(\\d+)\\]\\}\\W*");
    // TODO: rename as also used for table merge
    private static final Pattern OBJECT_LIST_REFERENCE = Pattern.compile("\\W*\\$\\{(.*)\\}\\W*");

    private List<T> objectList;
    private T object;
    private Class<T> type;
    private DataBase dataBase;
    private boolean singleColumn;

    public ObjectMapper(DataBase dataBase, Class<T> type) {
        objectList = new ArrayList<T>();
        this.type = type;
        this.dataBase = dataBase;

        if (Converter.isConvertible(type)) {
            singleColumn = true;
        }
    }

    public void nextRow() {
        if (!singleColumn) {
            object = createObject();
            objectList.add(object);
        }
    }

    public void read(String columnName, String columnValue) {
        Matcher matcher;

        if (singleColumn) {
            object = Converter.toObject(type, columnValue);
            objectList.add(object);
            return;
        }

        matcher = OBJECT_LIST_REFERENCE.matcher(columnName);
        if (matcher.find()) {
            setObject(matcher.group(1), Integer.parseInt(columnValue));
            return;
        }

        matcher = OBJECT_REFERENCE.matcher(columnValue);
        if (matcher.find()) {
            setObject(columnName, matcher.group(1), Integer.parseInt(matcher.group(2)));
            return;
        }

        matcher = OBJECT_LIST_REFERENCE.matcher(columnValue);
        if (matcher.find()) {
            setListObject(columnName, matcher.group(1));
            return;
        }

        setValue(columnName, columnValue);
    }

    public List<T> getObjectList() {
        return objectList;
    }

    private T createObject() {
        try {
            return type.newInstance();
        } catch (InstantiationException x) {
            throw new RuntimeException(x.getMessage());
        } catch (IllegalAccessException x) {
            throw new RuntimeException(x.getMessage());
        }
    }

    private void setObject(String tableName, int row) {
        Object foreignObject;
        Object value;
        Field[] fields;

        try {
            foreignObject = dataBase.getTable(type, tableName).readObject(row);
            fields = type.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                value = field.get(foreignObject);
                field.set(object, value);
            }
        } catch (IllegalAccessException x) {
            throw new RuntimeException(x.getMessage(), x);
        }
    }

    private void setListObject(String fieldName, String tableName) {
        Field field;
        Object o;

        try {
            field = type.getDeclaredField(fieldName);
            field.setAccessible(true);

            o = dataBase.getTable(getComponentType(field), tableName).readList();
            field.set(object, o);
        } catch (NoSuchFieldException x) {
            throw new RuntimeException(x.getMessage(), x);
        } catch (IllegalAccessException x) {
            throw new RuntimeException(x.getMessage(), x);
        }
    }

    private void setObject(String fieldName, String tableName, int rowNo) {
        Field field;
        Object o;

        try {
            field = type.getDeclaredField(fieldName);
            field.setAccessible(true);

            o = dataBase.getTable(field.getType(), tableName).readObject(rowNo);
            field.set(object, o);
        } catch (NoSuchFieldException x) {
            throw new RuntimeException(x.getMessage(), x);
        } catch (IllegalAccessException x) {
            throw new RuntimeException(x.getMessage(), x);
        }
    }

    private void setValue(String fieldName, String value) {
        Field field;
        Object o;

        try {
            field = type.getDeclaredField(fieldName);
            field.setAccessible(true);

            o = Converter.toObject(field.getType(), value);
            field.set(object, o);
        } catch (NoSuchFieldException x) {
            throw new RuntimeException(x.getMessage(), x);
        } catch (IllegalAccessException x) {
            throw new RuntimeException(x.getMessage(), x);
        }
    }

    private Class getComponentType(Field field) {
        return (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
    }
}
