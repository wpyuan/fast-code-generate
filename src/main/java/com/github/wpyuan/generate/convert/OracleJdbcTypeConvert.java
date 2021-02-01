package com.github.wpyuan.generate.convert;

/**
 * @author PeiYuan
 */
public class OracleJdbcTypeConvert implements IJdbcTypeConvert {

    /**
     * 识别数据库列类型转换为mapper.xml，result标签的jdbcType的值
     * @param columnType 数据库列类型
     * @return mapper.xml，result标签的jdbcType的值
     */
    @Override
    public String convert(String columnType) {
        if (columnType.contains("NUMBER")) {
            return "DECIMAL";
        } else if (columnType.contains("VARCHAR")) {
            return "VARCHAR";
        } else if (columnType.contains("DATE")) {
            return "TIMESTAMP";
        } else if (columnType.contains("CLOB")) {
            return "CLOB";
        } else {
            return columnType;
        }
    }
}
