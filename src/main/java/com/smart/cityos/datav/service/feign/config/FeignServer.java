package com.smart.cityos.datav.service.feign.config;


import feign.Headers;
import feign.RequestLine;
import com.smart.cityos.datav.domain.Result;
import com.smart.cityos.datav.domain.ExecuteQueryParam;

import java.util.List;
import java.util.Map;

/**
 * <p>title:配置中心</p>
 * <p>description:配置中心</p>
 *
 * @author:
 * @date Created in 2017-4-5
 */
public interface FeignServer {

  /**
   * 通过feign 通用sql查询mysql
   * @param executeQueryParam 配置选项
   * @return 返回接口处理后信息
   */
  @RequestLine("POST /api/sql/executeQuery")
  @Headers("Content-Type: application/json")
  List<Map> executeQuery(Map executeQueryParam);

  /**
   * 通过feign 通用table查询mysql
   * @param executeQueryParam 配置选项
   * @return 返回接口处理后信息
   */
  @RequestLine("POST /api/sql/tableQuery")
  @Headers("Content-Type: application/json")
  List<Map> tableQuery(Map executeQueryParam);

  /**
   * 通过feign 查询监控任务状态
   * @param executeQueryParam 配置选项
   * @return 返回接口处理后信息
   */
  @RequestLine("POST /api/object/getTaskStatus")
  @Headers("Content-Type: application/json")
  List<Map> getTaskStatus(Map executeQueryParam);

  /**
   * 通过feign 查询监控任务状态
   * @param executeQueryParam 配置选项
   * @return 返回接口处理后信息
   */
  @RequestLine("POST /api/collecttask/getAllTaskCount")
  @Headers("Content-Type: application/json")
  Map getTaskCount(Map executeQueryParam);

  /**
   * 通过feign 查询监控任务状态
   * @param executeQueryParam 配置选项
   * @return 返回接口处理后信息
   */
  @RequestLine("POST /api/screen/count")
  @Headers("Content-Type: application/json")
  Map getScreenCount(Map executeQueryParam);

  /**
   * 通过feign 获取应用列表
   * @param executeQueryParam 配置选项
   * @return 返回接口处理后信息
   */
  @RequestLine("POST /api/app/list")
  @Headers("Content-Type: application/json")
  Map getAppsList(Map executeQueryParam);

  /**
   * 获取监控采集订阅数据量
   * @return 返回接口处理后信息
   */
  @RequestLine("GET api/object/getMonitorLogDataCountNodeIds/allRef")
  @Headers("Content-Type: application/json")
  List<List<String>> getTaskDataNodesInfo();

  /**
   * 获取监控采集订阅数据量(小时)
   * @return 返回接口处理后信息
   */
  @RequestLine("POST api/monitor/countNodeTable/groupbyHour")
  @Headers("Content-Type: application/json")
  List<Map> getNodeDataByHour(Map map);

  /**
   * 获取监控采集订阅数据量(天)
   * @return 返回接口处理后信息
   */
  @RequestLine("POST api/monitor/countNodeTable/groupbyDay")
  @Headers("Content-Type: application/json")
  List<Map> getNodeDataByDay(Map map);

  /**
   * 获取监控采集订阅数据量(全部)
   * @return 返回接口处理后信息
   */
  @RequestLine("POST api/monitor/countNodeTable/getAll")
  @Headers("Content-Type: application/json")
  List<Map> getNodeAllData(Map map);

  /**
   * 获取监控采集订阅数据量
   * @return 返回接口处理后信息
   */
  @RequestLine("POST api/log/getMonitorLogLineCount")
  @Headers("Content-Type: application/json")
  List<Map> getTaskErrorCount(Map map);

  /**
   * 获取监控采集订阅数据量
   * @return 返回接口处理后信息
   */
  @RequestLine("POST api/object/getMonitorInfoBySrcId")
  @Headers("Content-Type: application/json")
  Map getMonitorInfoBySrcId(Map map);

  /**
   * 根据类型获取监控状态
   * @return 返回接口处理后信息
   */
  @RequestLine("POST api/object/getMonitorStatusByType")
  @Headers("Content-Type: application/json")
  List<Map> getMonitorStatusByType(Map map);

  /**
   * 获取应用状态
   * @return 返回接口处理后信息
   */
  @RequestLine("GET api/object/getMonitorByRef")
  @Headers("Content-Type: application/json")
  List<Map> getMonitorByRef();

  /**
   * 通过feign远程调度部署选项到指定地址接口
   * @param deployOption 部署选项
   * @return 返回接口处理后信息
   */
//  @RequestLine("POST /api/configs/dispatch")
//  @Headers("Content-Type: application/json")
//  Result dispatch(DeployOption deployOption);

  /**
   * 通过feign 远程获取审核选项
   * @return 返回审核选项，true:自动；false:人工
   */
//  @RequestLine("GET /api/configs/reviewinfo")
//  boolean getReviewInfo();
}



