package dk.fujitsu.utils.test.table;

import dk.fujitsu.utils.test.Dimension;
import dk.fujitsu.utils.test.Figure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 21-09-11
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
public class ObjectMapperTest {


    @Test
    public void testSetValue() {
        ObjectMapper<Dimension> subject = new ObjectMapper<Dimension>(new DataBase(""), Dimension.class);

        subject.nextRow();
        subject.read("length", "10");
        subject.nextRow();
        subject.read("length", "11");

        Assert.assertEquals("10", subject.getObjectList().get(0).getLength());
        Assert.assertEquals("11", subject.getObjectList().get(1).getLength());
        Assert.assertEquals(null, subject.getObjectList().get(0).getDepth());
    }

    @Test
    public void testNestedObject() {
        DataBase dataBase;

        dataBase = new DataBase("dk/fujitsu/utils/test/table/table.txt");
        ObjectMapper<Figure> subject = new ObjectMapper<Figure>(dataBase, Figure.class);

        subject.nextRow();
        subject.read("id", "10");
        subject.read("dimension", "${dimensions[0]}");

        Assert.assertEquals(10, subject.getObjectList().get(0).getId());
        Assert.assertEquals("20", subject.getObjectList().get(0).getDimension().getLength());
    }

    @Test
    public void testListObject() {
        DataBase dataBase;

        dataBase = new DataBase("dk/fujitsu/utils/test/table/table.txt");
        ObjectMapper<Figure> subject = new ObjectMapper<Figure>(dataBase, Figure.class);

        subject.nextRow();
        subject.read("id", "10");
        subject.read("shapes", "${shapes}");

        Assert.assertEquals(10, subject.getObjectList().get(0).getId());
        Assert.assertEquals(2, subject.getObjectList().get(0).getShapes().size());
        Assert.assertEquals("Chair", subject.getObjectList().get(0).getShapes().get(0).getName());
    }

    @Test
    public void temp() {
        Pattern pattern = Pattern.compile("\\W*\\$\\{(.*)\\[(\\d+)\\]\\}\\W*");
        String test = "${dimensions[0]}";

        Matcher matcher = pattern.matcher(test);
         matcher.find();
        System.out.println(matcher.group(1) + " " + matcher.group(2));
    }

}