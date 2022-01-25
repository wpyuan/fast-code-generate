package ${enumPackage};

<#if filedEnumData.isNumber>
import com.github.dc.common.base.annotation.ConverterHandler;
</#if>
import com.github.dc.common.base.structure.BaseEnum;
<#if filedEnumData.isNumber>
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
</#if>
import java.util.HashMap;
import java.util.Map;

/**
* <p>
*     ${filedEnumData.classDesc}
* </p>
*
* @author ${author}
* @date ${date}
*/
<#if filedEnumData.isNumber>
@ConverterHandler
</#if>
public enum ${filedEnumData.className} implements BaseEnum<<#if filedEnumData.isNumber>Object<#else>String</#if>, String><#if filedEnumData.isNumber>, Converter<String, ${filedEnumData.className}></#if> {
    <#if filedEnumData.isNumber>
    <#list filedEnumData.data as field>
    /**
     * ${field.desc}
     */
    ${field.desc}(${field.value}, "${field.desc}"),
    </#list>
    <#else>
    <#list filedEnumData.data as field>
    /**
     * ${field.desc}
     */
    ${field.value}("${field.desc}"),
    </#list>
    </#if>
    ;

    <#if filedEnumData.isNumber>
    public final Object value;
    </#if>
    public final String desc;

    private static final Map<String, ${filedEnumData.className}> DESC_LOOKUP = new HashMap<>();
    private static final Map<<#if filedEnumData.isNumber>Object<#else>String</#if>, String> VALE_DESC_MAP = new HashMap<>();

    static {
        for (${filedEnumData.className} item : ${filedEnumData.className}.values()) {
            <#if filedEnumData.isNumber>
            VALE_DESC_MAP.put(item.value(), item.desc());
            <#else>
            VALE_DESC_MAP.put(item.name(), item.desc());
            </#if>
            DESC_LOOKUP.put(item.desc(), item);
        }
    }

    <#if filedEnumData.isNumber>
    ${filedEnumData.className}(Object value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    <#else>
    ${filedEnumData.className}(String desc) {
        this.desc = desc;
    }
    </#if>

    public static ${filedEnumData.className} desc(String desc) {
        return DESC_LOOKUP.get(desc);
    }

    @Override
    public Map<<#if filedEnumData.isNumber>Object<#else>String</#if>, String> all() {
        return VALE_DESC_MAP;
    }

    @Override
    public <#if filedEnumData.isNumber>Object<#else>String</#if> value() {
        <#if filedEnumData.isNumber>
        return this.value;
        <#else>
        return this.name();
        </#if>
    }

    @Override
    public String desc() {
        return this.desc;
    }

    <#if filedEnumData.isNumber>
    @Override
    public ${filedEnumData.className} convert(String s) {
        if (!StringUtils.isNumeric(s)) {
            return Enum.valueOf(${filedEnumData.className}.class, s);
        }

        for (${filedEnumData.className} item : ${filedEnumData.className}.values()) {
            if (String.valueOf(item.value()).equals(s)) {
                return item;
            }
        }
        return null;
    }
    </#if>
}
