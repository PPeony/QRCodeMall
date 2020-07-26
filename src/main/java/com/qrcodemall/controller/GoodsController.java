package com.qrcodemall.controller;

import com.qrcodemall.entity.Goods;
import com.qrcodemall.entity.GoodsType;
import com.qrcodemall.entity.OrderFormDetail;
import com.qrcodemall.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/allGoods")
    public Result selectAllGoods(@RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {
        Result<List<Goods>> result = new Result<>();
        //分页
        List<Goods> list = new LinkedList<>();
        Goods goods = new Goods();
        goods.setGoodsName("testA");
        list.add(goods);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功获取所有商品信息");
        result.setData(list);
        return result;
    }

    @GetMapping("/goodsType")
    public Result selectAllGoodsType(@RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {
        Result<List<GoodsType>> result = new Result<>();
        //分页
        List<GoodsType> list = new LinkedList<>();
        GoodsType gt = new GoodsType();
        gt.setGoodsTypeName("testA");
        list.add(gt);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功获取所有种类信息");
        result.setData(list);
        return result;
    }

    @GetMapping("/{goodsId}")
    public Result selectGoods(@PathVariable Integer goodsId) {
        Result<Goods> result = new Result();
        Goods goods = new Goods();
        goods.setGoodsName("testName");
        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功获取信息");
        result.setData(goods);
        return result;
    }

    @PostMapping("/addToShoppingCart")
    public Result insertToShoppingCart(@RequestBody Goods goods,HttpSession session) {
        Result result = new Result();
        //查数据库，下架不能买
        //update或者insert到orderFormDetail表
        result.setCode(HttpStatus.OK.value());
        result.setMessage("添加成功");
        return result;
    }

    @GetMapping("/shoppingCart")//查看购物车所有东西
    public Result selectOrderFormDetail(@RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum
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
