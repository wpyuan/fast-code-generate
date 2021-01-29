<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackage}.${entityClassName}Mapper">
   <resultMap id="BaseResultMap" type="${entityPackage}.${entityClassName}">
      <#list resultMap as result>
         <#if result.isId>
      <id column="${result.column}" jdbcType="${result.jdbcType}" property="${result.property}" />
         <#else>
      <result column="${result.column}" jdbcType="${result.jdbcType}" property="${result.property}" />
         </#if>
      </#list>
   </resultMap>
   <sql id="Base_Column_List">
      <#list resultMap as result>
         ${result.column}<#if result_has_next>, </#if>
      </#list>
   </sql>

</mapper>