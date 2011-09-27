package dk.fujitsu;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test() {
        String[] test;

        test = new String[10];
        Arrays.fill(test, "?");

        System.out.println(Arrays.toString(test));

    }

}
