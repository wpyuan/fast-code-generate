package com.github.wpyuan.generate.service;

import com.github.wpyuan.generate.cache.Cache;
import com.github.wpyuan.generate.mapper.mysql.EntityInfoMapper;
import com.github.wpyuan.generate.util.GenerateUtil;
import com.google.common.base.CaseFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author PeiYuan
 */
@Service
@Slf4j
public class GenerateService {

    @Autowired
    private EntityInfoMapper entityInfoMapper;
    @Autowired
    private Configuration freeMarkerConfiguration;
    @Autowired
    private TableDetailService tableDetailService;

    public void generate(String tableName, String outPath, String packageName, String author, String entityPackage, String mapperPackage, String mapperXmlPath) {
        Set<String> fieldTypeImports = new HashSet<>();
        List<Map<String, Object>> fields = new ArrayList<>();
        Map<String, Object> field = null;
        List<Map<String, Object>> resultMap = new ArrayList<>();
        Map<String, Object> result = null;
        for (Map<String, Object> map : Cache.TABLE_INFO) {
            if (!tableName.equals(map.get("tableName"))) {
                continue;
            }
            fieldTypeImports.add((String) map.get("javaType"));
            field = new HashMap<>(4);
            field.put("desc", map.get("columnDesc"));
            boolean isPk = ((Long) map.get("isPk")).equals(1L) ? true : false;
            field.put("isId", isPk);
            field.put("type", (String) map.get("javaType"));
            field.put("name", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, ((String) map.get("columnName")).toLowerCase()));
            fields.add(field);

            //mapper.xml
            result = new HashMap<>(4);
            result.put("column", map.get("columnName"));
            result.put("jdbcType", jdbcType((String) map.get("columnType")));
            result.put("property", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, ((String) map.get("columnName")).toLowerCase()));
            result.put("isId", isPk);
            resultMap.add(result);
        }


        // 填充数据
        Map<String, Object> data = new HashMap<>(8);
        data.put("entityPackage", StringUtils.isNotBlank(entityPackage) ? packageName + "." + entityPackage : packageName);
        data.put("mapperPackage", StringUtils.isNotBlank(mapperPackage) ? packageName + "." + mapperPackage : packageName);
        data.put("mapperXmlPath", mapperXmlPath);
        data.put("fieldTypeImports", fieldTypeImports);
        data.put("tableDesc", tableDetailService.getTableDesc(tableName));
        data.put("author", author);
        data.put("date", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        data.put("tableName", tableName);
        data.put("entityClassName", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase()));
        data.put("fields", fields);
        data.put("resultMap", resultMap);

        // 生成entity
        this.entity(outPath, data);
        // 生成 mapper
        this.mapper(outPath, data);

        // 生成 service
        // 生成 controller
    }

    public void entity(String outPath, Map<String, Object> data) {
        Template temp = null;
        try {
            temp = freeMarkerConfiguration.getTemplate("entity.ftl");
            GenerateUtil.writeFile(outPath + "/" + ((String) data.get("entityPackage")).replaceAll("\\.", "/") + "/" + data.get("entityClassName") + ".java", temp, data);
        } catch (IOException e) {
            log.warn("entity generate error", e);
        }
    }

    public void mapper(String outPath, Map<String, Object> data) {
        Template temp = null;
        try {
            temp = freeMarkerConfiguration.getTemplate("mapper.ftl");
            GenerateUtil.writeFile(outPath + "/" + ((String) data.get("mapperPackage")).replaceAll("\\.", "/") +  "/" + data.get("entityClassName") + "Mapper.java", temp, data);
            // 生成 mapper.xml
            String resourcePath = outPath.replaceFirst("java", "resources");
            temp = freeMarkerConfiguration.getTemplate("mapper-xml.ftl");
            GenerateUtil.writeFile(resourcePath + "/" + data.get("mapperXmlPath")+  "/" + data.get("entityClassName") + "Mapper.xml", temp, data);
        } catch (IOException e) {
            log.warn("entity generate error", e);
        }
    }

    private String jdbcType(String columnType) {
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
