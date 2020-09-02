package com.qrcodemall.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @Author: Peony
 * @Date: 2020/8/10 11:42
 */
@Configuration
//0作用
public class SpringSessionConfig {


    @Bean
    public CookieSerializer httpSessionIdResolver() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        // 取消仅限同一站点设置
        cookieSerializer.setSameSite(null);
        cookieSerializer.setUseSecureCookie(true);
        cookieSerializer.setUseHttpOnlyCookie(false);
        cookieSerializer.setCookiePath("*");
        cookieSerializer.setDomainNamePattern("stu.hrbkyd.com");
        return cookieSerializer;
    }
}

