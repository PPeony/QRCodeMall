package com.qrcodemall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.Property;
import com.qrcodemall.configure.AlipayConfig;
import com.qrcodemall.dao.NoticeMapper;
import com.qrcodemall.dao.PromotionMapper;
import com.qrcodemall.entity.Notice;
import com.qrcodemall.entity.OrderForm;
import com.qrcodemall.entity.PromotionGoods;
import com.qrcodemall.service.NoticeService;
import com.qrcodemall.service.OrderFormService;
import com.qrcodemall.util.CookieUtils;
import com.qrcodemall.util.JedisUtil;
import com.qrcodemall.util.PictureUtil;
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
import redis.clients.jedis.Jedis;

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
    OrderFormService orderFormService;

    @Resource
    PromotionMapper promotionMapper;
    @Autowired
    JedisUtil jedisUtil;

    @GetMapping("/mysql")
    @ResponseBody
    public Result testMysql() {
        PromotionGoods pg = new PromotionGoods();
        pg.setGoodsId(1);
        promotionMapper.insert(pg);
        return Result.generateSuccessResult(pg,"success");
    }

    @GetMapping("/setRedis")
    @ResponseBody
    public Result setRedis(String key,String value) {
        Jedis jedis = jedisUtil.getJedis();
        String r = jedis.set(key, value);
        return Result.generateSuccessResult(r,"success");
    }

    @GetMapping("/getRedis")
    @ResponseBody
    public Result getRedis(String key) {
        Jedis jedis = jedisUtil.getJedis();
        String r = jedis.get(key);
        return Result.generateSuccessResult(r,"success");
    }

}
