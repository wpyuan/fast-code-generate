package com.github.wpyuan.generate.controller;

import com.github.wpyuan.generate.config.DBContextHolder;
import com.github.wpyuan.generate.service.TableListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author PeiYuan
 */
@Controller
public class TableListController {

    @Autowired
    private TableListService tableListService;

    @RequestMapping("/table-list/{dataSourceName}")
    public String tableList(@PathVariable String dataSourceName, Model model) {
        DBContextHolder.set(dataSourceName);
        String schemaName = tableListService.getCurrentSchemaName();
        List<Map<String, String>> allTable = tableListService.getAllTable(schemaName);
        model.addAttribute("schemaName", schemaName);
        model.addAttribute("allTable", allTable);
        return "table_list";
    }

    @RequestMapping("/table-list/{dataSourceName}/one")
    public String tableList(@PathVariable String dataSourceName, String tableName, Model model) {
        DBContextHolder.set(dataSourceName);
        String schemaName = tableListService.getCurrentSchemaName();
        List<Map<String, String>> allTable = tableListService.getTable(schemaName, tableName);
        model.addAttribute("schemaName", schemaName);
        model.addAttribute("allTable", allTable);
        return "table_list";
    }

    @RequestMapping("/refresh")
    public ResponseEntity<String> refresh() {
        String schemaName = tableListService.getCurrentSchemaName();
        List<Map<String, String>> allTable = tableListService.getAllTable(schemaName);
        return ResponseEntity.ok().build();
    }
}
