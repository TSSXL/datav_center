package com.smart.cityos.datav.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: yangqc
 * @date Created in 2017-7-2
 */
@Slf4j
public class JsonSerializer {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static <T> String serialize(T t) {
    try {
      return OBJECT_MAPPER.writeValueAsString(t);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }
    return "";
  }

  public static <T> T deserialize(String bytes, Class<T> clazz) {
    try {
      return OBJECT_MAPPER.readValue(bytes, clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
