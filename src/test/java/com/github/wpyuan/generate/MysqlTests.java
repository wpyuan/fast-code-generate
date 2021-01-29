package com.github.wpyuan.generate;

import com.github.wpyuan.generate.cache.Cache;
import com.github.wpyuan.generate.domain.entity.UserInfo;
import com.github.wpyuan.generate.infra.mapper.UserInfoMapper;
import com.github.wpyuan.generate.mapper.mysql.EntityInfoMapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
@Slf4j
public class MysqlTests {

    private Configuration cfg;

    private String outPath;

    @Autowired
    private EntityInfoMapper entityInfoMapper;

    @BeforeEach
    public void before() throws IOException {
        cfg = new Configuration(Configuration.VERSION_2_3_24);
        ClassPathResource classPathResource = new ClassPathResource("template");
        cfg.setDirectoryForTemplateLoading(classPathResource.getFile());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setClassicCompatible(true);

        outPath = "E:\\repo\\fast-code-generate\\src\\test\\out";
    }

    @Test
    public void entity() throws IOException, TemplateException {
        // 1、查询当前数据库所有表
        String schemaName = entityInfoMapper.selectCurrentSchemaName();
        Cache.TABLE_INFO = entityInfoMapper.selectTableInfo(schemaName);

        // 2、查询所有表信息
        Set<String> fieldTypeImports = new HashSet<>();
        Set<String> tableDescs = new HashSet<>();

        for (Map<String, Object> map : Cache.TABLE_INFO) {
            String javaType = (String) map.get("javaType");
            fieldTypeImports.add(javaType);

            String tableDesc = (String) map.get("tableDesc");
            tableDescs.add(tableDesc);
        }


        log.debug("{}", Cache.TABLE_INFO);

        // 填充数据
        Map<String, Object> data = new HashMap<>();
        String packageName = "com.github.wpyuan.generate.entity";
        data.put("package", packageName);
        data.put("fieldTypeImports", fieldTypeImports);
//        Template temp = cfg.getTemplate("entity.ftl");
//        GenerateUtil.writeFile(outPath + "/entity.java", temp, data);
    }
}
