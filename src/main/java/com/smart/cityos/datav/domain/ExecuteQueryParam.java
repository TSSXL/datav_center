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
public class ExecuteQueryParam {
    private DbInfo dbInfo;
    private String sql;
}
