package com.github.wpyuan.generate;

import com.github.wpyuan.generate.cache.Cache;
import com.github.wpyuan.generate.config.DBContextHolder;
import com.github.wpyuan.generate.mapper.oracle.EntityInfoOraMapper;
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
import java.sql.SQLException;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
@Slf4j
public class OracleTests {

    private Configuration cfg;

    private String outPath;

    @Autowired
    private EntityInfoOraMapper entityInfoOraMapper;

    @BeforeEach
    public void before() throws IOException {
        cfg = new Configuration(Configuration.VERSION_2_3_24);
        ClassPathResource classPathResource = new ClassPathResource("template");
        cfg.setDirectoryForTemplateLoading(classPathResource.getFile());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setClassicCompatible(true);
        DBContextHolder.oracle();
        outPath = "E:\\repo\\fast-code-generate\\src\\test\\out";
    }

    @Test
    public void entity() throws IOException, TemplateException, SQLException {
        // 1、查询当前数据库所有表
        String schemaName = entityInfoOraMapper.selectCurrentSchemaName();
        log.debug("{}", schemaName);
        Cache.TABLE_INFO = entityInfoOraMapper.selectTableInfo(schemaName);

        log.debug("{}", Cache.TABLE_INFO);

    }
}
