package com.github.wpyuan.generate;

import com.github.wpyuan.generate.cache.Cache;
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

        log.debug("{}", Cache.TABLE_INFO);

    }
}
