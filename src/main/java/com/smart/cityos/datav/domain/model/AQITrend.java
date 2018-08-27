package com.smart.cityos.datav.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>title:12小时AQI趋势模型</p>
 * <p>description:12小时AQI趋势模型</p>
 *
 * @author: mingcheong
 * @date Created in 2018-08-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AQITrend {


    /**
     * x坐标值
     */
    String x;

    /**
     * x坐标值
     */
    Integer y;
}
