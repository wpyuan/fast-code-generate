package com.github.wpyuan.generate.mapper.oracle;

/**
 * @author PeiYuan
 */
public interface SequenceMapper {

    /**
     * 根据序列名查询序列是否存在
     * @param sequenceName
     * @return
     */
    Boolean isSequenceExist(String sequenceName);
}
