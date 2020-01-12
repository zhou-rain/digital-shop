# digital-shop
数码商城

2020年1月10日  
项目骨架搭建完成，父依赖parent，通用工具类commons，通用接口层api，另加test-demo测试用例
关键技术：springboot2.2.2 + Druid + Mybatis-plus + mySql8.0
微服务：Dubbo + zookeeper
版本控制：git maven    ——zhouRan

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
