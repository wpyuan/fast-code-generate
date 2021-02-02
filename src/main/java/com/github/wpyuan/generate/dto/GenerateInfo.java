package com.github.wpyuan.generate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PeiYuan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateInfo {

    /**
     * 输出目录
     */
    private String outPath;
    /**
     * 顶层包路径
     */
    private String packageName;
    /**
     * 作者
     */
    private String author;
    /**
     * 实体类包路径，顶层包路径后面部分
     */
    private String entityPackage;
    /**
     * mapper包路径，顶层包路径后面部分
     */
    private String mapperPackage;
    /**
     * mapper.xml路径，resources后面部分
     */
    private String mapperXmlPath;
    /**
     * 是否生成Service和Controller代码
     */
    private Boolean isGenerateOther;
    /**
     * service所在次级路径，默认impl在该目录下
     */
    private String servicePackage;
    /**
     * controller所在次级路径
     */
    private String controllerPackage;
}
