package com.github.wpyuan.generate.domain.entity;

import com.github.mybatis.crud.annotation.Id;
import com.github.mybatis.crud.annotation.Table;
import lombok.Data;


/**
 *  用户表
 *
 * @author wangpeiyuan 490176245@qq.com
 * @date 2021-01-29 09:39:57
 */
@Table(name = "user_info")
@Data
public class UserInfo {

    /**
      * 
      */
    @Id
    private Long userId;
    /**
      * 用户名
      */
    private String userName;
    /**
      * 密码
      */
    private String userPass;
    /**
      * 是否启用
      */
    private Boolean isEnable;
    /**
      * 员工id
      */
    private Long employeeId;
}