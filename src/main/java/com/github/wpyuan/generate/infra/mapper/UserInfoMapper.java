package com.github.wpyuan.generate.infra.mapper;

import com.github.wpyuan.generate.domain.entity.UserInfo;
import com.github.mybatis.crud.mapper.BatchInsertMapper;
import com.github.mybatis.crud.mapper.DefaultMapper;

/**
 *  用户表 mapper
 *
 * @author wangpeiyuan 490176245@qq.com
 * @date 2021-01-29 16:42:44
 */
public interface UserInfoMapper extends DefaultMapper<UserInfo>, BatchInsertMapper<UserInfo> {
}