package dk.fujitsu.utils.test.table;

import dk.fujitsu.utils.test.Dimension;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TableAssertionRunnerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private DataBase db = new DataBase("dk/fujitsu/utils/test/table/TableAssertionRunnerTest.txt");

    public int calculateArea(Dimension dimension) {
        System.out.println("di: " + dimension.getWidth() + " " + dimension.getLength());
        return dimension.getWidth() * Integer.parseInt(dimension.getLength());
    }

    @Test
    public void testExpectationsOk() throws Throwable {
        TableAssertionRunner<Dimension, Integer> runner;

        runner = new TableAssertionRunner<Dimension, Integer>(
            db, Dimension.class, "calc-area", Integer.class, "exp.calc-area-ok") {
            @Override
            protected Integer execute(Dimension dimension) {
                return calculateArea(dimension);
            }
        };

        runner.run();
    }

    @Test
    public void testExpectationsIncorrect() throws Throwable {
        TableAssertionRunner<Dimension, Integer> runner;

        thrown.expect(AssertionError.class);
        thrown.expectMessage("expected:<3> but was: <2>");

        runner = new TableAssertionRunner<Dimension, Integer>(
            db, Dimension.class, "calc-area", Integer.class, "exp.calc-area-bad") {
            @Override
            protected Integer execute(Dimension dimension) {
                return calculateArea(dimension);
            }
        };

        runner.run();
    }
}