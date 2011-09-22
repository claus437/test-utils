package dk.fujitsu.utils.test;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 22-09-11
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class ReflectionTest {

    @Test
    public void testFindDeclaredField() throws Exception {
        Assert.assertEquals(ExtendedDimension.class.getDeclaredField("weight"), Reflection.findField(ExtendedDimension.class, "weight"));
    }

    @Test
    public void testFindInheritedField() throws Exception {
        Assert.assertEquals(Dimension.class.getDeclaredField("width"), Reflection.findField(ExtendedDimension.class, "width"));
    }

    @Test
    public void testFindAbstractField() throws Exception {
        Assert.assertEquals(MyAbstractClass.class.getField("y"), Reflection.findField(MyConcreteClass.class, "y"));
    }

    public abstract class MyAbstractClass {
        public int y;
    }

    public class MyConcreteClass extends MyAbstractClass {
        public int x;
    }
}
