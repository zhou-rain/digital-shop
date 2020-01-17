# digital-shop
数码商城

开发团队：潘祥森，吴映凡，周冉

开发语言：java  
开发工具：Intellij IDEA  
关键技术：springboot2.2.2 + Mybatis-plus   
数据库：mySql8.0 + Druid  
微服务：Dubbo + zookeeper + Docker  
操作系统：开发环境-win10  上线环境-Linux  
版本控制：github  maven      
    
2020年1月10日  
项目骨架搭建完成，父依赖parent，通用工具类commons，通用接口层api，另加test-demo测试用例  
  
  
2020年1月11日  
1、测试前端页面跳转，测试成功  
2、新建shop-service，抽取serviceImpl层，将zookeeper扫描包和mapperscan放在此层，这个模块负责整个项目的接口，并暴露服务。  
3、抽取api层的与数据库交互的依赖，service层要将这些依赖排除，以后只需要调用接口方法即可，所以以后impl层和新建的springboot子模块无需再写数据源。  
4、test-mp测试用例测试成功  
5、还未将包抽取出来，下个版本更新  
——zhouRan  
  
  
2020年1月12日  
1、新增5张基于shiro的后台权限数据库表  
2、构建后台流程业务，并画好流程图  
3、编写后台需求文档  
4、编写整个项目的规则要求和注意事项  
5、抽取后台业务层impl暴露端，新建shop-service-manager，并测试成功  
6、明天试用ssm框架搭建后台manager前端模块  
ps：由于规则要求还未完善，等与组员讨论好，完善后再更新  
——zhouRan  
  
  
1月13日  
优化框架，完善框架的各个模块，还未完成  
——zhouRan  
  
  
1月14日  
1、完善框架，修改框架的bug。  
2、调试前端的后台管理模块页面。  
3、搭建ssm框架，集成shiro，ssm框架内置管理员后台权限的前端页面。  
——zhouRan  
  
  
1月15日  
商城商品的录入功能：  
1、三级分类的查询  
2、商品的平台属性列表的增删改查  
3、商品spu的添加  
4、spu列表的查询（根据三级分类的id，查询spu列表）  
5、spu的销售属性、属性值的增删改查  
6、fast分布式图片服务器上传（图片上传后期有空再做）  
(保存商品信息、商品信息下关联图片和属性，属性关联属性值)  
——zhouRan  

1月16日  
1、商品sku功能的实现，完成四张表的关联关系  
2、新建商品详情页的前端web模块  
3、引入thymeleaf模板引擎渲染页面  
4、梳理sup和sku在页面上的对应关系和表之间的关联以及查询的一些思想  
5、远程测试zookeeper和dubbo的功能，测试成功  
——zhouRan   
  
  





