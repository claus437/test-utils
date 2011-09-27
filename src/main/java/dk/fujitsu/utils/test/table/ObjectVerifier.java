package dk.fujitsu.utils.test.table;

import dk.fujitsu.utils.test.Reflection;
import dk.fujitsu.utils.test.converter.Converter;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-09-11
 * Time: 18:39
 * To change this template use File | Settings | File Templates.
 */
public class ObjectVerifier implements CellReader {
    private static final Logger LOGGER = Logger.getLogger(ObjectVerifier.class);
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

        LOGGER.debug(
                "reading row: " + currentRowNo +
                " verify row:" + rowNo +
                " columnIndex + " + index +
                " columnWidth " + width +
                " columnName " + columnName +
                " columnValue " + columnValue);

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
            System.out.println("checking raw type: " + object.getClass());
            check(Converter.toObject(object.getClass(), columnValue), object);
            return;
        }


        field = Reflection.findField(object.getClass(), columnName);
        field.setAccessible(true);

        try {
            System.out.println("checking field: " + field.getType().getCanonicalName() + " " + field.getName());
            check(Converter.toObject(field.getType(), columnValue), field.get(object));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void check(Object expected, Object actual) {
        LOGGER.debug("asserting expected: " + expected + " actual: " + actual);

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
