package com.github.wpyuan.generate.util;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author PeiYuan
 */
@UtilityClass
@Slf4j
public class GenerateUtil {

    /**
     * 根据模板写文件
     *
     * @param outPath 输出文件路径
     * @param temp    模板
     * @param data    数据
     */
    public static void writeFile(String outPath, Template temp, Map<String, Object> data) {

        StringWriter stringWriter = new StringWriter();
        try {
            temp.process(data, stringWriter);
        } catch (TemplateException | IOException e) {
            log.error("填充变量异常", e);
        }

        File file = new File(outPath);
        try {
            FileUtils.writeStringToFile(file, stringWriter.toString());
        } catch (IOException e) {
            log.error("写文件异常", e);
        }
    }
}
