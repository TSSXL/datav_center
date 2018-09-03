package com.smart.cityos.datav.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by admin on 2018-09-03.
 */
@Data
@NoArgsConstructor
public class DbInfo {


    private Object characterSet;
    private String dbInstanceName;
    private String dbIp;
    private String dbPassword;
    private String dbPort;
    private Object dbType;
    private String dbUser;

    public DbInfo(Object characterSet, String dbInstanceName, String dbIp, String dbPassword, String dbPort, Object dbType, String dbUser) {
        this.characterSet = characterSet;
        this.dbInstanceName = dbInstanceName;
        this.dbIp = dbIp;
        this.dbPassword = dbPassword;
        this.dbPort = dbPort;
        this.dbType = dbType;
        this.dbUser = dbUser;
    }

    public Object getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(Object characterSet) {
        this.characterSet = characterSet;
    }

    public String getDbInstanceName() {
        return dbInstanceName;
    }

    public void setDbInstanceName(String dbInstanceName) {
        this.dbInstanceName = dbInstanceName;
    }

    public String getDbIp() {
        return dbIp;
    }

    public void setDbIp(String dbIp) {
        this.dbIp = dbIp;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public Object getDbType() {
        return dbType;
    }

    public void setDbType(Object dbType) {
        this.dbType = dbType;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }
}
