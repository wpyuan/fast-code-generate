package com.github.wpyuan.generate.service;

import com.github.wpyuan.generate.cache.Cache;
import com.github.wpyuan.generate.mapper.mysql.EntityInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author PeiYuan
 */
@Service
public class IndexService {

    @Autowired
    private EntityInfoMapper entityInfoMapper;

    public String getCurrentSchemaName() {
        String schemaName = entityInfoMapper.selectCurrentSchemaName();
        return schemaName;
    }

    public List<Map<String, String>> getAllTable(String schemaName) {
        Cache.TABLE_INFO = entityInfoMapper.selectTableInfo(schemaName);
        Set<Map<String, String>> allTable = new HashSet<>();
        Map<String, String> table = null;
        for (Map<String, Object> map : Cache.TABLE_INFO) {
            table = new HashMap<>(2);
            String name = (String) map.get("tableName");
            String desc = (String) map.get("tableDesc");
            table.put("name", name);
            table.put("desc", desc);
            allTable.add(table);
        }

        return new ArrayList<>(allTable);
    }
}
