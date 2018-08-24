package com.smart.cityos.datav.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.Optional;

/**
 * <p>title:rest接口帮助类</p>
 * <p>description:rest接口帮助类</p>
 *
 * @author:
 * @date Created in
 */
@Slf4j
public class RestUtils {

  /**
   * 当前应用名称, 用于生成响应头
   */
  private static final String APPLICATION_NAME = "sc";
  private static final String MESSAGE_NAME = "message";

  /**
   * 生成操作提示响应头
   *
   * @param message 消息
   * @param param 参数
   */
  public final static HttpHeaders createAlert(String message, String param) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-" + APPLICATION_NAME + "-alert", message);
    headers.add("X-" + APPLICATION_NAME + "-params", param);
    return headers;
  }

  /**
   * 生成生成操作响应头
   *
   * @param entityName 实体
   * @param param 参数
   */
  public final static HttpHeaders createEntityCreationAlert(String entityName, String param) {
    return createAlert(APPLICATION_NAME + "." + entityName + ".created", param);
  }

  /**
   * 生成更新操作响应头
   *
   * @param entityName 实体
   * @param param 参数
   */
  public final static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
    return createAlert(APPLICATION_NAME + "." + entityName + ".updated", param);
  }

  /**
   * 生成删除操作响应头
   *
   * @param entityName 实体
   * @param param 参数
   */
  public final static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
    return createAlert(APPLICATION_NAME + "." + entityName + ".deleted", param);
  }

  /**
   * 生成获取操作响应头
   *
   * @param entityName 实体
   * @param param 参数
   */
  public final static HttpHeaders createEntityGetAlert(String entityName, String param) {
    return createAlert(APPLICATION_NAME + "." + entityName + ".get", param);
  }

  /**
   * 生成操作失败响应头
   *
   * @param entityName 实体
   * @param errorKey 参数
   * @param defaultMessage 错误消息
   */
  public final static HttpHeaders createFailureAlert(String entityName, String errorKey,
      String defaultMessage) {
    log.error("生成操作失败, {}", defaultMessage);
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-" + APPLICATION_NAME + "-error", "error." + errorKey);
    headers.add("X-" + APPLICATION_NAME + "-params", entityName);
    headers.add(MESSAGE_NAME, defaultMessage);
    return headers;
  }

  /**
   * 生成分页相关响应头
   *
   * @param page 分页数据信息
   * @param baseUrl rest api地址
   */
  public final static HttpHeaders generatePaginationHttpHeaders(Page page, String baseUrl)
      throws URISyntaxException {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Total-Count", "" + Long.toString(page.getTotalElements()));
    return headers;
  }

  /**
   * 响应
   *
   * @param maybeResponse 响应数据
   * @param <X> 实体
   */
  public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
    return wrapOrNotFound(maybeResponse, null);
  }

  /**
   * 响应
   *
   * @param maybeResponse 响应数据
   * @param header 响应头
   * @param <X> 实体
   */
  public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse,
      HttpHeaders header) {
    return maybeResponse.map(response -> ResponseEntity.ok().headers(header).body(response))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
