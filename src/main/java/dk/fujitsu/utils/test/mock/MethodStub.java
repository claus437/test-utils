package dk.fujitsu.utils.test.mock;

import dk.fujitsu.utils.test.table.DataProvider;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

public class MethodStub {
    private DataProvider dataProvider;
    private Method method;
    private Object result;
    private ResultProperties resultProperties;

    public MethodStub(Class mockedClass, DataProvider dataProvider, String methodName, Class... args) {

        this.resultProperties = new ResultProperties();
        this.dataProvider = dataProvider;

        try {
            method = mockedClass.getDeclaredMethod(methodName, args);
        } catch (NoSuchMethodException x) {
            throw new RuntimeException("class " + mockedClass.getCanonicalName() + " has no method matching " + methodName + " (" + Arrays.asList(args));
        }
    }

    public void resultList(String tableName, int... rows) {
        Class componentType;

        componentType = (Class) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
        result = dataProvider.getTable(componentType, tableName).readList(rows);
    }

    public ResultProperties result(String tableName, int row) {
        result = dataProvider.getTable(method.getReturnType(), tableName).readObject(row);

        return resultProperties;
    }

    public void result(Object object) {
        result = object;
    }

    Object getResult() {
        return result;
    }

    boolean isMethod(Method method) {
        return this.method.equals(method);
    }

    ResultProperties getResultProperties() {
        return resultProperties;
    }
}
