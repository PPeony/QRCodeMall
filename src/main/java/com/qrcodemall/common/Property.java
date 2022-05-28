package com.qrcodemall.common;

/**
 * @Author: Peony
 * @Date: 2022/3/8 15:50
 */
public class Property {
    //todo,修改配置文件
    //积分，一级500，二级200
    public static final int firstPoint = 500;

    public static final int secondPoint = 200;

    //倍数,目前为10个积分1块
    public static final int times = 10;

    //userBill表的备注
    public static final String remark = "积分兑换";

    //图片存储地址
    //示例：/usr/developmentTool/myproject/bookstoreAPI/files/images/
    public static final String fileAddress = "/usr/QRCodeMall/images/";
    public static final Integer orderFormStatusExpireTime = 60;//单位s
    public static final String promotionRedisKeyPrefix = "promotion-";
    //支付宝配置
    public static final Integer sessionExpireTime = 600;//session过期时间10min
    public static final String adminSessionName = "session-admin";
    public static final String userSessionPrefix = "session-user-";



    //支付宝配置
    //push时记得清除
    public static final String alipayAppid = "";
    //私钥
    public static final String APP_PRIVATE_KEY = "";
    //公钥
    public static final String AliPayPublicKey = "";

    public static final String notify_url = "http://localhost:8090/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    public static final String return_url = "http://localhost:8090/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    public static String secretID = "";
    public static String secretKey = "";
    public static int appId = 0;
    public static String appKey = "";
    public static int templateId = 0;


    public static String smsSign = "";

}
