package com.smart.cityos.datav.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>title:实体未找到异常</p>
 * <p>description:实体未找到异常</p>
 *
 * @author: yangqc
 * @date Created in 2017-5-22
 */
@AllArgsConstructor
public class NotFoundException extends DatavCenterException {

    /**
     * 实体名称
     */
    @Getter
    @Setter
    private String entityName;
}
