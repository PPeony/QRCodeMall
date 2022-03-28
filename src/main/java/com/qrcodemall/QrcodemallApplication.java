package com.qrcodemall;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@SpringBootApplication
@MapperScan("com.qrcodemall.dao")
//@ComponentScan("com.qrcodemall.controller")
//目前版本0.0.13
//hrpkcu8907@sandbox.com
//111111,支付同111111
/*
* 1.redis设置秒杀数量--部署成功,使用jedis
* 管理员创建秒杀商品之后，后台会同时提交数据库和两个定时任务，在第一个定时任务开始执行时，设置redis缓存，
* 在促销时间结束时，第二个定时任务执行，删除redis缓存并修改数据库状态
* 读取促销商品一律从redis读，并且存在key作为计数，用于购买时的计数
* 2.mq保证支付队列不失败--创建订单步骤加入队列，再写入数据库。前端也对此进行了代码修改，异步执行并轮询请求后端接口。
* 3.定时任务修改促销商品状态
* 定时任务在集群使用时，为了防止重复执行任务，需要使用分布式锁，这里采用redis分布式锁的方式
* lua:setnx+expire,key是每个线程自己的uuid
* redis2.6就提供了语句代替上面的lua脚本
* 当然，本案例即使重复执行也没事
* 4.集群配置
* 5.容器化部署
* 6.支付宝接口完善--偶尔可用
* 7.忘记密码发送短信
* 8.样例体验
* */
public class QrcodemallApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrcodemallApplication.class, args);
    }

}
