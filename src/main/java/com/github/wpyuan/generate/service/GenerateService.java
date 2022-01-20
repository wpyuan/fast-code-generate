package com.github.wpyuan.generate.service;

import com.github.wpyuan.generate.cache.Cache;
import com.github.wpyuan.generate.config.DBContextHolder;
import com.github.wpyuan.generate.convert.IJdbcTypeConvert;
import com.github.wpyuan.generate.convert.MysqlJdbcTypeConvert;
import com.github.wpyuan.generate.convert.OracleJdbcTypeConvert;
import com.github.wpyuan.generate.dto.GenerateInfo;
import com.github.wpyuan.generate.dto.TableInfo;
import com.github.wpyuan.generate.mapper.oracle.SequenceMapper;
import com.github.wpyuan.generate.util.GenerateUtil;
import com.google.common.base.CaseFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author PeiYuan
 */
@Service
@Slf4j
public class GenerateService {

    @Autowired
    private Configuration freeMarkerConfiguration;
    @Autowired
    private TableDetailService tableDetailService;
    @Autowired
    private SequenceMapper sequenceMapper;

    public void generate(String tableName, GenerateInfo generateInfo) {
        Set<String> fieldTypeImports = new HashSet<>();
        List<Map<String, Object>> fields = new ArrayList<>();
        Map<String, Object> field = null;
        List<Map<String, Object>> resultMap = new ArrayList<>();
        Map<String, Object> result = null;
        String pkName = null;
        for (TableInfo tableInfo : Cache.TABLE_INFO) {
            if (!tableName.equals(tableInfo.getTableName())) {
                continue;
            }
            field = new HashMap<>(4);
            if (tableInfo.getIsPk() && "oracle".equals(DBContextHolder.get())) {
                // 查询序列，默认格式是：表名_主键名_S
                String sequenceName = tableName + "_" + tableInfo.getColumnName() + "_S";
                if (sequenceMapper.isSequenceExist(sequenceName)) {
                    field.put("sequenceName", sequenceName);
                }
                pkName = tableInfo.getColumnName();
            }
            fieldTypeImports.add(tableInfo.getJavaType());
            field.put("desc", tableInfo.getColumnDesc());
            field.put("isId", tableInfo.getIsPk());
            field.put("type", tableInfo.getJavaType());
            field.put("name", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableInfo.getColumnName().toLowerCase()));
            field.put("nameUpperUnderscore", tableInfo.getColumnName().toUpperCase());
            fields.add(field);
            
            //mapper.xml
            result = new HashMap<>(4);
            result.put("column", tableInfo.getColumnName());
            result.put("jdbcType", jdbcType(tableInfo.getColumnType()));
            result.put("property", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableInfo.getColumnName().toLowerCase()));
            result.put("isId", tableInfo.getIsPk());
            resultMap.add(result);
        }


        // 填充数据
        Map<String, Object> data = new HashMap<>(8);
        data.put("entityPackage", StringUtils.isNotBlank(generateInfo.getEntityPackage()) ? generateInfo.getPackageName() + "." + generateInfo.getEntityPackage() : generateInfo.getPackageName());
        data.put("constantPackage", generateInfo.getPackageName() + ".constant");
        data.put("mapperPackage", StringUtils.isNotBlank(generateInfo.getMapperPackage()) ? generateInfo.getPackageName() + "." + generateInfo.getMapperPackage() : generateInfo.getPackageName());
        data.put("mapperXmlPath", generateInfo.getMapperXmlPath());
        data.put("fieldTypeImports", fieldTypeImports);
        data.put("tableDesc", tableDetailService.getTableDesc(tableName));
        data.put("author", generateInfo.getAuthor());
        data.put("date", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        data.put("tableName", tableName);
        data.put("entityClassName", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase()));
        data.put("requestMappingUrl", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, tableName.toLowerCase()));
        data.put("fields", fields);
        data.put("resultMap", resultMap);
        data.put("pkName", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, pkName.toLowerCase()));

        // 生成entity
        this.entity(generateInfo.getOutPath(), data);
        // 生成 mapper
        this.mapper(generateInfo.getOutPath(), data);

        if (generateInfo.getIsGenerateOther()) {
            data.put("servicePackage", StringUtils.isNotBlank(generateInfo.getServicePackage()) ? generateInfo.getPackageName() + "." + generateInfo.getServicePackage() : generateInfo.getPackageName());
            data.put("controllerPackage", StringUtils.isNotBlank(generateInfo.getControllerPackage()) ? generateInfo.getPackageName() + "." + generateInfo.getControllerPackage() : generateInfo.getPackageName());
            // 生成 service
            this.service(generateInfo.getOutPath(), data);
            // 生成 controller
            this.controller(generateInfo.getOutPath(), data);
        }

        if (generateInfo.getIsGenerateHtml()) {
            data.put("htmlResourcesPath", generateInfo.getHtmlResourcesPath());
            // 生成 form_element_html.ftl
            this.html(generateInfo.getOutPath(), data);
        }
    }

    public void entity(String outPath, Map<String, Object> data) {
        Template temp = null;
        try {
            temp = freeMarkerConfiguration.getTemplate("entity.ftl");
            GenerateUtil.writeFile(outPath + "/" + ((String) data.get("entityPackage")).replaceAll("\\.", "/") + "/" + data.get("entityClassName") + ".java", temp, data);
            // 生成 constant
            temp = freeMarkerConfiguration.getTemplate("entity-field.ftl");
            GenerateUtil.writeFile(outPath + "/" + ((String) data.get("constantPackage")).replaceAll("\\.", "/") + "/" + data.get("entityClassName") + "Field.java", temp, data);
        } catch (IOException e) {
            log.warn("entity generate error", e);
        }
    }

    public void mapper(String outPath, Map<String, Object> data) {
        Template temp = null;
        try {
            temp = freeMarkerConfiguration.getTemplate("mapper.ftl");
            GenerateUtil.writeFile(outPath + "/" + ((String) data.get("mapperPackage")).replaceAll("\\.", "/") + "/" + data.get("entityClassName") + "Mapper.java", temp, data);
            // 生成 mapper.xml
            String resourcePath = outPath.replaceFirst("java", "resources");
            temp = freeMarkerConfiguration.getTemplate("mapper-xml.ftl");
            GenerateUtil.writeFile(resourcePath + "/" + ((String) data.get("mapperXmlPath")).replaceAll("\\.", "/") + "/" + data.get("entityClassName") + "Mapper.xml", temp, data);
        } catch (IOException e) {
            log.warn("entity generate error", e);
        }
    }

    public void service(String outPath, Map<String, Object> data) {
        Template temp = null;
        try {
            temp = freeMarkerConfiguration.getTemplate("service.ftl");
            String servicePath = outPath + "/" + ((String) data.get("servicePackage")).replaceAll("\\.", "/");
            GenerateUtil.writeFile(servicePath + "/" + data.get("entityClassName") + "Service.java", temp, data);
            // 生成 impl
            temp = freeMarkerConfiguration.getTemplate("service-impl.ftl");
            GenerateUtil.writeFile(servicePath + "/impl/" + data.get("entityClassName") + "ServiceImpl.java", temp, data);
        } catch (IOException e) {
            log.warn("entity generate error", e);
        }
    }

    public void controller(String outPath, Map<String, Object> data) {
        Template temp = null;
        try {
            temp = freeMarkerConfiguration.getTemplate("controller.ftl");
            GenerateUtil.writeFile(outPath + "/" + ((String) data.get("controllerPackage")).replaceAll("\\.", "/") + "/" + data.get("entityClassName") + "Controller.java", temp, data);
        } catch (IOException e) {
            log.warn("entity generate error", e);
        }
    }

    private String jdbcType(String columnType) {
        IJdbcTypeConvert jdbcTypeConvert = null;
        if ("oracle".equals(DBContextHolder.get())) {
            jdbcTypeConvert = new OracleJdbcTypeConvert();
        } else {
            jdbcTypeConvert = new MysqlJdbcTypeConvert();
        }

        return jdbcTypeConvert.convert(columnType);
    }

    public void html(String outPath, Map<String, Object> data) {
        Template temp = null;
        try {
            temp = freeMarkerConfiguration.getTemplate("form_element_html.ftl");
            String resourcePath = outPath.replaceFirst("java", "resources");
            GenerateUtil.writeFile(resourcePath + "/" + ((String) data.get("htmlResourcesPath")).replaceAll("\\.", "/") + "/"  + data.get("tableName").toString().toLowerCase() + ".html", temp, data);

        } catch (IOException e) {
            log.warn("html generate error", e);
        }
    }
}
