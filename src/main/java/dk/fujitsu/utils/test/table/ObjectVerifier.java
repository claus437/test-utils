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
    private int rowNo;
    private int currentRowNo;
    private int rowColumns;

    public ObjectVerifier(Object object, int rowNo) {
        this.object = object;
        this.rowNo = rowNo;
    }

    @Override
    public void nextRow(int tableColumns, int rowColumns) {
        this.currentRowNo ++;
        this.rowColumns = rowColumns;
    }

    @Override
    public void read(int index, int width, String columnName, String columnValue) {
        Field field;

        System.out.println("reading (" + rowNo + ":" + currentRowNo + ")" + index + " " + width + " " + columnName + " " + columnValue);
        if (rowNo != currentRowNo) {
            return;
        }

        if (object == null && columnValue.isEmpty()) {
            return;
        }

        if (object == null && !columnValue.isEmpty()) {
            throw new AssertionError("expected:<" + columnValue + "> but was: <null>");
        }

        if (Converter.isConvertible(object.getClass())) {
            check(Converter.toObject(object.getClass(),columnValue), object);
            return;
        }


        field = Reflection.findField(object.getClass(), columnName);
        field.setAccessible(true);

        try {
            check(Converter.toObject(field.getType(), columnValue), field.get(object));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void check(Object expected, Object actual) {
        System.out.println("checking " + expected + " " + actual);
        if (expected != null && actual == null) {
            throw new AssertionError("expected:<" + expected + "> but was: <" + actual + ">");
        }

        if (expected == null && actual != null) {
            throw new AssertionError("expected:<" + expected + "> but was: <" + actual + ">");
        }

        if (expected != null && ! expected.equals(actual)) {
            throw new AssertionError("expected:<" + expected + "> but was: <" + actual + ">");
        }
    }
}
