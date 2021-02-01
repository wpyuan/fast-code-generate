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
            <h2>共${allDataSource?size}个数据源，选择要操作的数据源</h2>
        </div>
        <section class="ui-input-wrap ui-border-t">
            <div class="ui-input ui-border-radius">
                <input id="dataSourceSearch" list="dataSourceList" placeholder="数据源key检索，输入配置的数据源key">
                <datalist id="dataSourceList">
                    <#list allDataSource as dataSource>
                    <option value="${dataSource.name}">
                        </#list>
                </datalist>
            </div>
            <button class="ui-btn" onclick="tableList()">选择</button>
        </section>
        <section id="list">
            <table class="demo-item ui-table ui-border">
                <tbody class="demo-block">
                <#list allDataSource as dataSource>
                    <#if dataSource_index % 3 == 0>
                        <tr>
                    </#if>
                    <td data-href="/table-list/${dataSource.name}">
                        <div>
                            <div class="icon-actionsheet icon"></div>
                            <div class="tit">${dataSource.name}</div>
                        </div>
                    </td>
                    <#if dataSource_index % 3 == 2 || !dataSource_has_next>
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

    function tableList() {
        var dataSourceName = $("#dataSourceSearch").val();
        window.location.href = "/table-list/" + dataSourceName;
    }
</script>
</body>
</html>