package dk.fujitsu.utils.test;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 26-09-11
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public class IOUtil {

    public static InputStream getResource(String resource) {
        InputStream stream;

        stream = IOUtil.class.getClassLoader().getResourceAsStream(resource);
        if (stream == null) {
            throw new RuntimeException("found no resource named " + resource);
        }

        return stream;
    }
}
