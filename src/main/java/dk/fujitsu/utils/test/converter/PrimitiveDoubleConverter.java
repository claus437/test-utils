package dk.fujitsu.utils.test.converter;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 22-09-11
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
public class PrimitiveDoubleConverter extends Converter {
    @Override
    Object toObject(String value) {
        return value.trim().isEmpty() ? 0 : Double.parseDouble(value);
    }

    @Override
    Object getInitValue() {
        return 0D;
    }
}
