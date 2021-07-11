package com.nashtech.ecommerce_webapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertUtils {

    private static final String PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat format = new SimpleDateFormat(PATTERN);

    public static Date longToDate(long longDate){
        return new Date(longDate);
    }

    public static String dateToString(Date date){
        return format.format(date);
    }

    public static Date stringToDate(String dateString) throws ParseException {
        return format.parse(dateString);
    }
}
