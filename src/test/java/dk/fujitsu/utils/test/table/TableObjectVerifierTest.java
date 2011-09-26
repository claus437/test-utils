package dk.fujitsu.utils.test.table;

import dk.fujitsu.utils.test.Dimension;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 26-09-11
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
public class TableObjectVerifierTest {
    private TableObjectVerifier subject;

    @Test
    public void testVerifyExpectedTable() {
        Dimension dimension;

        subject = new TableObjectVerifier("dk/fujitsu/utils/test/table/TableObjectVerifierTest.txt");

        dimension = new Dimension();
        dimension.setWidth(10);
        dimension.setLength("20");
        subject.verify(dimension, "dimension", 1);

        dimension.setWidth(11);
        dimension.setLength("21");
        subject.verify(dimension, "dimension", 2);
    }
}
