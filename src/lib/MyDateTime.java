package lib;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateTime {
    /*
     * Chuyển đỗi String sang Date với SimpleDateFormat
     */
    public static Date convertStringtoDate(String datetimeString, String format) {
        try {
            return new SimpleDateFormat(format).parse(datetimeString);
        } catch (Exception e) {
//            System.err.println(e);
            return null;
        }
    }

    /*
     * Chuyển đỗi Date sang String với SimpleDateFormat
     */
    public static String convertDatetoString(Date datetime, String format) {
        try {
            return new SimpleDateFormat(format).format(datetime);
        } catch (Exception e) {
//            System.err.println(e);
            return null;
        }
    }
}
