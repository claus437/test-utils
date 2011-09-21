package dk.fujitsu.utils.test.table;

import dk.fujitsu.utils.test.Dimension;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 21-09-11
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public class TableDataProviderTest {
    private TableDataProvider<Dimension> subject;

    @Before
    public void setup() {
        subject = new TableDataProvider<Dimension>(new DataBase(""), Dimension.class, "dimension", getClass().getClassLoader().getResourceAsStream("dk/fujitsu/utils/test/table/table.txt"));
    }

    @Test
    public void testReadObject() {
        Dimension dimension;

        dimension = subject.readObject(0);
        Assert.assertEquals(10, dimension.getWidth());
        Assert.assertEquals("20", dimension.getLength());
        Assert.assertEquals("30", dimension.getDepth());
    }

    @Test
    public void testReadFullList() {
        List<Dimension> dimensionList;

        dimensionList = subject.readList();
        Assert.assertEquals(3, dimensionList.size());
        Assert.assertEquals(10, dimensionList.get(0).getWidth());
        Assert.assertEquals(11, dimensionList.get(1).getWidth());
        Assert.assertEquals(12, dimensionList.get(2).getWidth());
    }

    @Test
    public void testReadPartialList() {
        List<Dimension> dimensionList;

        dimensionList = subject.readList(0, 2);
        Assert.assertEquals(2, dimensionList.size());
        Assert.assertEquals(10, dimensionList.get(0).getWidth());
        Assert.assertEquals(12, dimensionList.get(1).getWidth());
    }

}
