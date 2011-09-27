package dk.fujitsu.utils.test.mock;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 19-09-11
 * Time: 14:17
 * To change this template use File | Settings | File Templates.
 */
public class ResultProperties {
    private boolean repeat;

    public void repeat(boolean flag) {
        this.repeat = true;
    }

    boolean isRepeated() {
        return repeat;
    }
}
