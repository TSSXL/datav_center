package com.smart.cityos.datav.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by admin on 2018-09-03.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbInfo {


    private Object characterSet;
    private String dbInstanceName;
    private String dbIp;
    private String dbPassword;
    private String dbPort;
    private Object dbType;
    private String dbUser;
}
