package dk.fujitsu.utils.test.converter;

import dk.fujitsu.utils.test.Figure;
import dk.fujitsu.utils.test.FigureType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 22-09-11
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public class ConverterTest {

    @Test
    public void testEnum() {
        Assert.assertEquals(true, Converter.isConvertible(FigureType.class));
        Assert.assertEquals(FigureType.TRIANGLE, Converter.toObject(FigureType.class, "TRIANGLE"));
    }
}
