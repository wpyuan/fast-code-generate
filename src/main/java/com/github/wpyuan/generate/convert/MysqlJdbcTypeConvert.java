package com.github.wpyuan.generate.convert;

/**
 * @author PeiYuan
 */
public class MysqlJdbcTypeConvert implements IJdbcTypeConvert {

    /**
     * 识别数据库列类型转换为mapper.xml，result标签的jdbcType的值
     * @param columnType 数据库列类型
     * @return mapper.xml，result标签的jdbcType的值
     */
    @Override
    public String convert(String columnType) {
        if (columnType.contains("bigint")) {
            return "BIGINT";
        } else if (columnType.contains("varchar") || columnType.contains("text")) {
            return "VARCHAR";
        } else if (columnType.contains("decimal")) {
            return "DECIMAL";
        } else if (columnType.contains("tinyint(1)")) {
            return "BOOLEAN";
        } else if (columnType.contains("date")) {
            return "TIMESTAMP";
        } else {
            return "VARCHAR";
        }
    }
}
