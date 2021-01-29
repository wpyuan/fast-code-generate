package com.github.wpyuan.generate.controller;

import com.github.wpyuan.generate.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author PeiYuan
 */
@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @RequestMapping("/")
    public String index(Model model) {
        String schemaName = indexService.getCurrentSchemaName();
        List<Map<String, String>> allTable = indexService.getAllTable(schemaName);
        model.addAttribute("schemaName", schemaName);
        model.addAttribute("allTable", allTable);
        return "index";
    }

    @RequestMapping("/refresh")
    public ResponseEntity<String> refresh() {
        String schemaName = indexService.getCurrentSchemaName();
        List<Map<String, String>> allTable = indexService.getAllTable(schemaName);
        return ResponseEntity.ok().build();
    }
}
