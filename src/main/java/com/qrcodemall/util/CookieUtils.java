package com.qrcodemall.util;

import com.alibaba.fastjson.JSON;
import com.qrcodemall.common.Property;
import org.apache.http.cookie.Cookie;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;

/**
 * @Author: Peony
 * @Date: 2022/4/1 17:17
 */
public class CookieUtils {
    //todo，domain需要随着域名修改
    private final static String domain = "ip";
    public static HttpCookie generateSetCookie(HttpServletRequest request, String name, String value,Duration duration) {
        ResponseCookie cookie = ResponseCookie.from(name, value) // key & value
                //.secure(true)		// 在https下传输,配合none使用
                .domain(domain)// 域名
                .path("*")			// path
                .maxAge(duration)	// 过期时间
                //.sameSite("None")	// 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外Lax或者none
                .build()
                ;
        return cookie;
    }
    public static HttpCookie generateSetCookie2(HttpServletRequest request, String name, String value,Duration duration) {
        ResponseCookie cookie = ResponseCookie.from(name, value) // key & value
                //.secure(true)		// 在https下传输,配合none使用
                .domain(domain)// 域名
                .path("/")			// path
                .maxAge(duration)	// 过期时间
                .sameSite("Lax")	// 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外Lax或者none
                .build()
                ;
        return cookie;
    }
    public static HttpCookie generateSetCookie3(HttpServletRequest request, String name, String value,Duration duration) {
        ResponseCookie cookie = ResponseCookie.from(name, value) // key & value
                //.secure(true)		// 在https下传输,配合none使用
                .domain(domain)// 域名
                .path("/")			// path
                .maxAge(duration)	// 过期时间
                //.sameSite("None")	// 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外Lax或者none
                .build()
                ;
        return cookie;
    }


}
