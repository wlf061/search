#search
#参考资料
 [Spring-boot](http://www.ityouknow.com/springboot/2015/12/30/springboot-collect.html )
#环境配置
### 1. mysql
1.1 mysql 安装路径：
/usr/local/mysql/bin
1.2 启动方式
/bin/sh ./mysqld_safe --defaults-file=../my.cnf
### 2. canal 配置
1.1 canal 安装路径:
/root/nancy/server/canal
### 3. elasticsearch 配置
1.1 elasticsearch 安装路径：
/home/elsearch/elasticsearch-6.5.1
1.2 elasticsearch 安装IK 分词插件 : https://github.com/medcl/elasticsearch-analysis-ik
1.3 elasticsearch 自定义分词 器,支持中文同义词,同义词配置
详细见 resource中的 template.json
其中 synonyms_path 是相对于 /home/elsearch/elasticsearch-6.5.1/config 而言
1.4 测试自定义分词效果：
GET /search_data/_analyze
{
"analyzer":"ik_syno",
"text":"漂亮的夹克"
}

1.5 Ik 分词器 安装扩展去停用词：https://blog.csdn.net/yiyiholic/article/details/83715867

1.6 查询链接：http://localhost:8080/search/queryContent?type=0&content=夹克

1.7 框架流程 参考：https://www.e-learn.cn/content/qita/1224514



