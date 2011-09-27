package dk.fujitsu.utils.test.converter;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 21-09-11
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class StringConverter extends Converter {
    @Override
    Object toObject(String value) {
        return value.trim().isEmpty() ? null : value;
    }

    @Override
    Object getInitValue() {
        return null;
    }
}
