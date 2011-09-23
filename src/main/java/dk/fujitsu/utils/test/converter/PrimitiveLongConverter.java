package dk.fujitsu.utils.test.converter;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 22-09-11
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public class PrimitiveLongConverter extends Converter {
    @Override
    Object toObject(String value) {
        return value.trim().isEmpty() ? 0 : Long.parseLong(value);
    }
}
