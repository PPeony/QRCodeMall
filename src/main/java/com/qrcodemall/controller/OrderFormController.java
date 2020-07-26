package com.qrcodemall.controller;

import com.qrcodemall.entity.OrderForm;
import com.qrcodemall.entity.OrderFormDetail;
import com.qrcodemall.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/24 15:42
 */
@RestController
@RequestMapping("/orderForm")
public class OrderFormController {

    @PutMapping("/buyGoods")
    public Result buyGoods(@RequestBody OrderForm orderForm) {
        Result result = new Result();
        //应该唤醒支付方式在跳转，不会写，先放着
        result.setMessage("success");
        result.setCode(HttpStatus.OK.value());
        return result;
    }

    //user用的
    @GetMapping("/myOrderForm")
    public Result selectOneOrderForm(HttpSession session,
            @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
            @RequestParam(required = false,value = "beginTime") Date beginTime,
            @RequestParam(required = false,value = "endTime") Date endTime) {
        //判断session为不为空，分页
        Result<List<OrderForm>> result = new Result();
        List<OrderForm> list = new LinkedList<>();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(list);
        return result;
    }

    //admin用的
    @GetMapping("/allOrderForms")
    public Result selectAllOrderForms(
                                      @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
                                      @RequestParam(required = false,value = "beginTime") Date beginTime,
                                      @RequestParam(required = false,value = "endTime") Date endTime) {
        //分页
        Result<List<OrderForm>> result = new Result();
        List<OrderForm> list = new LinkedList<>();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(list);
        return result;
    }

    @GetMapping("/{orderFormId}")//查看订单详情
    public Result selectOrderDetails(@PathVariable Integer orderFormId,
                                     @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {
        //orderDetail表照着id查
        Result<List<OrderFormDetail>> result = new Result<>();
        List<OrderFormDetail> list = new LinkedList<>();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(list);
        return result;

    }
    @PutMapping("/updateOrderForm")
    public Result updateOrderForm(@RequestBody OrderForm orderForm) {
        Result result = new Result();
        ///
        result.setCode(HttpStatus.OK.value());
        result.setMessage("update success");
        return result;
    }
    @DeleteMapping("/deleteOrderForm")
    public Result deleteOrderForm(Integer orderFormId) {
        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("delete success");
        return result;
    }
}
