package com.github.wpyuan.generate.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wpyuan.generate.cache.Cache;
import com.github.wpyuan.generate.dto.GenerateInfo;
import com.github.wpyuan.generate.dto.TableInfo;
import com.github.wpyuan.generate.mapper.mysql.EntityInfoMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author PeiYuan
 */
@Service
public class TableDetailService {

    public String getTableDesc(String tableName) {
        for (TableInfo tableInfo : Cache.TABLE_INFO) {
            if (tableName.equals(tableInfo.getTableName())) {
                return tableInfo.getTableDesc();
            }
        }
        return null;
    }

    public void writeCache(GenerateInfo generateInfo) throws IOException {
        File cache = ResourceUtils.getFile("classpath:cache/table_detail_cache.json");
        FileUtils.writeStringToFile(cache, JSON.toJSONString(generateInfo));
    }
}
