package com.smart.cityos.datav.utils;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: zsj
 * @date Created in 2017-4-13
 */
public class FileHasher {

  /**
   * 如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
   */
  private static final String MD_ALGORITHM = "MD5";

  public String getMD5Checksum(InputStream is) throws Exception {
    byte[] b = readBytesByDis(is);
    is.close();
    return bytesToHex(b);
  }

  private byte[] readBytesByDis(InputStream is) throws Exception {
    MessageDigest md = MessageDigest.getInstance(MD_ALGORITHM);
    DigestInputStream dis = new DigestInputStream(is, md);
    int numRead = 0;
    do {
      numRead = dis.read();
    } while (numRead > -1);
    return md.digest();
  }

  private String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder();
    for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

}
