package dk.fujitsu.utils.test.table;

import dk.fujitsu.utils.test.IOUtil;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 26-09-11
 * Time: 09:22
 * To change this template use File | Settings | File Templates.
 */
public class TableObjectVerifier {
    private String resource;

    public TableObjectVerifier(String resource) {
        this.resource = resource;
    }

    public void verify(Object object, String table, int rowNo) {
        TableReader reader;
        ObjectVerifier verifier;

        verifier = new ObjectVerifier(object, rowNo);

        reader = new TableReader(verifier);
        reader.read(table, IOUtil.getResource(resource));
    }
}
