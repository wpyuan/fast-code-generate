package com.github.wpyuan.generate.mapper;

import com.github.wpyuan.generate.dto.TableInfo;

import java.util.List;

/**
 * EntityInfoMapper
 *
 * @author PeiYuan
 */
public interface DefaultEntityInfoMapper {

    /**
     * 查询表信息
     *
     * @param schemaName 数据库名
     * @return 表信息
     */
    List<TableInfo> selectTableInfo(String schemaName);

    /**
     * 查询当前数据库名
     *
     * @return 当前数据库名
     */
    String selectCurrentSchemaName();

}
