package dk.fujitsu.utils.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 22-09-11
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class Reflection {

    public static Field findField(Class type, String name) {
        Field field;
        Class superClass;

        try {
            field = type.getDeclaredField(name);
        } catch (NoSuchFieldException x) {
            superClass = type.getSuperclass();
            if (superClass == null) {
                return null;
            }

            field = findField(type.getSuperclass(), name);
        }

        return field;
    }

    public static Field[] findFields(Class type) {
        Field[] declaredFields;
        List<Field> fields;
        Class superType;

        fields = new ArrayList<Field>();
        superType = type;

        do {
            declaredFields = superType.getDeclaredFields();

            for (Field declaredField : declaredFields) {
                if (!hasField(declaredField.getName(), fields)) {
                    fields.add(declaredField);
                }
            }

            superType = superType.getSuperclass();
        } while (superType != null);

        return fields.toArray(new Field[fields.size()]);
    }

    public static boolean hasField(String name, List<Field> fields) {
        for (Field field : fields) {
            if (field.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}
