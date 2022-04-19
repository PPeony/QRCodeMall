package com.qrcodemall.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;

import static com.qrcodemall.common.Property.*;

/**
 * @Author: Peony
 * @Date: 2022/3/1 14:56
 */
public class SendSms {
    public static String sms(String phone)
    {
        String verifyCode = String.valueOf(Math.round((Math.random() * 9 + 1) * 1000));
        try {
            //短信模板中的参数列表
            //第一个为验证码，第二个是几分钟有效

            String[] params = {verifyCode,"1"};
            SmsSingleSender sender = new SmsSingleSender(appId, appKey);
            SmsSingleSenderResult result = sender.sendWithParam("86", phone,
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
        return verifyCode;
    }
}
