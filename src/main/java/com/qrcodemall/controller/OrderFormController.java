package com.qrcodemall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.Exception.GlobalException;
import com.qrcodemall.configure.AlipayConfig;
import com.qrcodemall.controller.vo.OrderFormVO;
import com.qrcodemall.entity.*;
import com.qrcodemall.service.OrderFormService;
import com.qrcodemall.service.UserBillService;
import com.qrcodemall.util.BeanUtil;
import com.qrcodemall.util.OrderFormNumberGenerator;
import com.qrcodemall.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Peony
 * @Date: 2020/7/24 15:42
 */
@RestController
@RequestMapping("/orderForm")
public class OrderFormController {

    @Autowired
    OrderFormService orderFormService;

    @Autowired
    UserBillService userBillService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;


    @GetMapping("/buyGoods")
    //todo,应该先生成订单，再跳转支付宝,功能应该是实现了，出问题再说
    @ApiOperation(value = "传参数是orderForm信息，id，number，payType，price一定要有,payType为2为微信支付，不会跳转到支付宝")
    public void buyGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //System.out.println("buyGoods :"+json);
        HttpSession session = request.getSession();
        /*
        User u = (User)session.getAttribute("user");
        if (u == null) {
            GlobalException.fail("未登录");
            return;
        }

         */
        Integer id = Integer.valueOf(request.getParameter("orderFormId"));
        String number = request.getParameter("orderFormNumber");
        BigDecimal price = new BigDecimal(request.getParameter("orderFormPrice"));
        Integer payType = Integer.valueOf(request.getParameter("orderFormPayType"));
        System.out.println(id+" "+number+" "+price+" "+payType);
        OrderForm orderForm = new OrderForm();
        orderForm.setOrderFormPayType(payType);
        orderForm.setOrderFormId(id);
        //在这里就写入时间，跳转支付宝过程中写入的话不会写
        orderForm.setOrderFormPayTime(new Date());
        orderFormService.updateOrderForm(orderForm);
        if (payType == 2) {
            return;
        }
        //唤醒支付宝：
        aliPay(number,price.toString(),number,response);
        /*
        String id = OrderFormNumberGenerator.generate();
        orderForm.setOrderFormNumber(id);
        orderFormService.insertOrderForm(orderForm);
        Integer orderFormId = orderFormService.selectByOrderFormNumber(id).getOrderFormId();
        Cookie[] cookies = request.getCookies();
        result.setMessage("success");
        result.setCode(HttpStatus.OK.value());
         */
    }

    private void aliPay(String out_trade_no,String total_amount,String subject,HttpServletResponse response) throws IOException {

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //付款金额，必填
        //total_amount = new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
        //订单名称，必填
        //subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
        //商品描述，可空
        //String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
        String body = "";
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

    //todo,支付宝/微信，异步通知，修改订单状态
    @GetMapping("/buyingSuccessfully")
    @ApiOperation(value = "参数为订单号和总金额")
    public Result buyingSuccessfully(@RequestParam("orderFormNumber") String orderFormNumber,
                                     @RequestParam("totalAmount") String totalAmount,
                                     HttpSession session) {
        Result result = new Result();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.code(HttpStatus.UNAUTHORIZED.value()).message("未登录");
            return result;
        }
        orderFormService.buyingSuccessfully(orderFormNumber);
        BigDecimal decimal = new BigDecimal(totalAmount);
        UserBill userBill = new UserBill();
        userBill.setUserId(user.getUserId());
        userBill.setUserBillMoney(decimal);
        userBill.setUserBillDirection(1);
        userBillService.insertUserBill(userBill);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @PostMapping("/generateOrderForm")
    @ApiOperation(value = "生成订单,只返回orderForm，想要返回vo再请求别的接口")
    public Result<OrderForm> generateOrderForm(@RequestBody List<Goods> list) {
        System.out.println("generateOrderForm : "+list);
        Result<OrderForm> result = new Result<>();
        if (list == null || list.size() == 0) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("请选择商品");
            return result;
        }
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");
        if (user == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("请登录");
            return result;
        }
        OrderForm orderForm = orderFormService.generateOrderForm(list,user);
        result.setData(orderForm);
        result.setCode(HttpStatus.OK.value());
        return result;
    }


    //user用的
    @GetMapping("/myOrderForm")
    public Result<PageInfo<OrderFormVO>> selectOneOrderForm(HttpSession session,
            @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
            @RequestParam(required = false,value = "beginTime") Date beginTime,
            @RequestParam(required = false,value = "endTime") Date endTime) {
        //判断session为不为空，分页
        Result<PageInfo<OrderFormVO>> result = new Result();
        User u = (User)session.getAttribute("user");
        if (u == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("未登录");
            return result;
        }
        PageInfo<OrderForm> list = orderFormService.selectOrderForm(u.getUserId(),beginTime,endTime,pageNum);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(buildOrderFormVO(list));
        return result;
    }

    //admin用的
    @GetMapping("/allOrderForms")
    public Result<PageInfo<OrderFormVO>> selectAllOrderForms(
                                      @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
                                      @RequestParam(required = false,value = "beginTime") Date beginTime,
                                      @RequestParam(required = false,value = "endTime") Date endTime) {
        //分页
        Result<PageInfo<OrderFormVO>> result = new Result();
        PageInfo pageInfo = orderFormService.selectAllOrderForms(beginTime,endTime,pageNum);

        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(buildOrderFormVO(pageInfo));
        return result;
    }

    private PageInfo<OrderFormVO> buildOrderFormVO(PageInfo<OrderForm> pageInfo) {
        //System.out.println("OrderForm: = ");
        //System.out.println(pageInfo.getList().toString());
        List<OrderFormVO> res = new LinkedList<>();
        List<OrderForm> forms = pageInfo.getList();
        for (OrderForm of : forms) {
            //System.out.println("ofId = "+of.getOrderFormId());
            OrderFormVO aof = new OrderFormVO();
            aof.setOrderForm(of);
            List<OrderFormDetail> tl = orderFormService.selectOrderFormDetailWithoutPage(of.getOrderFormId());
            //System.out.println(tl);
            aof.setDetails(tl);
            res.add(aof);
        }
        //System.out.println("res : = ");
        //System.out.println(res);
        PageInfo<OrderFormVO> pres = new PageInfo<>();
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
