package com.github.wpyuan.generate.controller;

import com.github.wpyuan.generate.service.GenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author PeiYuan
 */
@Controller
public class GenerateController {
    @Autowired
    private GenerateService generateService;

    @RequestMapping("/generate/{tableName}")
    public ResponseEntity<String> generate(@PathVariable("tableName") String tableName, String outPath, String packageName, String author, String entityPackage, String mapperPackage, String mapperXmlPath) {
        generateService.generate(tableName, outPath, packageName, author, entityPackage, mapperPackage, mapperXmlPath);
        return ResponseEntity.ok("生成成功");
    }
}
