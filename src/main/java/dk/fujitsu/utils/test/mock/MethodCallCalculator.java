package dk.fujitsu.utils.test.mock;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodCallCalculator {
    private List<MethodCall> callList;
    private Method method;

    MethodCallCalculator(List<MethodCall> callList, Method method) {
        this.callList = callList;
        this.method = method;
    }

    public int count(Object... args) {
        int count;

        count = 0;

        for (MethodCall call : callList) {
            if (call.matches(method, args)) {
                count ++;
            }
        }

        return count;
    }

    public List<MethodCall> asList() {
        List<MethodCall> callList;

        callList = new ArrayList<MethodCall>();

        for (MethodCall aCallList : this.callList) {
            if (this.method.equals(aCallList.getMethod())) {
                callList.add(aCallList);
            }
        }

        return callList;
    }
}
