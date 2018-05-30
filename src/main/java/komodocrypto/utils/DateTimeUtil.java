package komodocrypto.utils;

public class DateTimeUtil {

    /**
     * Returns a string with the current time with the yyyyMMddHHmmss format.
     * Useful when storing historial data.
     * @return A string with the current date, from the year to the second.
     */
    public static String getCurrentDateTime(){
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyyMMddHHmmss");

        return sdf.format(dt);
    }
}
