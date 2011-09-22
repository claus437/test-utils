package dk.fujitsu.utils.test.converter;

import sun.java2d.SunGraphicsEnvironment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 21-09-11
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */
public abstract class Converter {
    private static final Map<Class, Converter> CONVERTERS = new HashMap<Class, Converter>();
    abstract Object toObject(String value);

    static {
        CONVERTERS.put(int.class, new PrimitiveIntegerConverter());
        CONVERTERS.put(String.class, new StringConverter());
    }

    public static boolean isConvertible(Class type) {
        return CONVERTERS.containsKey(type) || type.isEnum() || getStringConstructor(type) != null;
    }

    public static <T> T toObject(Class<T> type, String value) {
        Constructor<T> constructor;

        if (CONVERTERS.containsKey(type)) {
            return (T) CONVERTERS.get(type).toObject(value);
        }

        if (type.isEnum()) {
            return (T) createEnum(type, value);
        }

        constructor = getStringConstructor(type);
        if (constructor != null) {
            return createObject(constructor, value);
        }

        throw new RuntimeException("incorvertible object " + type.getCanonicalName() + " " + value);

    }

    private static <T> T createObject(Constructor<T> constructor, String value) {
        T object;

        try {
            object = constructor.newInstance(value);
        } catch (InvocationTargetException x) {
            throw new RuntimeException(x.getMessage(), x);
        } catch (InstantiationException x) {
            throw new RuntimeException(x.getMessage(), x);
        } catch (IllegalAccessException x) {
            throw new RuntimeException(x.getMessage(), x);
        }

        return object;
    }

    private static Object createEnum(Class type, String value) {
        return Enum.valueOf(type, value);
    }

    private static Constructor getStringConstructor(Class type) {
        try {
            return type.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}
