package dk.fujitsu.utils.test.table;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 20-09-11
 * Time: 12:15
 * To change this template use File | Settings | File Templates.
 */
public interface CellReader {
    public void nextRow();
    public void read(String columnName, String columnValue);
}
