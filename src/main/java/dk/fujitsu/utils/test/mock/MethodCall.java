package dk.fujitsu.utils.test.mock;


import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodCall {
    private Method method;
    private Object[] values;

    public MethodCall(Method method, Object[] values) {
        this.method = method;
        this.values = values;

        if (this.values == null) {
            this.values = new Object[0];
        }
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArguments() {
        return values;
    }

    public boolean matches(Method method, Object... values) {
        return this.method.equals(method) && Arrays.equals(this.values, values);
    }
}
