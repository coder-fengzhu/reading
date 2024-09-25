基于spring boot的一个学习项目，会使用到redis, rabbitmq, elastic search等框架
部署简单，非常适合学习提高

实现一个类似豆瓣读书 [https://book.douban.com/](https://book.douban.com/) 类似功能的网站，后端系统API应该如何设计

### 业务需求
+ 支持用户登陆注册
+ 展示图书列表，需要分页，支持按标签过滤
+ 图书标签，按组别分类，展示所有标签的时候需要展示每个标签包含的图书
+ 展示畅销图书排行榜
+ 用户可以对图书评分，评论
+ 图书详情页面需要展示 图书基础信息，评分信息，书评列表
+ 点赞系统支持对图书，书评的点赞以及查看用户的点赞列表
+ 支持图书的全文搜索



### 数据库设计
![](https://cdn.nlark.com/yuque/0/2024/png/26411187/1717507556167-297d05e4-d57e-405b-85db-5fa58891da03.png)



### API
+ 用户注册
+ 用户登陆
+ 新增图书  /book post
+ 更新图书 /book put
+ 查询图书列表，支持按标签过滤 /books get
+ 查询图书详情
+ 查询畅销图书排行榜  /book/rank get
+ 搜索图书（接入es） /book/search
+ 新增图书评分  /book/rating
+ 新增图书书评 /book/review
+ 查询图书书评列表 支持分页
+ 新增标签 /tag post
+ 查询所有图书标签，按标签组别分类 /tags get
+ 点赞接口，支持点赞图书，书评等
+ 查询用户的点赞列表
+ 查询图书或者书评的点赞总数



### 项目亮点
#### 利用bitmap算法减少关联数据量，利用位运算提高效率
#### 利用canal同步mysql数据到es, 使用es的全文搜索功能
安装es跟kibana: [https://www.cnblogs.com/benjieqiang/p/17501293.html](https://www.cnblogs.com/benjieqiang/p/17501293.html)

安装canal-server, canal-adapter: [https://www.macrozheng.com/project/canal_start.html](https://www.macrozheng.com/project/canal_start.html)

#### 利用redis实现图书的热度排行榜
#### spring boot定时任务同步数据
#### 利用completableFuture提升图书详情接口性能
#### 利用rabbitmq，redis实现支持高并发读写的点赞系统


### 启动依赖
redis：docker安装 

rabbitmq: docker安装

elastic search: docker安装

安装canal-server, canal-adapter: [https://www.macrozheng.com/project/canal_start.html](https://www.macrozheng.com/project/canal_start.html)
