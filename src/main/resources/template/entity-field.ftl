package ${constantPackage};

/**
 *  ${tableDesc!}字段常量，供查询方法使用
 *
 * @author ${author}
 * @date ${date}
 */
public interface ${entityClassName}Field {

<#list fields as field>
    /**
      * ${field.desc!}
      */
    String ${field.nameUpperUnderscore} = "${field.name}";
</#list>

}