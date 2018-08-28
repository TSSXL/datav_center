package com.smart.cityos.datav.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>title:前端Vue接收的结果集</p>
 * <p>description:前端Vue接收的结果集</p>
 *
 * @author: mingcheong
 * @date Created in 2018-08-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    public Result(Object data) {
        this.code = 20000;
        this.data = data;
    }


    /**
     * 状态
     */
    Integer code = 20000;


    /**
     * 数据集
     */
    Object data = null;
}
