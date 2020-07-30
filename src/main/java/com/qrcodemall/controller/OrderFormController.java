package com.qrcodemall.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.controller.vo.AdminOrderFormVO;
import com.qrcodemall.entity.OrderForm;
import com.qrcodemall.entity.OrderFormDetail;
import com.qrcodemall.entity.User;
import com.qrcodemall.service.OrderFormService;
import com.qrcodemall.util.BeanUtil;
import com.qrcodemall.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    OrderFormService orderFormService;

    @PutMapping("/buyGoods")
    @ApiOperation(value = "不知道咋写业务，不知道咋返回，先起个名字，这是买东西用的")
    public Result buyGoods(@RequestBody OrderForm orderForm) {
        Result result = new Result();
        //应该唤醒支付方式在跳转，不会写，先放着
        result.setMessage("success");
        result.setCode(HttpStatus.OK.value());
        return result;
    }

    //user用的
    @GetMapping("/myOrderForm")
    public Result<PageInfo<OrderForm>> selectOneOrderForm(HttpSession session,
            @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
            @RequestParam(required = false,value = "beginTime") Date beginTime,
            @RequestParam(required = false,value = "endTime") Date endTime) {
        //判断session为不为空，分页
        Result<PageInfo<OrderForm>> result = new Result();
        User u = (User)session.getAttribute("user");
        PageInfo<OrderForm> list = orderFormService.selectOrderForm(u.getUserId(),beginTime,endTime,pageNum);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(list);
        return result;
    }

    //admin用的
    @GetMapping("/allOrderForms")
    public Result<PageInfo<AdminOrderFormVO>> selectAllOrderForms(
                                      @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
                                      @RequestParam(required = false,value = "beginTime") Date beginTime,
                                      @RequestParam(required = false,value = "endTime") Date endTime) {
        //分页
        Result<PageInfo<AdminOrderFormVO>> result = new Result();
        PageInfo pageInfo = orderFormService.selectAllOrderForms(beginTime,endTime,pageNum);

        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(buildOrderFormVO(pageInfo));
        return result;
    }

    private PageInfo<AdminOrderFormVO> buildOrderFormVO(PageInfo<OrderForm> pageInfo) {
        //System.out.println("OrderForm: = ");
        //System.out.println(pageInfo.getList().toString());
        List<AdminOrderFormVO> res = new LinkedList<>();
        List<OrderForm> forms = pageInfo.getList();
        for (OrderForm of : forms) {
            //System.out.println("ofId = "+of.getOrderFormId());
            AdminOrderFormVO aof = new AdminOrderFormVO();
            aof.setOrderForm(of);
            List<OrderFormDetail> tl = orderFormService.selectOrderFormDetailWithoutPage(of.getOrderFormId());
            //System.out.println(tl);
            aof.setDetails(tl);
            res.add(aof);
        }
        //System.out.println("res : = ");
        //System.out.println(res);
        PageInfo<AdminOrderFormVO> pres = new PageInfo<>();
        BeanUtil.copyProperties(pageInfo,pres,"list");
        pres.setList(res);
        return pres;
    }
    @GetMapping("/{orderFormId}")//查看订单详情
    public Result<List<OrderFormDetail>> selectOrderDetails(@PathVariable Integer orderFormId,
                                     @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {
        //orderDetail表照着id查
        Result<List<OrderFormDetail>> result = new Result<>();
        List<OrderFormDetail> list = orderFormService.selectOrderFormDetailWithoutPage(orderFormId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(list);
        return result;

    }
    @PutMapping("/updateOrderForm")
    public Result updateOrderForm(@RequestBody OrderForm orderForm) {
        Result result = new Result();
        orderFormService.updateOrderForm(orderForm);
        ///
        result.setCode(HttpStatus.OK.value());
        result.setMessage("update success");
        return result;
    }
    @DeleteMapping("/deleteOrderForm")
    public Result deleteOrderForm(Integer orderFormId) {
        Result result = new Result();
        orderFormService.deleteOrderForm(orderFormId);

        result.setCode(HttpStatus.OK.value());
        result.setMessage("delete success");
        return result;
    }
}
