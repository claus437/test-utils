package dk.fujitsu.utils.test;

import dk.fujitsu.utils.test.table.CellReader;
import dk.fujitsu.utils.test.table.TableReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TableReaderTest implements CellReader {
    private TableReader subject;
    private List<String> cells;
    private List<String> rows;

    @Before
    public void setup() {
        cells = new ArrayList<String>();
        rows = new ArrayList<String>();
        subject = new TableReader(this);
    }

    @Test
    public void testReadLeftTable() {
        subject.read("dimensions", getClass().getClassLoader().getResourceAsStream("dk/fujitsu/utils/test/table/table.txt"));

        Assert.assertEquals(9, cells.size());
        Assert.assertEquals("#1:0:1:width=10", cells.get(0));
        Assert.assertEquals("#2:1:1:length=21", cells.get(4));
        Assert.assertEquals("#3:2:1:depth=32", cells.get(8));
    }


    @Test
    public void testReadRightTable() {
        subject.read("expected", getClass().getClassLoader().getResourceAsStream("dk/fujitsu/utils/test/table/table.txt"));

        Assert.assertEquals(6, cells.size());
        Assert.assertEquals("#1:0:1:square=6", cells.get(0));
        Assert.assertEquals("#3:1:1:cubic=15", cells.get(5));
    }

    @Test
    public void testReadBottomTable() {
        subject.read("shapes", getClass().getClassLoader().getResourceAsStream("dk/fujitsu/utils/test/table/table.txt"));

        Assert.assertEquals(4, cells.size());
        Assert.assertEquals("#1:0:1:id=1", cells.get(0));
        Assert.assertEquals("#2:1:1:name=Piano", cells.get(3));
    }

    @Test
    public void testReadSpanCell() {
        subject.read("span-dimensions", getClass().getClassLoader().getResourceAsStream("dk/fujitsu/utils/test/table/table.txt"));

        Assert.assertEquals(7, cells.size());
        Assert.assertEquals("#1:0:1:width=10", cells.get(0));
        Assert.assertEquals("#1:1:1:length=20", cells.get(1));
        Assert.assertEquals("#1:2:1:depth=30", cells.get(2));
        Assert.assertEquals("#2:0:3:width=", cells.get(3));
        Assert.assertEquals("#3:0:1:width=11", cells.get(4));
        Assert.assertEquals("#3:1:1:length=21", cells.get(5));
        Assert.assertEquals("#3:2:1:depth=31", cells.get(6));

        Assert.assertEquals("#0:3/3", rows.get(0));
        Assert.assertEquals("#1:3/1", rows.get(1));
        Assert.assertEquals("#2:3/3", rows.get(2));
    }

    public void nextRow(int tableColumns, int rowColumns) {
        rows.add("#" + rows.size() + ":" + tableColumns + "/" + rowColumns);
    }

    public void read(int index, int width, String columnName, String columnValue) {
        cells.add("#" + rows.size() + ":" + index + ":" + width + ":"  + columnName + "=" +columnValue);
    }
}
