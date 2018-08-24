package com.smart.cityos.datav.utils;

import java.util.UUID;

/**
 * <p>title:令牌工具类</p>
 * <p>description:令牌工具类</p>
 *
 * @author: yubj
 * @date Created in 2017-4-17
 * @modified By zhengkai
 */
public class TokenUtils {

  /**
   * 令牌长度
   */
  private static final int TOKEN_LENGTH = 8;

  public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
      "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
      "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
      "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
      "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "TopicField", "U", "V",
      "W", "X", "Y", "Z"};

  /**
   * 生成令牌
   */
  public static String generateToken() {
    StringBuffer token = new StringBuffer();
    String uuid = UUID.randomUUID().toString().replace("-", "");
    for (int i = 0; i < TOKEN_LENGTH; i++) {
      String str = uuid.substring(i * 4, i * 4 + 4);
      int x = Integer.parseInt(str, 16);
      token.append(chars[x % 0x3E]);
    }
    return token.toString();
  }
}
