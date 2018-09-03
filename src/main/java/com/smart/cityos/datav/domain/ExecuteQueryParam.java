package com.smart.cityos.datav.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by admin on 2018-09-03.
 */
@Data
@NoArgsConstructor
public class ExecuteQueryParam {
    private DbInfo dbInfo;
    private String sql;

    public ExecuteQueryParam(DbInfo dbInfo, String sql) {
        this.dbInfo = dbInfo;
        this.sql = sql;
    }

    public DbInfo getDbInfo() {
        return dbInfo;
    }

    public void setDbInfo(DbInfo dbInfo) {
        this.dbInfo = dbInfo;
    }



    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
