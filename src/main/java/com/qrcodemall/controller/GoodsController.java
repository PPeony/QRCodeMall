package com.qrcodemall.controller;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.entity.Goods;
import com.qrcodemall.entity.GoodsType;
import com.qrcodemall.entity.OrderFormDetail;
import com.qrcodemall.service.GoodsService;
import com.qrcodemall.service.GoodsTypeService;
import com.qrcodemall.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    GoodsTypeService goodsTypeService;

    @GetMapping("/allGoods")
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
    public Result<Goods> selectGoods(@PathVariable Integer goodsId) {
        Result<Goods> result = new Result();
        Goods goods = goodsService.selectGoods(goodsId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功获取信息");
        result.setData(goods);
        return result;
    }

    @PostMapping("/addToShoppingCart")
    @ApiOperation(value = "一次对goodsid商品计数器加一")
    public Result insertToShoppingCart(@RequestBody Map<String,String> json, HttpServletResponse response, HttpServletRequest request) {
        System.out.println(json);
        Result result = new Result();
        String goodsId = json.get("goodsId");
        Integer id = Integer.valueOf(goodsId);
        Goods sg = goodsService.selectGoods(id);
        if (sg.getIsDeleted() == 1) {
            //已下架，不让买
            result.setCode(HttpStatus.NOT_FOUND.value());
            result.setMessage("商品已下架");
            return result;
        }
        //Cookie cookie = new Cookie();
        Cookie add;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(goodsId)) {
                    add = new Cookie(goodsId,c.getValue() + 1);
                    break;
                }
            }
        }

        add = new Cookie(goodsId,"1");
        response.addCookie(add);
        //查数据库，下架不能买

        result.setCode(HttpStatus.OK.value());
        result.setMessage("添加成功");
        return result;
    }

    @GetMapping("/shoppingCart")//查看购物车所有东西
    //@ApiOperation()
    public Result<List<Goods>> selectOrderFormDetail(HttpServletRequest request) {

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
            for (Cookie c : cookies) {

                Integer goodsId = Integer.valueOf(c.getName());
                list.add(goodsService.selectGoods(goodsId));
            }
        }

        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功");
        result.setData(list);
        return result;
    }
}
