package com.github.wpyuan.generate.service;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PeiYuan
 */
@Service
public class DataSourceListService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public List<Map<String, String>> getAllDataSource() {
        DataSource dataSource = sqlSessionFactory.getConfiguration().getEnvironment().getDataSource();
        Map<Object, DataSource> dataSourceMap = new HashMap<>(2);
        List<Map<String, String>> allDataSource = new ArrayList<>();
        if (dataSource instanceof AbstractRoutingDataSource) {
            dataSourceMap = ((AbstractRoutingDataSource) dataSource).getResolvedDataSources();
            dataSourceMap.forEach((key, ds) -> {
                Map<String, String> dataSourceInfo = new HashMap<>(1);
                dataSourceInfo.put("name", (String) key);
                allDataSource.add(dataSourceInfo);
            });

        }

        return allDataSource;
    }
}
