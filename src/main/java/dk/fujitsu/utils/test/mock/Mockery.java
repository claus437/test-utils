package dk.fujitsu.utils.test.mock;

import dk.fujitsu.utils.test.converter.Converter;
import dk.fujitsu.utils.test.table.DataProvider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 27-09-11
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class Mockery<T> implements InvocationHandler {
    private List<MethodStub> stubs;
    private Class<T> mockedClass;
    private T mockedInstance;
    private DataProvider dataProvider;
    private List<MethodCall> calls;

    @SuppressWarnings("unchecked")
    public Mockery(Class<T> mockedClass, DataProvider dataProvider) {
        this.mockedClass = mockedClass;
        this.mockedInstance = (T) Proxy.newProxyInstance(mockedClass.getClassLoader(), new Class[]{mockedClass}, this);

        stubs = new ArrayList<MethodStub>();
        this.dataProvider = dataProvider;
        calls = new ArrayList<MethodCall>();
    }

    public void clear() {
        stubs.clear();
        calls.clear();
    }

    public MethodStub addStub(String methodName, Class... argumentTypes) {
        MethodStub methodStub;

        methodStub = new MethodStub(mockedClass, dataProvider, methodName, argumentTypes);
        stubs.add(methodStub);

        return methodStub;
    }

    public boolean hasStub(String methodName, Class... argumentTypes) {
        Method method;

        try {
            method = mockedClass.getDeclaredMethod(methodName, argumentTypes);
        } catch (NoSuchMethodException x) {
            throw new RuntimeException(x.getMessage(), x);
        }

        for (MethodStub stub : stubs) {
            if (stub.isMethod(method)) {
                return true;
            }
        }

        return false;
    }

    public MethodStub setStub(String methodName, Class... argumentTypes) {
        Method method;

        try {
            method = mockedClass.getDeclaredMethod(methodName, argumentTypes);
        } catch (NoSuchMethodException x) {
            throw new RuntimeException(x.getMessage(), x);
        }

        for (MethodStub stub : stubs) {
            if (stub.isMethod(method)) {
                return stub;
            }
        }

        return null;
    }

    public T getInstance() {
        return mockedInstance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodStub methodStub;

        calls.add(new MethodCall(method, args));

        for (int i = 0; i < stubs.size(); i++) {
            if (stubs.get(i).isMethod(method)) {
                if (stubs.get(i).getResultProperties().isRepeated()) {
                    methodStub = stubs.get(i);
                } else {
                    methodStub = stubs.remove(i);
                }
                return methodStub.getResult();
            }
        }

        if (method.getReturnType() == void.class) {
            return void.class;
        } else {
            return Converter.getInitialValue(method.getReturnType());
        }
    }

    Class getMockedClass() {
        return mockedClass;
    }

    public MethodCallCalculator getCalls(String methodName, Class... args) {
        Method method;

        try {
            method = mockedClass.getDeclaredMethod(methodName, args);
        } catch (NoSuchMethodException x) {
            throw new RuntimeException("found no method named " + methodName + ", " + x.getMessage(), x);
        }

        return new MethodCallCalculator(calls, method);
    }
}
