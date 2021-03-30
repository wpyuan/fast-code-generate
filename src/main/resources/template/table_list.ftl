<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, initial-scale=1.0, user-scalable=no">
    <title>快速生成代码</title>
    <!-- 引入 FrozenUI -->
    <link rel="stylesheet" href="/frozenui.css"/>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body ontouchstart>
<section class="ui-container">

    <div class="index-wrap">
        <div class="header">
            <h1>快速代码生成</h1>
            <h2>当前数据源数据库是：${schemaName!}，共${allTable?size}个表，选择要操作的表</h2>
        </div>
        <section class="ui-input-wrap ui-border-t">
            <div class="ui-input ui-border-radius">
                <input id="tableSearch" list="tableList" placeholder="表名检索，输入表名">
                <datalist id="tableList">
                    <#list allTable as table>
                    <option value="${table.name}">
                        </#list>
                </datalist>
            </div>
            <button class="ui-btn" onclick="tableDetail()">选择</button>
        </section>
        <section id="list">
            <table class="demo-item ui-table ui-border">
                <tbody class="demo-block">
                <#list allTable as table>
                    <#if table_index % 3 == 0>
                        <tr>
                    </#if>
                    <td data-href="/table-detail/${table.name}">
                        <div>
                            <div class="icon-article icon"></div>
                            <div class="tit">${table.name}</div>
                            <div class="sub-tit">${table.desc!}</div>
                        </div>
                    </td>
                    <#if table_index % 3 == 2 || !table_has_next>
                        </tr>
                    </#if>
                </#list>
                </tbody>
            </table>
        </section>
        <div class="footer">
            <a href="https://github.com/wpyuan/fast-code-generate"><div class="logo"></div></a>
        </div>
    </div>

</section>


<script src="/js/lib/zepto.min.js"></script>
<script src="/js/index.js"></script>
<script>

    function tableDetail() {
        var tableName = $("#tableSearch").val();
        window.location.href = "/table-detail/" + tableName;
    }
</script>
</body>
</html>