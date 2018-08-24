package com.smart.cityos.datav.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: yangqc
 * @date Created in 2017-4-10
 */
public class DateUtils {

  private static final String WEEK_RANGE = "week";

  private static final String MONTH_RANGE = "month";

  private static final String YEAR_RANGE = "year";

  /**
   * 整数常量
   */
  private static final int INT_THREE = 3;

  /**
   * 整数常量
   */
  private static final int INT_FOUR = 4;

  /**
   * 整数常量
   */
  private static final int INT_SIX = 6;

  /**
   * 整数常量
   */
  private static final int INT_SEVEN = 7;

  /**
   * 整数常量
   */
  private static final int INT_NINE = 9;

  /**
   * 整数常量
   */
  private static final int INT_TEN = 10;

  /**
   * 整数常量
   */
  private static final int INT_TWELVE = 12;

  /**
   * 获取当前月份所属季度的起始月
   */
  public static int getQuarterStartMonth(int currentMonth) {
    int startMonth;
    if (currentMonth >= 1 && currentMonth <= INT_THREE) {
      startMonth = 1;
    } else if (currentMonth >= INT_FOUR && currentMonth <= INT_SIX) {
      startMonth = 4;
    } else if (currentMonth >= INT_SEVEN && currentMonth <= INT_NINE) {
      startMonth = 7;
    } else if (currentMonth >= INT_TEN && currentMonth <= INT_TWELVE) {
      startMonth = 10;
    } else {
      throw new IllegalArgumentException(currentMonth + " 不属于正常月份!");
    }
    return startMonth;
  }

  /**
   * 获取当前月所在季度的截止月
   */
  public static int getQuarterEndMonth(int currentMonth) {
    int endMonth;
    if (currentMonth >= 1 && currentMonth <= INT_THREE) {
      endMonth = 3;
    } else if (currentMonth >= INT_FOUR && currentMonth <= INT_SIX) {
      endMonth = 6;
    } else if (currentMonth >= INT_SEVEN && currentMonth <= INT_NINE) {
      endMonth = 9;
    } else if (currentMonth >= INT_TEN && currentMonth <= INT_TWELVE) {
      endMonth = 12;
    } else {
      throw new IllegalArgumentException(currentMonth + " 不属于正常月份!");
    }
    return endMonth;
  }

  /**
   * 获取某年，某月所在季度的总天数
   */
  public static int getQuarterDaysByDate(int month, int year) {
    if (month <= 0 || month > INT_TWELVE || year < 1) {
      throw new IllegalArgumentException("参数不在规定范围!");
    }
    int beginMonth = getQuarterStartMonth(month);
    return getMonthDays(beginMonth, year) + getMonthDays(beginMonth + 1, year) + getMonthDays(
        beginMonth + 2, year);
  }

  /**
   * 获取某年，某月的具体天数
   */
  public static int getMonthDays(int month, int year) {
    if (month <= 0 || month > INT_TWELVE || year < 1) {
      throw new IllegalArgumentException("参数不在规定范围!");
    }
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month - 1);
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  /**
   * 获取特定范围时间
   *
   * @param rangeName week或者month或者year
   */
  public static Date[] getDateRange(String rangeName, Date endDate) {
    if (null == rangeName) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(endDate);
    Date beginDate = endDate;
    switch (rangeName) {
      case WEEK_RANGE:
        long currentMillis = calendar.getTimeInMillis();
        long beforeMillis = currentMillis - 7 * 24 * 60 * 60 * 1000;
        beginDate = new Date(beforeMillis);
        break;
      case MONTH_RANGE:
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        beginDate = calendar.getTime();
        break;
      case YEAR_RANGE:
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        int beginMonth = currentMonth - 6 < 0 ? currentMonth - 6 + 11 : currentMonth - 6;
        int beginYear = currentMonth - 6 < 0 ? --currentYear : currentYear;
        calendar.set(Calendar.MONTH, beginMonth);
        calendar.set(Calendar.YEAR, beginYear);
        beginDate = calendar.getTime();
        break;
      default:
        throw new RuntimeException(
            "参数不符要求！规定参数值['" + WEEK_RANGE + "' , '" + MONTH_RANGE + "' , '" + YEAR_RANGE + "']");
    }
    return new Date[]{beginDate, endDate};
  }
}
