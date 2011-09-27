package dk.fujitsu.utils.test.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 27-09-11
 * Time: 18:21
 * To change this template use File | Settings | File Templates.
 */
public class DateConverter extends Converter {
    private static final String DATE_PATTERN = "yyyy.MM.dd";
    private static final String DATE_TIME_PATTERN = DATE_PATTERN + " HH:mm:ss";

    @Override
    Object toObject(String value) {
        String pattern;

        if (value.length() == DATE_PATTERN.length()) {
            pattern = DATE_PATTERN;
        } else if (value.length() == DATE_TIME_PATTERN.length()) {
            pattern = DATE_TIME_PATTERN;
        } else {
            throw new RuntimeException("invalid date " + value + " (" + DATE_PATTERN + " / " + DATE_TIME_PATTERN + ")");
        }

        try {
            return new SimpleDateFormat(pattern).parse(value);
        } catch (ParseException x) {
            throw new RuntimeException("failed converting date " + value + " (" + pattern + ")");
        }
    }

    @Override
    Object getInitValue() {
        return null;
    }
}
