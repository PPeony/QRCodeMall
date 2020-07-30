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
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

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
    public Result insertToShoppingCart(@RequestBody Goods goods,HttpSession session) {
        Result result = new Result();
        //Cookie cookie = new Cookie();
        //查数据库，下架不能买
        //update或者insert到orderFormDetail表
        result.setCode(HttpStatus.OK.value());
        result.setMessage("添加成功");
        return result;
    }

    @GetMapping("/shoppingCart")//查看购物车所有东西
    //@ApiOperation()
    public Result<List<OrderFormDetail>> selectOrderFormDetail(@RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum
                                        ,HttpSession session) {
        //判断session不为空，注意分页
        Result<List<OrderFormDetail>> result = new Result<>();
        List<OrderFormDetail> list = new LinkedList<>();
        OrderFormDetail orderFormDetail = new OrderFormDetail();
        orderFormDetail.setGoodsName("testDetail");
        list.add(orderFormDetail);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功");
        result.setData(list);
        return result;
    }
}
