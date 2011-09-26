package dk.fujitsu.utils.test.table;

import javax.imageio.metadata.IIOInvalidTreeException;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 20-09-11
 * Time: 12:15
 * To change this template use File | Settings | File Templates.
 */
public interface CellReader {
    public void nextRow(int tableColumns, int rowColumns);
    public void read(int index, int width, String columnName, String columnValue);
}
