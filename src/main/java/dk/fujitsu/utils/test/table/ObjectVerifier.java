package dk.fujitsu.utils.test.table;

import dk.fujitsu.utils.test.Reflection;
import dk.fujitsu.utils.test.converter.Converter;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-09-11
 * Time: 18:39
 * To change this template use File | Settings | File Templates.
 */
public class ObjectVerifier implements CellReader {
    private Object object;

    public ObjectVerifier(Object object) {
        this.object = object;
    }

    @Override
    public void nextRow(int tableColumns, int rowColumns) {

    }

    @Override
    public void read(int index, int width, String columnName, String columnValue) {
        Field field;

        field = Reflection.findField(object.getClass(), columnName);

        try {
            check(field.get(object), Converter.toObject(field.getClass(), columnName));
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void check(Object a, Object b) {

    }


}
