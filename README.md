# QRCodeMall
本质是商城，商品是面向商家的二维码服务，提供运输，编辑二维码的服务
用户购买完二维码可以在个人页面配置二维码样式，商家可以在后台管理各个种类二维码可以展示的字段
### springboot+mybatis+mysql
这里只有后端代码，
前端 __用户侧__ 代码点击[这里](https://github.com/PPeony/QrcodeMall-webfront)

前端 __管理员侧__ 代码点击[这里](https://github.com/PPeony/qrcode-admin-webfront)
# 值得一说的技术
1. 动态添加的定时任务，分布式锁，redis应对秒杀，rabbitmq保证任务异步执行

管理员可以对某个商品执行促销操作，在设定时间和金额之后就会提交两个定时任务，分别对应促销开始和促销结束，这两个定时任务的信息会存入数据库。

第一个定时任务：设置商品缓存，这样商品数量减少就不用不断查询数据库。

第二个定时任务：删除对应的商品信息。

由于最后使用集群部署，集群部署时定时任务可能会在每个节点都执行一遍，为了防止重复执行，采用redisLock的方式让定时任务争取锁。
采用setnx语句执行，key是线程唯一id，防止误解锁。

在用户购买时，首先检测redis内数量是否足够，足够就减一并提交订单，由于redis单线程，可以原子化减一。提交订单采用消息队列的形式，防止高并发写入数据库，利用消息队列让后端自己去队列里面取数据。
用户提交订单之后会在redis生成对应的id号码，前端轮询这个号码来查询订单状态。

2. 代码优化
在pageUtil里编写了generatePageInfoByTime方法，采用反射应对所有名字是selectByEntityAndTime
   的查询，这些查询都是查起始日期到终止日期并分页的查询，这里使用反射注入对应的参数，可以省略很多xml。

3. 基于k8s的部署
在resources下面写了很多deployment和svc，最外部有dockerfile。这是本人第一次尝试用k8s部署，在最开始部署k8s时就遇到很多问题，解决方式可以看[这里](https://ppeony.github.io/2022/04/23/%E4%BA%91%E4%B8%BB%E6%9C%BA%E5%AE%89%E8%A3%85kubernetes%E9%81%87%E5%88%B0%E7%9A%84%E8%8A%82%E7%82%B9%E4%B9%8B%E9%97%B4%E4%B8%8D%E4%BA%92%E9%80%9A%E9%97%AE%E9%A2%98/)

# 一些基本功能，包括
- 后台查看交易记录，curd商品，curd用户、通知等等curd操作，都做了分页处理
- samesite跨域请求
- logback日志记录
- 上传图片
- 引入alipay
- 全局异常处理
- restful返回形式
- swagger
- post请求关闭springboot
- 手机号发送验证码
- 可以查看邀请的朋友
- aop编程

