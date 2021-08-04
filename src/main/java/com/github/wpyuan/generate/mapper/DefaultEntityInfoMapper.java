package com.github.wpyuan.generate.mapper;

import com.github.wpyuan.generate.dto.TableInfo;
import org.apache.ibatis.annotations.Param;

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
     * 查询表信息
     *
     * @param schemaName 数据库名
     * @return 表信息
     */
    List<TableInfo> selectTableInfoByTableName(@Param("schemaName") String schemaName, @Param("tableName") String tableName);

    /**
     * 查询当前数据库名
     *
     * @return 当前数据库名
     */
    String selectCurrentSchemaName();

}
