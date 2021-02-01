package com.github.wpyuan.generate.helper;

import com.github.wpyuan.generate.config.DBContextHolder;
import com.github.wpyuan.generate.mapper.DefaultEntityInfoMapper;
import com.github.wpyuan.generate.mapper.mysql.EntityInfoMapper;
import com.github.wpyuan.generate.mapper.oracle.EntityInfoOraMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author PeiYuan
 */
@Component
public class MapperHepler {
    @Autowired
    private EntityInfoMapper entityInfoMapper;
    @Autowired
    private EntityInfoOraMapper entityInfoOraMapper;

    public DefaultEntityInfoMapper getEntityInfoMapper() {
        if ("oracle".equals(DBContextHolder.get())) {
            return entityInfoOraMapper;
        }

        return entityInfoMapper;
    }
}
