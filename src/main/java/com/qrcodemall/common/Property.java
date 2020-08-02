package com.qrcodemall.common;

/**
 * @Author: Peony
 * @Date: 2020/7/28 15:50
 */
public class Property {
    //图片存储地址
    //示例：/usr/developmentTool/myproject/bookstoreAPI/files/images/
    public static final String fileAddress = "/usr/QRCodeMall/images/";




    //支付宝配置
    //push时记得清除
    public static final String alipayAppid = "";
    //私钥
    public static final String APP_PRIVATE_KEY = "";
    //公钥
    public static final String AliPayPublicKey = "";

    public static final String notify_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    public static final String return_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    public static String secretID = "";
    public static String secretKey = "";
    public static int appId = 0;
    public static String appKey = "";
    public static int templateId = 0;


    public static String smsSign = "";


}
