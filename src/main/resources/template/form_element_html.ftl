<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, initial-scale=1.0, user-scalable=no">
    <title>${tableDesc!'表单'}</title>
    <link rel="stylesheet" href="/css/element-v2.15.7.css">
    <style>
        [v-cloak]{
            display: none;
        }
    </style>
</head>
<body>
<div id="app" v-cloak>
    <el-container>
        <el-header>${tableDesc!'表单'}</el-header>
        <el-main>
            <el-form ref="form" :model="form" label-width="80px" size="small" :loading="formLoading">
            <#list fields as item>
                <#if item.isId == true>
                <#elseIf item.type == 'String'>
                <el-form-item label="${item.desc!}" prop="${item.name}">
                    <el-input v-model="form.${item.name}"></el-input>
                </el-form-item>
                <#elseIf item.type == 'Date'>
                <el-form-item label="${item.desc!}" prop="${item.name}">
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.${item.name}" style="width: 100%;"></el-date-picker>
                </el-form-item>
                <#elseIf item.type == 'Boolean'>
                <el-form-item label="${item.desc!}" prop="${item.name}">
                    <el-switch v-model="form.${item.name}"></el-switch>
                </el-form-item>
                <#else>
                <el-form-item label="${item.desc!}" prop="${item.name}">
                    <el-input-number v-model="form.${item.name}" :min="0" :precision="4" :controls="false" style="width: 100%;"></el-input-number>
                </el-form-item>
                </#if>
            </#list>
            </el-form>
        </el-main>
        <el-footer>
            <el-button size="small" type="primary" @click="handleSubmit" :loading="submitLoading">{{ submitLoading ? '提交中...' : '提交' }}</el-button>
            <el-button size="small" @click="handleReset">重置</el-button>
        </el-footer>
    </el-container>

</div>
<script src="/js/vue.js"></script>
<script src="/js/element-v2.15.7.js"></script>
<script src="/js/axios.js"></script>
<script src="/js/utils.js"></script>
<script type="text/javascript">

    var vm = new Vue({
        el: '#app',
        data() {
            return {
                form: {
                    <#list fields as item>
                    <#if item.isId == true>
                    ${item.name}: getQs('${item.name}'),
                    <#else>
                    ${item.name}: undefined,
                    </#if>
                    </#list>
                },
                formLoading: false,
                submitLoading: false,
            }
        },
        mounted() {
            this.init();
        },
        methods: {
            init() {
                if (!this.form.${pkName}) {
                    return;
                }
                this.formLoading = true;
                axios.get(`/${requestMappingUrl}/detail?${pkName}=${r'${this.form.'}${pkName}${r'}'}`).then((res)=>{
                    if (!res.data) {
                        return;
                    }
                    this.form = res.data;
                    this.formLoading = false;
                }).catch((error)=> {
                    this.$notify.error({
                        title: '错误',
                        message: error.response.data.message,
                        position: 'bottom-right'
                    })
                    this.formLoading = false;
                })
            },
            handleSubmit() {
                this.submitLoading = true;
                axios.post(`/${requestMappingUrl}/save`, this.form).then((res)=>{
                    this.form = res.data;
                    this.$notify.success({
                        title: '成功',
                        message: '操作成功',
                        position: 'bottom-right'
                    })
                    this.submitLoading = false;
                }).catch((error)=> {
                    this.$notify.error({
                        title: '错误',
                        message: error.response.data.message,
                        position: 'bottom-right'
                    })
                    this.submitLoading = false;
                })
            },
            handleReset() {
                this.$refs.form.resetFields();
            },
        },
    })


</script>
</body>
<style>
    .el-header {
        line-height: 60px;
        border-bottom: #DCDFE6 solid 1px;
    }

    .el-footer {
        text-align: center;
    }

</style>
</html>
