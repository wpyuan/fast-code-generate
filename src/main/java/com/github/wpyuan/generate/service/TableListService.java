package com.github.wpyuan.generate.service;

import com.github.wpyuan.generate.cache.Cache;
import com.github.wpyuan.generate.dto.TableInfo;
import com.github.wpyuan.generate.mapper.DefaultEntityInfoMapper;
import com.github.wpyuan.generate.helper.MapperHepler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author PeiYuan
 */
@Service
public class TableListService {
    @Autowired
    private MapperHepler mapperHepler;
    private DefaultEntityInfoMapper entityInfoMapper;

    public String getCurrentSchemaName() {
        entityInfoMapper = mapperHepler.getEntityInfoMapper();
        String schemaName = entityInfoMapper.selectCurrentSchemaName();
        return schemaName;
    }

    public List<Map<String, String>> getAllTable(String schemaName) {
        entityInfoMapper = mapperHepler.getEntityInfoMapper();
        Cache.TABLE_INFO = entityInfoMapper.selectTableInfo(schemaName);
        Set<Map<String, String>> allTable = new HashSet<>();
        Map<String, String> table = null;
        for (TableInfo tableInfo : Cache.TABLE_INFO) {
            table = new HashMap<>(2);
            String name = tableInfo.getTableName();
            String desc = tableInfo.getTableDesc();
            table.put("name", name);
            table.put("desc", desc);
            allTable.add(table);
        }

        return new ArrayList<>(allTable);
    }
}
