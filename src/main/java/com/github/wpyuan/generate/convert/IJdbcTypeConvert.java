package com.github.wpyuan.generate.convert;

/**
 * @author PeiYuan
 */
public interface IJdbcTypeConvert {
    /**
     * 识别数据库列类型转换为mapper.xml，result标签的jdbcType的值
     * @param columnType 数据库列类型
     * @return mapper.xml，result标签的jdbcType的值
     */
    String convert(String columnType);
}
