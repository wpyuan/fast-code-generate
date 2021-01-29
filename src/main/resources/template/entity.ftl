package ${entityPackage};

import com.github.mybatis.crud.annotation.Id;
import com.github.mybatis.crud.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<#list fieldTypeImports as item>
        <#if item == "BigDecimal">
import java.math.BigDecimal;
        </#if>
        <#if item == "Date">
import java.util.Date;
        </#if>
</#list>

/**
 *  ${tableDesc!}
 *
 * @author ${author}
 * @date ${date}
 */
@Table(name = "${tableName}")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ${entityClassName} {

<#list fields as item>
    /**
      * ${item.desc!}
      */
    <#if item.isId == true>
    @Id
    </#if>
    private ${item.type} ${item.name};
</#list>
}