package dk.fujitsu.utils.test;

import dk.fujitsu.utils.test.table.CellReader;
import dk.fujitsu.utils.test.table.TableReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TableTest implements CellReader {
    private TableReader subject;
    private List<String> cells;
    private int rowNo;

    @Before
    public void setup() {
        cells = new ArrayList<String>();
        subject = new TableReader(this);
    }

    @Test
    public void testReadLeftTable() {
        subject.read("dimensions", getClass().getClassLoader().getResourceAsStream("dk/fujitsu/utils/test/table/table.txt"));

        Assert.assertEquals(9, cells.size());
        Assert.assertEquals("1,width:10", cells.get(0));
        Assert.assertEquals("2,length:21", cells.get(4));
        Assert.assertEquals("3,depth:32", cells.get(8));
    }


    @Test
    public void testReadRightTable() {
        subject.read("expected", getClass().getClassLoader().getResourceAsStream("dk/fujitsu/utils/test/table/table.txt"));

        Assert.assertEquals(6, cells.size());
        Assert.assertEquals("1,square:6", cells.get(0));
        Assert.assertEquals("3,cubic:15", cells.get(5));
    }

    @Test
    public void testReadBottomTable() {
        subject.read("shapes", getClass().getClassLoader().getResourceAsStream("dk/fujitsu/utils/test/table/table.txt"));

        Assert.assertEquals(4, cells.size());
        Assert.assertEquals("1,id:1", cells.get(0));
        Assert.assertEquals("2,type:Piano", cells.get(3));
    }



    public void nextRow() {
        rowNo++;
    }

    public void read(String columnName, String columnValue) {
        cells.add(rowNo + "," + columnName +":" +columnValue);
    }
}
