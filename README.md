# fast-code-generate
快速代码生成器，目前仅支持mysql、oracle数据源

## 使用说明
两种方式使用

第一种，手动拉起源码，改写`application.yml`配置文件数据源部分配置，自行运行项目，访问http://localhost:8080使用

第二种，[下载对应版本的可执行`jar`包](https://github.com/wpyuan/fast-code-generate/releases)，执行如下命令启动`jar`包，访问http://localhost:8080使用

以下为linux环境使用举例
```shell
# mysql 数据源需配置的环境变量，无则不需要配置
export MYSQL_IP=your.mysql.ip;
export MYSQL_PORT=your.mysql.port;
export MYSQL_DB=your.mysql.db;
export MYSQL_USERNAME=your.db.username;
export MYSQL_PASSWORD=your.db.password;

# oracle 数据源需配置的环境变量，无则不需要配置
export ORACLE_IP=your.oracle.ip;
export ORACLE_PORT=your.oracle.port;
export ORACLE_SID=your.oracle.sid;
export ORACLE_USERNAME=your.db.username;
export ORACLE_PASSWORD=your.db.password;

nohup java -jar fast-code-generate-0.0.1.jar --server.port=8080 >app.log &
```
