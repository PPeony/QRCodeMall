# QRCodeMall
an innovative project in university using springboot
### springboot+mybatis+mysql
这里只有后端代码，前端代码点击[这里](https://github.com/yekaile/QRCodeMall-webFront)
# now the basic structure has been built
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

## 新增
- 使用redis缓存验证码

# 可以点击链接测试
网站地址：[stu.jeehon.com](http://stu.jeehon.com/public)
可以注册账号，也可以使用测试账号测试： __test,123456789__

