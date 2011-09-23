package dk.fujitsu.utils.test.converter;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 21-09-11
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
public class PrimitiveIntegerConverter extends Converter {
    @Override
    Object toObject(String value) {
        return value.trim().isEmpty() ? 0 : Integer.parseInt(value);
    }
}
