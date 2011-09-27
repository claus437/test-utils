package dk.fujitsu.utils.test.converter;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-09-11
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */
public class PrimitiveCharConverter extends Converter{
    @Override
    Object toObject(String value) {
        return value.isEmpty() ? 0 : value.charAt(0);
    }

    @Override
    Object getInitValue() {
        return (char) 0;
    }
}
