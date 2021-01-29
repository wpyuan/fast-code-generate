package com.github.wpyuan.generate.mapper.mysql;

import java.util.List;
import java.util.Map;

/**
 * @author PeiYuan
 */
public interface EntityInfoMapper {

    /**
     * 查询表信息
     * @param schemaName 数据库名
     * @return 表信息
     */
    List<Map<String, Object>> selectTableInfo(String schemaName);

    /**
     * 查询当前数据库名
     * @return 当前数据库名
     */
    String selectCurrentSchemaName();

}
