package com.qrcodemall.util;

import org.apache.http.cookie.Cookie;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;

/**
 * @Author: Peony
 * @Date: 2022/4/1 17:17
 */
public class CookieUtils {
    private final static String domain = "stu.hrbkyd.com";
    public static HttpCookie generateSetCookie(HttpServletRequest request, String name, String value,Duration duration) {
        ResponseCookie cookie = ResponseCookie.from(name, value) // key & value
                .secure(true)		// 在https下传输,配合none使用
                .domain(domain)// 域名
                .path("*")			// path
                .maxAge(duration)	// 过期时间
                .sameSite("None")	// 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外Lax或者none
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
                .secure(true)		// 在https下传输,配合none使用
                .domain(domain)// 域名
                .path("/")			// path
                .maxAge(duration)	// 过期时间
                .sameSite("None")	// 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外Lax或者none
                .build()
                ;
        return cookie;
    }
}
