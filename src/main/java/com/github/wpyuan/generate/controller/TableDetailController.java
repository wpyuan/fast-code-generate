package com.github.wpyuan.generate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wpyuan.generate.dto.GenerateInfo;
import com.github.wpyuan.generate.service.TableDetailService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * @author PeiYuan
 */
@Controller
public class TableDetailController {

    @Autowired
    private TableDetailService tableDetailService;

    @RequestMapping("/table-detail/{tableName}")
    public String tableDetail(@PathVariable("tableName") String tableName, Model model) throws IOException {
        String tableDesc = tableDetailService.getTableDesc(tableName);
        JSONObject cache = JSON.parseObject(FileUtils.readFileToString(ResourceUtils.getFile("classpath:cache/table_detail_cache.json")));
        model.addAttribute("outPath", cache.get("outPath"));
        model.addAttribute("packageName", cache.get("packageName"));
        model.addAttribute("author", cache.get("author"));
        model.addAttribute("entityPackage", cache.get("entityPackage"));
        model.addAttribute("mapperPackage", cache.get("mapperPackage"));
        model.addAttribute("mapperXmlPath", cache.get("mapperXmlPath"));
        model.addAttribute("isGenerateOther", cache.get("isGenerateOther"));
        model.addAttribute("servicePackage", cache.get("servicePackage"));
        model.addAttribute("controllerPackage", cache.get("controllerPackage"));
        model.addAttribute("tableDesc", tableDesc);
        model.addAttribute("tableName", tableName);
        return "table_detail";
    }

    @RequestMapping("/table-detail/cache")
    public ResponseEntity<String> cache(GenerateInfo generateInfo) throws IOException {
        tableDetailService.writeCache(generateInfo);
        return ResponseEntity.ok("缓存成功");
    }
}
