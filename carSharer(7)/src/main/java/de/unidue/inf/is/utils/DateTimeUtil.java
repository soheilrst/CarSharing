package de.unidue.inf.is.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    static String DB2_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";


    /**
     * @param datetimeStr DB2 datetime format. Example: 2020-12-28 15:45:00.000000
     * @return Time string. Format: HH:mm, Example: 15:45
     */
    public static String extractTimeFromDB2DateTimeString(String datetimeStr) {
        String timeStr = null;
        try {
            Date parsedDate = new SimpleDateFormat(DB2_DATE_FORMAT).parse(datetimeStr);
            timeStr = new SimpleDateFormat("HH:mm").format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStr;
    }


    /**
     * @param datetimeStr DB2 datetime format. Example: 2020-12-28 15:45:00.000000
     * @return Date string. Format: dd.MM.yyyy, Example: 28.12.2020
     */
    public static String extractDateFromDB2DateTimeString(String datetimeStr) {
        String dateStr = null;
        try {
            Date parsedDate = new SimpleDateFormat(DB2_DATE_FORMAT).parse(datetimeStr);
            dateStr = new SimpleDateFormat("dd.MM.yyyy").format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }


    /**
     * @param dateStr Date as string. Format dd.MM.yyyy, Example: 28.12.2020
     * @param timeStr Time as string. Format HH:mm, Example: 15:45
     * @return DB2 datetime format (e.g 2020-12-28 15:45:00.000000)
     */
    public static String convertDateAndTimeToDB2DateTime(String dateStr, String timeStr) {
        String datetimeStr = null;
        try {
            Date parsedDate = new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
            Date parsedTime = new SimpleDateFormat("HH:mm").parse(timeStr);
            datetimeStr = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate) + " " + new SimpleDateFormat("HH:mm:ss.SSSSSS").format(parsedTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datetimeStr;
    }

    public static void main(String[] args) {
        System.out.println(extractDateFromDB2DateTimeString("2022-03-12 08:00:00.000000"));
        System.out.println(extractTimeFromDB2DateTimeString("2022-02-02 08:00:00.000000"));
        System.out.println(convertDateAndTimeToDB2DateTime("15.04.2022", "12:15"));
    }
}
