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
    public static final String APP_PRIVATE_KEY = "";//私钥
    //公钥
    public static final String AliPayPublicKey = "+QFfaDOJY1Vm5xP4s9vQ2t79c4ruNEuHlcWaRKCjhx4SO4Ls278Y3FyMBSRwPsf4fvA5p54Cp9YfSTOI8W0KzTfSDI5YIe8A5qPXjhhSbTI+E/SxnuTrQctDNSrDvK31jw7eDuzVtDEsRtwHcZAQxBlkOUEtD9EcVT+rzqKmVQMiY95Wo0sc3aqrO/XpE99r8WRn+LFzqcfzskbLa5KHtHZmwWlVauggFfQtL5GaLbERDvyp4Y0dTILLE79Z/BKUs7DwGQnr2f7rqZQMIJSwrxOn0gbZjgFkSYX9X9skt1L/lThwCN9wBofFx71qOxgzjMFQWi1jkr4Nvp7wIDAQAB";

    public static final String notify_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    public static final String return_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    public static String secretID = "";
    public static String secretKey = "";
    public static int appId = 0;
    public static String appKey = "";
    public static int templateId = 0;
    public static String smsSign = "";


}
