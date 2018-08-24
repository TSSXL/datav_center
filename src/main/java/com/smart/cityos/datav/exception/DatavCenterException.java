package com.smart.cityos.datav.exception;

/**
 * <p>title:业务异常基类</p>
 * <p>description:业务异常基类</p>
 *
 * @author: mingcheong
 * @date Created in 2018-8-22
 */
public class DatavCenterException extends RuntimeException {

    public DatavCenterException() {
        super();
    }

    public DatavCenterException(String message) {
        super(message);
    }

    public DatavCenterException(String message, Throwable cause) {
        super(message, cause);
    }

}