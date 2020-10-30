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
import com.qrcodemall.util.CookieUtils;
import com.qrcodemall.util.PictureUtil;
import com.qrcodemall.util.RedisUtil;
import com.qrcodemall.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
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
@Api(tags = "测试方法，正式发布时候删除")
public class TestController {

    @Resource
    private RedisUtil redisUtil;

    /**redis***/
    @GetMapping("/redisSet")
    @ResponseBody
    public Result redisSet(String key,String value) {
        boolean f = redisUtil.set(key,value);
        redisUtil.expire(key,Property.REDIS_EXPIRE_TIME);
        Result result = new Result();
        if (f) {
            return result.code(200).message("success");
        } else {
            return result.code(500).message("server error");
        }
    }

    @GetMapping("/redisGet")
    @ResponseBody
    public Result redisGet(String key) {
        Object o = redisUtil.get(key);
        Result result = new Result();
        return result.code(200).data(o).message("success");
    }
    @GetMapping("/redisRemove")
    @ResponseBody
    public Result redisRemove(String key) {
        redisUtil.del(key);
        return new Result().code(200).message("delete success");
    }
    /*****/
    private final String domain = "stu.hrbkyd.com";

    //"stu.hrbkyd.com"
    @GetMapping("/setSession")
    @ResponseBody
    public Result cookiek(String str,HttpSession session,HttpServletRequest request,HttpServletResponse response) {
        System.out.println(str);
        session.setAttribute("str",str);
        String id = session.getId();
        System.out.println("test get id: "+id);

        HttpCookie cookie = CookieUtils.generateSetCookie2(request, "JSESSIONID", id, Duration.ofHours(3));
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());


        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        return result;
    }

    @GetMapping("/getSession")
    @ResponseBody
    public Result<String> getSession(HttpSession session) {
        System.out.println("getSessionId = "+session.getId());
        String s = (String)session.getAttribute("str");
        System.out.println("session := "+s);
        Result<String> result = new Result<>();
        result.setData(s);
        return result;
    }



    @PostMapping("/post")
    @ResponseBody
    public String post(@RequestParam("Name") String name,@RequestParam("age") Integer age) {
        System.out.println("==="+name+"==="+age+"===");
        return "success";
    }

    @GetMapping("/get")
    @ResponseBody
    public String getTest(@RequestParam("Name") String name,HttpServletRequest request) {
        System.out.println(request);
        System.out.println("==="+name+"===");
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
        cookie.setDomain(domain);
        cookie.setHttpOnly(false);
        cookie.setPath("*");
        //System.out.println(request.getServerName()+" "+request.getContextPath());
        cookie.setMaxAge(60*60*24);

        response.setHeader("Set-Cookie", "HttpOnly;Secure;SameSite=None");
        response.addCookie(cookie);
        //new
        /*
        ResponseCookie cookie = ResponseCookie.from("myCookie", "myCookieValue") // key & value
                .httpOnly(true)		// 禁止js读取
                .secure(false)		// 在http下也传输
                .domain("localhost")// 域名
                .path("/")			// path
                .maxAge(Duration.ofHours(1))	// 1个小时候过期
                .sameSite("Lax")	// 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外
                .build()
                ;

        // 设置Cookie Header
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

         */
        return "success";
    }

    @GetMapping("/addCookie2")
    @ResponseBody
    public String addCookie2(@RequestParam("name") String name,@RequestParam("value") String value,HttpServletRequest request,HttpServletResponse response) {
        //String name = json.get("name");
        //String value = json.get("value");
        System.out.println(name+" "+value);
        /*
        Cookie cookie = new Cookie(name,value);
        cookie.setDomain(domain);
        cookie.setHttpOnly(false);
        cookie.setPath(request.getContextPath());
        //System.out.println(request.getServerName()+" "+request.getContextPath());
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        response.setHeader("Set-Cookie", "HttpOnly;Secure;SameSite=None");

         */
        //new
        HttpCookie cookie = CookieUtils.generateSetCookie(request, name, value,Duration.ofHours(24 * 7));
        // 设置Cookie Header
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return "success";
    }

    @GetMapping("/addCookie3")//第一次尝试这么写是ok的
    @ResponseBody
    public String addCookie3(@RequestParam("name") String name,@RequestParam("value") String value,HttpServletRequest request,HttpServletResponse response) {
        //String name = json.get("name");
        //String value = json.get("value");
        System.out.println(name+" "+value);

        Cookie cookie = new Cookie(name,value);
        cookie.setDomain(domain);
        cookie.setHttpOnly(false);
        cookie.setPath(request.getContextPath());
        //System.out.println(request.getServerName()+" "+request.getContextPath());
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        //response.setHeader("Set-Cookie", "HttpOnly;Secure;SameSite=None");
        String s = name+"="+value+";";
        response.setHeader("Set-Cookie",s + "Path=*; SameSite=None; Secure");

        //new
        /*
        ResponseCookie cookie = ResponseCookie.from(name, value) // key & value
                .httpOnly(true)		// 禁止js读取
                .secure(false)		// 在http下也传输
                .domain(domain)// 域名
                .path("*")			// path
                .maxAge(Duration.ofHours(1))	// 1个小时候过期
                .sameSite("Lax")	// 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外
                .build()
                ;

        // 设置Cookie Header
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

         */


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
        //String delete = "";
        for (int i = 0; i < cookies.length; i++) {
            if (names.equals(cookies[i].getValue())) {
                ResponseCookie cookie = (ResponseCookie)CookieUtils.generateSetCookie(request,names,null,Duration.ZERO);
                response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
                break;
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

    @GetMapping("/notify")
    public void notify(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("notify");
        String out_trade_no = request.getParameter("out_trade_no");
        System.out.println(out_trade_no);
        response.setContentType("text/html;charset=UTF-8");
        String site = new String("http://www.runoob.com");
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);
    }

}
