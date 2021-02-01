package com.github.wpyuan.generate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PeiYuan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表描述
     */
    private String tableDesc;
    /**
     * 列名
     */
    private String columnName;
    /**
     * 列类型
     */
    private String columnType;
    /**
     * 列对应的javaType
     */
    private String javaType;
    /**
     * 列长度
     */
    private Long length;
    /**
     * 列是否允许为空
     */
    private Boolean isNull;
    /**
     * 列默认值
     */
    private String defaultValue;
    /**
     * 列描述
     */
    private String columnDesc;
    /**
     * 列是否为主键
     */
    private Boolean isPk;
}
