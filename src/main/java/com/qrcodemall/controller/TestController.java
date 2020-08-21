package com.qrcodemall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qrcodemall.common.Property;
import com.qrcodemall.configure.AlipayConfig;
import com.qrcodemall.dao.NoticeMapper;
import com.qrcodemall.entity.Notice;
import com.qrcodemall.service.NoticeService;
import com.qrcodemall.util.PictureUtil;
import com.qrcodemall.util.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Peony
 * @Date: 2020/7/27 16:43
 */
@Controller
@RequestMapping("/test")
public class TestController {


    @GetMapping("/setSession")
    @ResponseBody
    public Result cookiek(String str,HttpSession session) {
        System.out.println(str);
        session.setAttribute("str",str);

        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        return result;
    }

    @GetMapping("/getSession")
    @ResponseBody
    public Result<String> getSession(HttpSession session) {
        String s = (String)session.getAttribute("str");
        System.out.println("session := "+s);
        Result<String> result = new Result<>();
        result.setData(s);
        return result;
    }



    @PostMapping("/post")
    @ResponseBody
    public String post(@RequestBody Map<String,Object> json, HttpSession session) {
        Integer orderFormId = (Integer)json.get("orderFormId");
        System.out.println(orderFormId);
        return "success";
    }



    @PostMapping("/upload")
    public String uploadFile(MultipartFile file, HttpServletRequest request) {
        return PictureUtil.uploadFile(file, request);
    }

    @GetMapping("/getPayTest")
    @ResponseBody
    public void payTest(@RequestParam("outTradeNo") String out_trade_no,
                        @RequestParam("totalAmount") String total_amount,
                        @RequestParam("subject") String subject,
                        HttpServletResponse response) throws IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //付款金额，必填
        //total_amount = new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
        //订单名称，必填
        //subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
        //商品描述，可空
        //String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
        String body = "";
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }


    @RequestMapping("/pay")
    @ResponseBody
    //必须restcontroller，而且方法不能带有返回值.或者直接用responsebody
    public void payController(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //付款金额，必填
        String total_amount = new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
        //订单名称，必填
        String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
        //商品描述，可空
        //String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

    @RequestMapping("/alipay")
    public String via() {
        System.out.println("via");
        return "alipay";
    }

    @GetMapping("/addCookie")
    @ResponseBody
    public String addCookie(@RequestParam("name") String name,@RequestParam("value") String value,HttpServletRequest request,HttpServletResponse response) {
        //String name = json.get("name");
        //String value = json.get("value");
        System.out.println(name+" "+value);
        Cookie cookie = new Cookie(name,value);
        cookie.setDomain("stu.hrbkyd.com");
        cookie.setHttpOnly(false);
        cookie.setPath(request.getContextPath());
        System.out.println(request.getServerName()+" "+request.getContextPath());
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        //response.setHeader("Set_Cookie","");
        return "success";
    }

    @GetMapping("/getCookie")
    @ResponseBody
    public String getCookie(@RequestParam("name") String name,HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "no cookies";
        }
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName()+" "+cookie.getValue());
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return "no such cookie";
    }

    @GetMapping("/deleteCookie")
    @ResponseBody
    public String deleteCookie(String names, HttpServletRequest request,HttpServletResponse response) {
        System.out.println("names:"+names);
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "no cookies";
        }
        for (int i = 0; i < cookies.length; i++) {
            if (names.equals(cookies[i].getValue())) {
                cookies[i].setMaxAge(0);
                cookies[i].setValue(null);
                cookies[i].setDomain("stu.hrbkyd.com");
                cookies[i].setHttpOnly(false);
                cookies[i].setPath(request.getContextPath());
                System.out.println("delete: "+cookies[i].getName());
                response.addCookie(cookies[i]);
            }
        }
        return "success";
    }

    @GetMapping("/sendList")
    @ResponseBody
    public String sendList(@RequestParam("list") ArrayList<Integer> list) {
        System.out.println(list);
        return "success";
    }

}
