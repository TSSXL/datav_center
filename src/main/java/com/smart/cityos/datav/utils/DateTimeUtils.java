package com.smart.cityos.datav.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: zsj
 * @date Created in 2017-4-21
 */
public class DateTimeUtils {

  public static Date toDate(String date) throws ParseException {

    SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return simpleDateFormat.parse(date);
  }

  public static String toString(Date date) {
    SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return simpleDateFormat.format(date);
  }

}
