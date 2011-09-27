package dk.fujitsu.utils.test.converter;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 22-09-11
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
public class PrimitiveBooleanConverter extends Converter {
    @Override
    Object toObject(String value) {
        return value.equals("Y") || value.equals("1") || value.toLowerCase().equals("true");
    }

    @Override
    Object getInitValue() {
        return false;
    }
}
