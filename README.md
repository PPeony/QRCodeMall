# QRCodeMall
an innovative project in university using springboot
### springboot+mybatis+mysql
这里只有后端代码，
前端 __用户侧__ 代码点击[这里](https://github.com/PPeony/QrcodeMall-webfront)

前端 __管理员侧__ 代码点击[这里](https://github.com/PPeony/qrcode-admin-webfront)
# 新加技术
动态添加的定时任务，分布式锁，redis应对秒杀，rabbitmq保证任务异步执行
# 一些基本功能完成了，包括
- 后台查看交易记录，curd商品，curd用户、通知等等curd操作，都做了分页处理
- 折磨人的跨域请求
- logback日志记录
- 上传图片
- 引入alipay支付（微信正在交涉中）
- 全局异常处理
- restful返回形式
- swagger
- post请求关闭springboot
- 手机号发送验证码
- 现在已经可以注册使用了，可以用支付宝沙箱购买
- 可以查看邀请的朋友
- 尝试用多线程查询，但是发现效率可能不如修改底层sql语句高

