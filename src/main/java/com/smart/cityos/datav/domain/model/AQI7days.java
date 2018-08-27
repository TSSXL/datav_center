package com.smart.cityos.datav.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>title:7天AQI趋势模型</p>
 * <p>description:7天AQI趋势模型</p>
 *
 * @author: mingcheong
 * @date Created in 2018-08-27
 */
@Data
@NoArgsConstructor
public class AQI7days extends AQITrend {

    public AQI7days(String x, Integer y, String s) {
        super(x, y);
        this.s = s;
    }

    /**
     * 系列值
     */
    String s;

}
