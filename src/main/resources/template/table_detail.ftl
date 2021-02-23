<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, initial-scale=1.0, user-scalable=no">
    <title>表详细信息</title>
    <!-- 引入 FrozenUI -->
    <link rel="stylesheet" href="/frozenui.css"/>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body ontouchstart>
<section class="ui-container">

    <div id="error-tooltip" hidden class="ui-tooltips ui-tooltips-warn">
        <div class="ui-tooltips-cnt ui-border-b">
            <i></i><span id="tooltip-message"></span><a class="ui-icon-close" href="javascript:$('#error-tooltip').hide()"></a>
        </div>
    </div>
    <div id="cache-tooltip" hidden class="ui-tooltips ui-tooltips-guide ui-tooltips-function">
        <div class="ui-tooltips-cnt ui-border-b">
            <i></i> 是否将下面信息写入缓存
            <button class="ui-btn-s" onclick="writeCache()">缓存</button>
        </div>
    </div>
    <div class="index-wrap">
        <div class="header">
            <h1>${tableName!}</h1>
            <h2>${tableDesc!}</h2>
        </div>

        <section id="form">
            <div class="demo-item">
                <p class="demo-desc">填写文件生成所需信息</p>
                <div class="demo-block">
                    <div class="ui-form ui-border-t">
                        <div class="ui-form-item ui-border-b">
                            <label>
                                输出目录
                            </label>
                            <input id="outPath" name="outPath" type="text" value="${outPath!''}" placeholder="如E:/code/src/main/java" required />
                            <a href="#" class="ui-icon-close" onclick="$('#outPath').val('')"></a>
                        </div>
                        <div class="ui-form-item ui-border-b">
                            <label>
                                类所在顶层路径
                            </label>
                            <input id="packageName" name="packageName" type="text" value="${packageName!''}" placeholder="如com.github.generate" required />
                            <a href="#" class="ui-icon-close" onclick="$('#packageName').val('')"></a>
                        </div>
                        <div class="ui-form-item ui-border-b">
                            <label>
                                entity所在次级路径
                            </label>
                            <input id="entityPackage" name="entityPackage" type="text" value="${entityPackage!''}" placeholder="如domain.entity" required />
                            <a href="#" class="ui-icon-close" onclick="$('#entityPackage').val('')"></a>
                        </div>
                        <div class="ui-form-item ui-border-b">
                            <label>
                                mapper所在次级路径
                            </label>
                            <input id="mapperPackage" name="mapperPackage" type="text" value="${mapperPackage!''}" placeholder="如infra.mapper" required />
                            <a href="#" class="ui-icon-close" onclick="$('#mapperPackage').val('')"></a>
                        </div>
                        <div class="ui-form-item ui-border-b">
                            <label>
                                mapper.xml所在resource路径
                            </label>
                            <input id="mapperXmlPath" name="mapperXmlPath" type="text" value="${mapperXmlPath!''}" placeholder="如mapper，即resource/mapper" required />
                            <a href="#" class="ui-icon-close" onclick="$('#mapperXmlPath').val('')"></a>
                        </div>
                        <div class="ui-form-item ui-border-b">
                            <label>
                                作者
                            </label>
                            <input id="author" name="author" type="text" value="${author!''}" placeholder="如wpyuan" required />
                            <a href="#" class="ui-icon-close" onclick="$('#author').val('')"></a>
                        </div>
                        <div class="ui-form-item ui-form-item-switch ui-border-b">
                            <p>生成service/controller代码</p>
                            <label class="ui-switch">
                                <input id="isGenerateOther" name="isGenerateOther" type="checkbox" onchange="isGenerateOther()"/>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <div id="other" class="demo-item" hidden>
                <p class="demo-desc">service/controller相关</p>
                <div class="demo-block">
                    <div class="ui-form ui-border-t">
                        <div class="ui-form-item ui-border-b">
                            <label>
                                service所在次级路径，默认impl在该目录下
                            </label>
                            <input id="servicePackage" name="servicePackage" type="text" value="${servicePackage!''}" placeholder="如app.service" required />
                            <a href="#" class="ui-icon-close" onclick="$('#servicePackage').val('')"></a>
                        </div>
                        <div class="ui-form-item ui-border-b">
                            <label>
                                controller所在次级路径
                            </label>
                            <input id="controllerPackage" name="controllerPackage" type="text" value="${controllerPackage!''}" placeholder="如api.controller" required />
                            <a href="#" class="ui-icon-close" onclick="$('#controllerPackage').val('')"></a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="demo-item">
                <div class="demo-block">
                    <div class="ui-form ui-border-t">
                        <div class="ui-btn-wrap">
                            <button class="ui-btn-lg ui-btn-primary" onclick="submit()">
                                生成文件
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="demo-item">
                <div class="demo-block">
                    <div id="loading" class="ui-loading-block hidden">
                        <div class="ui-loading-cnt">
                            <i class="ui-loading-bright"></i>
                            <p id="loading-message"></p>
                        </div>
                    </div>
                </div>
            </div>
            <div id="dialog" class="ui-dialog hidden">
                <div class="ui-dialog-cnt">
                    <div class="ui-dialog-bd">
                        <p id="message"></p>
                    </div>
                    <div class="ui-dialog-ft">
                        <button type="button" data-role="button" onclick="$('#dialog').removeClass('show'); $('#dialog').addClass('hidden');">返回</button>
                    </div>
                </div>
            </div>
        </section>
        <i class="ui-subscript ui-subscript-red" onclick="refresh()">刷新</i>

        <div class="footer">
            <a href="https://github.com/wpyuan/fast-code-generate"><div class="logo"></div></a>
        </div>
    </div>

</section>


<script src="/js/lib/zepto.min.js"></script>
<script src="/js/index.js"></script>
<script>
    <#if isGenerateOther?? && isGenerateOther>
        $("#isGenerateOther").prop("checked", true);
        isGenerateOther();
    </#if>

    function refresh() {
        $.ajax({
            url: "/refresh",
            beforeSend: function () {
                this.handleBeforeSend("同步数据库数据中...");
            },
            success: function (res) {
                this.handleSuccess("刷新成功");
            },
            error: function (e) {
                this.handleError("刷新失败");
            },
            complete: function () {
                this.handleAfterSend();
            }
        });
    }

    function writeCache() {
        $.ajax({
            url: "/table-detail/cache",
            data: getFrom(),
            beforeSend: function () {
                this.handleBeforeSend("写入中...");
            },
            success: function (res) {
                this.handleSuccess(res);
                $('#cache-tooltip').hide();
            },
            error: function (e) {
                this.handleError("缓存失败");
            },
            complete: function () {
                this.handleAfterSend();
            }
        });
    }
    function submit() {
        var validate = !$("#outPath").val() || !$("#packageName").val() || !$("#author").val() || !$("#entityPackage").val() || !$("#mapperPackage").val() || !$("#mapperXmlPath").val();

        if (validate) {
            $("#tooltip-message").html("请输入所有值");
            $("#error-tooltip").show();
            return;
        }
        var data = getFrom();
        $.ajax({
            url: "/generate/${tableName}",
            data: data,
            beforeSend: function () {
                this.handleBeforeSend("生成文件...");
            },
            success: function (res) {
                this.handleSuccess(res);
                $('#cache-tooltip').show();
            },
            error: function (e) {
                this.handleError("生成失败");
            },
            complete: function () {
                this.handleAfterSend();
            }
        });
    }

    function getFrom() {
        var outPath = $("#outPath").val().replaceAll("\\","/");
        return "outPath=" + outPath + "&packageName=" + $("#packageName").val() + "&author=" + $("#author").val() +
            "&entityPackage=" + $("#entityPackage").val() + "&mapperPackage=" + $("#mapperPackage").val() + "&mapperXmlPath=" + $("#mapperXmlPath").val() +
            "&isGenerateOther=" + $("#isGenerateOther").is(":checked") + "&servicePackage=" + $("#servicePackage").val() + "&controllerPackage=" + $("#controllerPackage").val();
    }

    function handleSuccess(res) {
        $("#dialog").removeClass("hidden");
        $("#dialog").addClass("show");
        $("#message").html(res);
        $('#error-tooltip').hide();
    }

    function handleError(res) {
        $("#dialog").removeClass("hidden");
        $("#dialog").addClass("show");
        $("#message").html(res);
        $("#tooltip-message").html(res);
        $("#error-tooltip").show();
    }

    function handleBeforeSend(message) {
        $("#loading-message").html(message);
        $("#loading").removeClass("hidden");
        $("#loading").addClass("show");
    }

    function handleAfterSend() {
        $("#loading").removeClass("show");
        $("#loading").addClass("hidden");
    }

    function isGenerateOther() {
        var otherShow = $("#isGenerateOther").is(":checked");
        if (otherShow) {
            $("#other").show();
        } else {
            $("#other").hide();
        }
    }

</script>
</body>
</html>