package com.qrcodemall.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.controller.vo.PromotionGoodsVO;
import com.qrcodemall.entity.Goods;
import com.qrcodemall.entity.GoodsType;
import com.qrcodemall.service.GoodsService;
import com.qrcodemall.service.GoodsTypeService;
import com.qrcodemall.util.CookieUtils;
import com.qrcodemall.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.Cleanup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Peony
 * @Date: 2020/7/24 15:21
 */
@RestController
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    GoodsTypeService goodsTypeService;

    @Autowired
    JedisPool jedisPool;


    @GetMapping("/allGoods")
    @ApiOperation(value = "唯一参数pageNum，页号(1开始)。每页大小写死了")
    public Result<PageInfo<Goods>> selectAllGoods(@RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {
        Result<PageInfo<Goods>> result = new Result<>();
        //分页
        PageInfo<Goods> list = goodsService.selectAllGoods(pageNum);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功获取所有商品信息");
        result.setData(list);
        return result;
    }

    @GetMapping("/goodsType")
    @ApiOperation(value = "no param")
    public Result<List<GoodsType>> selectAllGoodsType(@RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {
        Result<List<GoodsType>> result = new Result<>();
        //不分页
        List<GoodsType> list = goodsTypeService.selectAllGoodsType();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功获取所有种类信息");
        result.setData(list);
        return result;
    }

    @GetMapping("/{goodsId}")
    @ApiOperation(value = "唯一参数goodsId")
    public Result<Goods> selectGoods(@PathVariable Integer goodsId) {
        Result<Goods> result = new Result();
        Goods goods = goodsService.selectGoods(goodsId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功获取信息");
        result.setData(goods);
        return result;
    }

    @PostMapping("/addToShoppingCart")
    @ApiOperation(value = "一次对goodsid商品计数器加一,唯一参数goodsId")
    public Result insertToShoppingCart(@RequestBody Map<String,String> json, HttpServletResponse response, HttpServletRequest request) {
        System.out.println("addToShoppingCart");
        System.out.println(json);
        Result result = new Result();
        String goodsId = json.get("goodsId");
        Integer id = Integer.valueOf(goodsId);
        Goods sg = goodsService.selectGoods(id);
        //查数据库，下架不能买
        if (sg.getIsDeleted() == 1) {
            //已下架，不让买
            result.setCode(HttpStatus.NOT_FOUND.value());
            result.setMessage("商品已下架");
            return result;
        }
        //Cookie cookie = new Cookie();
        Cookie add = null;
        Cookie[] cookies = request.getCookies();
        String name = null,value = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(goodsId)) {
                    name = c.getName();
                    value = String.valueOf(Integer.valueOf(c.getValue() + 1));
                    break;
                }
            }
        }
        if (name == null) {
            name = goodsId;
            value = "1";
        }
        /*
        add.setDomain("stu.hrbkyd.com");
        add.setHttpOnly(false);
        add.setPath("*");
        add.setMaxAge(60*60*24);
        System.out.println("contextPath = "+request.getContextPath());
        System.out.println("ServerName = "+request.getServerName());
        response.addCookie(add);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

         */
        HttpCookie cookie = CookieUtils.generateSetCookie(request, name, value, Duration.ofHours(24 * 7));
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        result.setCode(HttpStatus.OK.value());
        result.setMessage("添加成功");
        return result;
    }

    @GetMapping("/shoppingCart")//查看购物车所有东西
    @ApiOperation(value = "no param")
    public Result<List<Goods>> selectOrderFormDetail(HttpServletRequest request) {
        System.out.println("shoppingCart");
        Result<List<Goods>> result = new Result<>();
        /*
        if (session.getAttribute("user") == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("请重新登录");
            return result;
        }
         */
        List<Goods> list = new LinkedList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {

            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if (!StringUtils.isNumeric(c.getName())) {
                    System.out.println(c.getName());
                    continue;
                }
                System.out.println("c.getName() = "+c.getName());

                Integer goodsId = Integer.valueOf(c.getName());
                System.out.println("goodsId = "+goodsId);
                list.add(goodsService.selectGoods(goodsId));
            }
        } else {
            System.out.println("cookies null!!!!");
        }

        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功");
        result.setData(list);
        return result;
    }

    @PostMapping("/deleteShoppingCartGoods")
    @ApiOperation(value = "删除购物车某一项，传goodsId,jsonExample:[1,2]",notes = "jsonExample:[1,2]")
    //jsonExample:[1,2],urlExample:?goodsIdList=1,2,3,4
    //
    public Result deleteOne(@RequestBody ArrayList<Integer> goodsIdList,HttpServletRequest request,HttpServletResponse response) {
        Result result = new Result();
        //System.out.println("deleteOne");
        //System.out.println(goodsIdList);
        Cookie[] cookies = request.getCookies();
        String delete = "";
        if (cookies != null) {
            for (int i = 0; i < goodsIdList.size(); i++) {
                Cookie c = cookies[i];
                //System.out.println("cookies name:" + c.getName());
                if (!StringUtils.isNumeric(c.getName())) {
                    continue;
                } else {
                    if (goodsIdList.contains(Integer.valueOf(c.getName()))) {
                        //System.out.println("contains: "+c.getName());
                        //c = deleteCookie(c,request);
//                        c.setMaxAge(0);
//                        c.setPath("*");
//                        c.setDomain("stu.hrbkyd.com");
//                        c.setHttpOnly(false);
                        //System.out.println("cookie = "+c);
                        //System.out.println(c.getName()+" "+c.getDomain()+" "+c.getMaxAge());
                        //response.addCookie(c);
                        ResponseCookie cookie = (ResponseCookie)CookieUtils.generateSetCookie(request,c.getName(),null,Duration.ZERO);
                        delete += cookie.toString()+";";
                    } else {
                        System.out.println("no contains");
                    }
                }
            }
            response.setHeader(HttpHeaders.SET_COOKIE, delete);
        }

        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }
    private Cookie deleteCookie(Cookie c,HttpServletRequest request) {
        c.setMaxAge(0);
        c.setValue(null);
        c.setDomain("stu.hrbkyd.com");
        c.setHttpOnly(false);
        c.setPath("*");
        return c;
    }
    @DeleteMapping("/deleteAllShoppingCartGoods")
    @ApiOperation("no params")
    public Result deleteAll(HttpServletRequest request,HttpServletResponse response) {
        Result result = new Result();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if (StringUtils.isNumeric(c.getName())) {
                    continue;
                }
                c = deleteCookie(c,request);
                response.addCookie(c);
            }
        }
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @GetMapping("/getPromotionGoods")
    public Result getPromotionGoods() {
        System.out.println("getPromotionGoods ---");
        Result result = new Result();
        List<PromotionGoodsVO> promotionGoodsVOList = goodsService.selectPromotionGoods();
        result.data(promotionGoodsVOList);
        if (promotionGoodsVOList.size() == 0) {
            return result.code(HttpStatus.OK.value()).message("目前暂时没有促销活动");
        }
        return result.code(HttpStatus.OK.value());
    }
    @GetMapping("/getPromotionGoodsByPK")
    public Result getPromotionGoodsByPK(Integer promotionId) {
        Result result = new Result();
//        先查redis,redis没有再查数据库
        @Cleanup Jedis jedis = jedisPool.getResource();
        PromotionGoodsVO vo;
        vo = JSONObject.parseObject(jedis.get(String.valueOf(promotionId)),PromotionGoodsVO.class);
        List<PromotionGoodsVO> list = new ArrayList<>();
        if (vo != null) {
            list.add(vo);
            return Result.generateSuccessResult(list,null);
        }
        vo = goodsService.getPromotionGoodsByPK(promotionId);
        if (vo != null) {
            list.add(vo);
            return Result.generateSuccessResult(list,null);
        }
        return Result.generateSuccessResult(null,"该活动已结束");


    }




}
