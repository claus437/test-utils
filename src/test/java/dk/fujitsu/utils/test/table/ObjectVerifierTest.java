package dk.fujitsu.utils.test.table;

import dk.fujitsu.utils.test.Dimension;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.security.auth.Subject;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 26-09-11
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class ObjectVerifierTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ObjectVerifier subject;
    private Dimension actual;

    @Before
    public void setup() {
        actual = new Dimension();
        actual.setWidth(10);
        actual.setLength("20");
        actual.setDepth("30");

        subject = new ObjectVerifier(actual, 1);
    }

    @Test
    public void testFlatObjectOk() {
        subject.nextRow(2, 2);
        subject.read(0, 1, "width", "10");
        subject.read(1, 1, "length", "20");
    }

    @Test
    public void testFlatObjectOkBothNull() {
        actual.setLength(null);

        subject.nextRow(2, 2);
        subject.read(0, 1, "length", "");
    }


    @Test
    public void testFlatObjectIncorrect() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("expected:<20> but was: <10>");

        subject.nextRow(2, 2);
        subject.read(0, 1, "width", "20");
    }

    @Test
    public void testFlatObjectIncorrectActualNull() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("expected:<10> but was: <null>");

        actual.setLength(null);

        subject.nextRow(2, 2);
        subject.read(0, 1, "length", "10");
    }

    @Test
    public void testFlatObjectIncorrectExpectedNull() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("expected:<null> but was: <20>");

        subject.nextRow(2, 2);
        subject.read(0, 1, "length", "");
    }


    @Test
    public void testRawObjectOk() {
        subject = new ObjectVerifier("Hello", 1);
        subject.nextRow(2, 2);
        subject.read(1, 1, "value", "Hello");
    }

    @Test
    public void testRawObjectOkBothNull() {
        subject = new ObjectVerifier(null, 1);
        subject.nextRow(2, 2);
        subject.read(1, 1, "value", "");
    }

    @Test
    public void testRawObjectIncorrect() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("expected:<Hello> but was: <Hello2>");

        subject = new ObjectVerifier("Hello2", 1);
        subject.nextRow(2, 2);
        subject.read(1, 1, "value", "Hello");
    }

    @Test
    public void testRawObjectIncorrectExpectedNull() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("expected:<null> but was: <Hello>");

        subject = new ObjectVerifier("Hello", 1);
        subject.nextRow(2, 2);
        subject.read(1, 1, "value", "");
    }

    @Test
    public void testRawObjectIncorrectActualNull() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("expected:<Hello> but was: <null>");

        subject = new ObjectVerifier(null, 1);
        subject.nextRow(2, 2);
        subject.read(1, 1, "value", "Hello");
    }

    // ${items}
    public void testCompareList() {

    }

    // ${items[0]}
    public void testCompareChild() {

    }

    // 1
    public void testCompareReference() {

    }
}
