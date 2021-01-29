package ${mapperPackage};

import ${entityPackage}.${entityClassName};
import com.github.mybatis.crud.mapper.BatchInsertMapper;
import com.github.mybatis.crud.mapper.DefaultMapper;

/**
 *  ${tableDesc!} mapper
 *
 * @author ${author}
 * @date ${date}
 */
public interface ${entityClassName}Mapper extends DefaultMapper<${entityClassName}>, BatchInsertMapper<${entityClassName}> {
}