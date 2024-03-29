package dk.fujitsu.utils.test.table;

import dk.fujitsu.utils.test.Dimension;
import dk.fujitsu.utils.test.ExtendedDimension;
import dk.fujitsu.utils.test.Figure;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 21-09-11
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
public class ObjectMapperTest {
    public int myField;

    @Test
    public void testSetValue() {
        ObjectMapper<Dimension> subject = new ObjectMapper<Dimension>(new DataProvider(""), Dimension.class);

        subject.nextRow(1, 1);
        subject.read(0, 1, "length", "10");
        subject.nextRow(1, 1);
        subject.read(0, 1, "length", "11");

        Assert.assertEquals("10", subject.getObjectList().get(0).getLength());
        Assert.assertEquals("11", subject.getObjectList().get(1).getLength());
        Assert.assertEquals(null, subject.getObjectList().get(0).getDepth());
    }

    @Test
    public void testNestedObject() {
        DataProvider dataBase;

        dataBase = new DataProvider("dk/fujitsu/utils/test/table/table.txt");
        ObjectMapper<Figure> subject = new ObjectMapper<Figure>(dataBase, Figure.class);

        subject.nextRow(3, 3);
        subject.read(0, 1, "id", "10");
        subject.read(0, 1, "dimension", "${dimensions[0]}");

        Assert.assertEquals(10, subject.getObjectList().get(0).getId());
        Assert.assertEquals("20", subject.getObjectList().get(0).getDimension().getLength());

        subject.nextRow(3, 3);
        subject.read(0, 1, "id", "11");
        subject.read(0, 1, "dimension", "${dimensions[1]}");

        Assert.assertEquals(11, subject.getObjectList().get(1).getId());
        Assert.assertEquals("21", subject.getObjectList().get(1).getDimension().getLength());
    }

    @Test
    public void testListObject() {
        DataProvider dataBase;

        dataBase = new DataProvider("dk/fujitsu/utils/test/table/table.txt");
        ObjectMapper<Figure> subject = new ObjectMapper<Figure>(dataBase, Figure.class);

        subject.nextRow(2, 2);
        subject.read(0, 1, "id", "10");
        subject.read(0, 1, "shapes", "${shapes}");

        Assert.assertEquals(10, subject.getObjectList().get(0).getId());
        Assert.assertEquals(2, subject.getObjectList().get(0).getShapes().size());
        Assert.assertEquals("Chair", subject.getObjectList().get(0).getShapes().get(0).getName());
    }

    @Test
    public void testMergedTables() {
        DataProvider dataBase;

        dataBase = new DataProvider("dk/fujitsu/utils/test/table/table.txt");
        ObjectMapper<ExtendedDimension> subject = new ObjectMapper<ExtendedDimension>(dataBase, ExtendedDimension.class);

        subject.nextRow(4, 4);
        subject.read(0, 1, "${dimension}", "0");
        subject.read(0, 1, "weight", "100");

        Assert.assertEquals(10, subject.getObjectList().get(0).getWidth());
        Assert.assertEquals("20", subject.getObjectList().get(0).getLength());
        Assert.assertEquals("30", subject.getObjectList().get(0).getDepth());
        Assert.assertEquals("100", subject.getObjectList().get(0).getWeight());
    }

    @Test
    public void testSpannedNullObject() {
        DataProvider dataBase;

        dataBase = new DataProvider("dk/fujitsu/utils/test/table/table.txt");
        ObjectMapper<Dimension> subject = new ObjectMapper<Dimension>(dataBase, Dimension.class);

        subject.nextRow(3, 1);
        subject.read(0, 3, "width", "");

        Assert.assertEquals(null, subject.getObjectList().get(0));
    }

    @Test
    public void testNullObject() {
        DataProvider dataBase;

        dataBase = new DataProvider("dk/fujitsu/utils/test/table/table.txt");
        ObjectMapper<Dimension> subject = new ObjectMapper<Dimension>(dataBase, Dimension.class);

        subject.nextRow(1, 1);
        subject.read(0, 1, "string", "");

        Assert.assertEquals(null, subject.getObjectList().get(0));
    }

}
