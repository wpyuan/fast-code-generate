package com.github.wpyuan.generate.controller;

import com.github.wpyuan.generate.service.DataSourceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author PeiYuan
 */
@Controller
public class DataSourceListController {

    @Autowired
    private DataSourceListService dataSourceListService;

    @RequestMapping("/")
    public String index(Model model) {
        List<Map<String, String>> allDataSource = dataSourceListService.getAllDataSource();
        model.addAttribute("allDataSource", allDataSource);
        return "data_source_list";
    }
}
