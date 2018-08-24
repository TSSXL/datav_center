package com.smart.cityos.datav.utils;

/**
 * <p>title:字符串操作</p>
 * <p>description:字符串操作</p>
 *
 * @author:
 * @date Created in
 */
public class StringUtils {

  /**
   * 首字母大写
   */
  public static String firstCharToUpperCase(String str) {
    char[] chars = str.toCharArray();
    chars[0] -= 32;
    return String.valueOf(chars);
  }

  /**
   * 字符串是否为空
   */
  public static boolean stringIsNullOrEmpty(String str) {
    return str == null || str.trim().length() == 0;
  }

  public static Long conver2Long(String str) {
    return conver2Long(str, 0L);
  }

  public static Long conver2Long(String str, Long defVal) {
    try {
      Long val = Long.parseLong(str);
      return val;
    } catch (NumberFormatException ex) {
      return defVal;
    }
  }
}
