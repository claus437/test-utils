package dk.fujitsu.utils.test.converter;

import sun.java2d.SunGraphicsEnvironment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
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
    abstract Object getInitValue();

    static {
        CONVERTERS.put(boolean.class, new PrimitiveBooleanConverter());
        CONVERTERS.put(char.class, new PrimitiveCharConverter());
        CONVERTERS.put(double.class, new PrimitiveDoubleConverter());
        CONVERTERS.put(int.class, new PrimitiveIntegerConverter());
        CONVERTERS.put(long.class, new PrimitiveLongConverter());
        CONVERTERS.put(String.class, new StringConverter());
        CONVERTERS.put(Date.class, new DateConverter());
    }

    public static boolean isConvertible(Class type) {
        return CONVERTERS.containsKey(type) || type.isEnum();
    }

    public static Object getInitialValue(Class type) {
        return type.isPrimitive() ? CONVERTERS.get(type).getInitValue() : null;
    }

    public static <T> T toObject(Class<T> type, String value) {
        Constructor<T> constructor;

        if (CONVERTERS.containsKey(type)) {
            return (T) CONVERTERS.get(type).toObject(value);
        }

        if (value.trim().isEmpty()) {
            return null;
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
        try {
            return Enum.valueOf(type, value);
        } catch (Throwable x) {
            throw new RuntimeException("failed converting \"" + value + "\" to " + type.getCanonicalName());
        }
    }

    private static Constructor getStringConstructor(Class type) {
        try {
            return type.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}
