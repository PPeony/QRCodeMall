package com.qrcodemall.controller;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.qrcodemall.common.Property;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

//导入可选配置类
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

// 导入 SMS 模块的 client
import com.tencentcloudapi.sms.v20190711.SmsClient;

// 导入要请求接口对应的 request response 类
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: Peony
 * @Date: 2020/7/31 19:22
 */
@RestController
@RequestMapping("/sms")
public class SendSMS
{
    //上传时注意清除
    String secretID = Property.secretID;
    String secretKey = Property.secretKey;
    int appId = Property.appId;
    String appKey = Property.appKey;
    int templateId = Property.templateId;
    public String[] phones={"18846039727"};

    String smsSign = Property.smsSign;

    @GetMapping("/send")
    public void sms()
    {
        try {
            //短信模板中的参数列表
            //第一个为验证码，第二个是几分钟有效
            String[] params = {"4567","5"};

            SmsSingleSender sender = new SmsSingleSender(appId, appKey);
            SmsSingleSenderResult result = sender.sendWithParam("86", phones[0],
                    templateId, params, smsSign, "", "");
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        } catch (com.github.qcloudsms.httpclient.HTTPException e) {
            e.printStackTrace();
        }
    }
}
