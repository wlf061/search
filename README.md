#search
#参考资料
 [Spring-boot](http://www.ityouknow.com/springboot/2015/12/30/springboot-collect.html )
#环境配置
### 1. mysql
* 1.1 mysql 安装路径：
/usr/local/mysql/bin
* 1.2 启动方式
/bin/sh ./mysqld_safe --defaults-file=../my.cnf
### 2. canal 配置
2.1 canal 安装路径:
/root/nancy/server/canal
### 3. elasticsearch 配置
* 3.1 elasticsearch 安装路径：
/home/elsearch/elasticsearch-6.5.1
* 3.2 elasticsearch 安装IK 分词插件 : https://github.com/medcl/elasticsearch-analysis-ik
* 3.3 elasticsearch 自定义分词 器,支持中文同义词,同义词配置
详细见 resource中的 template.json
其中 synonyms_path 是相对于 /home/elsearch/elasticsearch-6.5.0/config 而言
* 3.4 测试自定义分词效果：
GET /search_data/_analyze
{
"analyzer":"ik_syno",
"text":"漂亮的夹克"
}

* 3.5 Ik 分词器 安装扩展去停用词：https://blog.csdn.net/yiyiholic/article/details/83715867

* 3.6 查询链接：http://localhost:8080/search/queryContent?type=4&content=夹克

* 3.7 框架流程 参考：https://www.e-learn.cn/content/qita/1224514

### 4.支持的搜索类型：
    ORDER_PRICE(0,"价格排序"),

    ORDER_STAR(1,"打分排序"),

    ORDER_CREATETIME(2,"更新时间"),

    ORDER_COMPLEX_MODEL(3,"综合排序使用Ranklib模型"),
    ORDER_COMPLEX(4,"综合排序使用ES 的score function");

   其中 ORDER_COMPLEX_MODEL 使用 ranklib 生成的 model 加载到 es 的rescore 中实现， 具体实现见python demo
   ORDER_COMPLEX 使用 function_score
   权重比：prod_name : prod_desc: prod_merchant = 4:2:1
   增加 feild_vector: prod_star 参与打分，

### 5. demo 的操作说明(可参考 https://github.com/o19s/elasticsearch-learning-to-rank)
* 配置符合业务的search_sample_judgment.txt: 包含两部分： query head + query body
  * query head: qid:1: 夹克  (qid==1,的查询关键字为夹克)
  * query body: 4	qid:1 #	4   夹克 [grade (0-4)	queryid	docId	title)]
* 特征工程：配置 json 文件
* 执行脚本 python loadFeatures.py 加载特征 到elasticsearch
* 执行 collectFeatures.py, 执行 ltr query, 分析 query 的结果，得到每个特征的打分，生成文件
search_sample_judgments_wfeatures.txt. 作为模型的输入
* 训练模型：python train.py; 模型训练后 会将模型的输出作为es 的rescore 部分,进行重新打分
* 查询： python search.py 夹克 test_8


### 4. todolist
- [ ] 全量同步大数据优化 （jennifer）



