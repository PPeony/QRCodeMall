package com.qrcodemall.common;

/**
 * @Author: Peony
 * @Date: 2020/7/28 15:50
 */
public class Property {
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

    //支付宝配置
    //push时记得清除
    public static final String alipayAppid = "2021000116697527";
    //私钥
    public static final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCby5sC7QjgOujzmjGSGObHCgpg77JCGrR9XRC8weprjwW7Dm34aWXMArwmQt2eKrBEBkhC1SYznz1jb0oF47T7SJ300/hhrI62S+e4KzmGzETRiS0lyTcwq8RAlWmT4R6vxorpdYiixqspnpAGMpbTHna6JJuHpdjhjkB1H4Wnoss2LmQlLam1UzlGJQEZRRcQ2uRWjMQ/3B/6z1SYWIB5N9AD3aGqXOpL/8Wol9MGqlReTL3Cu9P9Us3DWiOAsu1LBbOHjalHGSBeP0dt7F/yOlV7/q1IQ32iSKosmNgMFoSJVfpeQUZXN+pZJ0KY/VawkvYd79kKohBw/qlZYKR3AgMBAAECggEANI7Ke4E1llepUMz63huSdyE3AR671zslGomz5MnSaHi2aTVj+gKsedeOW6YBF6O1FS29YPaBTbbxwcLAiR4ZZxBHYVnFGEZbWrpesK28WXI94SZS2cV43e2EZnlBRccTUB89q6af8rHs+jxov/5/DTfgu8kvwuI7OVSBAEIjF7r34qSdzGRO0asUg/FWfMOb8Eq9vjrNo65UcETxBFCY5Vin9ahqCxQXOiM/JXvLxAQc7m75+I7wsM51de+M9RdBjzLq199g2oH1fDa6zeGtw8N/GiMj44xfHGYOnNEtQMQrRgMObdk+HQBK6jMtWmZiyL5txN/tCq+lEWsvdGsauQKBgQDM6ME3NVbu6k+fUAYXV8P9QgBtBy/RuOYrzxRxqUyq5WKSifiy0P46niDCM5oG5TiliNI3I5J7S1bHVUtyel1bqyNkQB2huv7/AAm2grb/64Ttn+Fg0nHlOqWdIDr3/Tba/oVyzmskP11pGuLP0C54ZNixedVNyrgFrS/O8cwl8wKBgQDCo/BcRY6OKvhp+lBlkaK30+TaHSlsH8rSFj8geSFVg7JxFIEXPhUZI1WmCnEw1dih5NJk6LlRGjSM+Sfdj8wwKKw5RRKr1NT4Fj5I7NLvQNUHjMunpf70RnYOC6munc+9nj2Ee6KJoTXgLHgqU/S01HYkmTMmeHg8Jgeab2yUbQKBgQCtOvTl5J0q/EH2EGQl5dfWSq8rfVDq/2LvlHDXKmabb7Nm6WesXM0g50AQjilgPPcn0sTNQrB6tZWWjUBsdpBoiAUyopWqfflHwVN0zYtCTm0hqvGRtjf9sBgE/xN7RcHTYCuXhHTFvyAMVGN9hqVSuQcM+0/eJUxv2Bs72+VYCwKBgCrdcdTQWLnEitrEDqqfrScw2iiS3Yl7Yu5ridF7+7RagZTXwCo/OORFXNppWE45+jCFNTSV8CIFVGdrg7UOsxg0DOG77YW8JH7SXzsuNLIOJBw6jKtKKmDfl6Y2OVNEW/jVfZTMqZoHz9aj1H716GFenPooZxRk2Y3djjUbrtztAoGBAKVbxQ3T5lT4/3+Zmt7KYfLVh44qf1Pb2I9HSoSycYfJAtGxjn4oR83mH5wL/WAJk/L2U4UbNl63dG04meDeqxw7P8/F5xWCeoyEk4+rF4PVCQJBSd67C3nU6yELTnD56qhE2r4dVKMRI1IhGcEKtZt6byDldlfroNzRSv28/OqU";
    //公钥
    public static final String AliPayPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String notify_url = "http://stu.jeehon.com/QRCodeMall/test/notify";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
    //public static final String return_url = "http://stu.jeehon.com/QRCodeMall/test/notify";
    public static final String return_url = "http://stu.jeehon.com/public";
    //sms

    public static String secretID = "AKIDEn14tbdWDAqGQLN4W5sNzToukPQ1rfjY";
    public static String secretKey = "lOlrzsWnocKm47xXBftrjMPCnhOpe0A3";
    public static int appId = 1400405375;
    public static String appKey = "807851ac5cb0733393579d4db75f618a";
    public static int templateId = 673642;
    public static String smsSign = "策联网络";


    /*
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

*/
}
