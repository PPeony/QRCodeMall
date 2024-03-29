package com.qrcodemall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.OrderDetail;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.Exception.GlobalException;
import com.qrcodemall.common.Property;
import com.qrcodemall.configure.AlipayConfig;
import com.qrcodemall.controller.vo.OrderFormVO;
import com.qrcodemall.entity.*;
import com.qrcodemall.entity.Test;
import com.qrcodemall.service.AccountService;
import com.qrcodemall.service.OrderFormService;
import com.qrcodemall.service.UserBillService;
import com.qrcodemall.service.UserService;
import com.qrcodemall.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Cleanup;
import org.apache.http.HttpResponse;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Peony
 * @Date: 2022/3/4 15:42
 */
@RestController
@RequestMapping("/orderForm")
public class OrderFormController {

    @Autowired
    OrderFormService orderFormService;

    @Autowired
    UserBillService userBillService;

    @Autowired
    AccountService accountService;

    @Resource
    UserService userService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    JedisUtil jedisUtil;

    @Autowired
    SessionUtil sessionUtil;


    @GetMapping("/buyGoods")
    //应该先生成订单，再跳转支付宝,功能应该是实现了，出问题再说
    @ApiOperation(value = "传参数是orderForm信息，id，number，payType，price一定要有,payType为2为微信支付，不会跳转到支付宝。" +
            "注意此方法没有返回值，支付宝是通过response重定向，微信暂无")
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


    @GetMapping("/buyingSuccessfully")
    @ApiOperation(value = "参数为订单号orderFormNumber和总金额totalAmount")
//    out_trade_no and total_amount
    public Result buyingSuccessfully(String orderFormNumber,
                                     String totalAmount,
                                     HttpSession session) {
        System.out.println("orderFormNumber = "+orderFormNumber+" totalAmount = "+totalAmount);
        if (orderFormNumber == null || totalAmount == null) {
            return Result.generateSuccessResult(null,null);
        }
        Result result = new Result();
        //User user = (User) session.getAttribute("user");
        User user = (User) sessionUtil.getSession(Property.userSessionPrefix+session.getId(),User.class);
        if (user == null) {
            result.code(HttpStatus.UNAUTHORIZED.value()).message("未登录");
            return result;
        }
        //更新该用户的父级节点积分，只会在第一次买的时候更新
        userService.addPoint(user);
        //更新orderForm表
        orderFormService.buyingSuccessfully(orderFormNumber);
        BigDecimal decimal = new BigDecimal(totalAmount);
        UserBill userBill = new UserBill();
        userBill.setUserId(user.getUserId());
        userBill.setUserBillMoney(decimal);
        userBill.setUserBillDirection(1);
        //更新userBill表
        userBillService.insertUserBill(userBill);
        //更新account表
        OrderForm orderForm = orderFormService.selectByOrderFormNumber(orderFormNumber);
        List<OrderFormDetail> details = orderFormService.selectOrderFormDetailWithoutPage(orderForm.getOrderFormId());
        for (OrderFormDetail detail : details) {
            Account account = new Account();
            account.setUserId(user.getUserId());
            account.setGoodsTypeName(detail.getGoodsTypeName());
            account.setGoodsTypeQrcodeQuantity(detail.getGoodsQrcodeQuantity());
            accountService.insertAccount(account);
        }

        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @PostMapping("/generateOrderForm")
    @ApiOperation(value = "生成订单,只返回orderForm，想要返回vo再请求别的接口。" +
            "传递good类型的list。jsonExample[{},{}]")
//    提交给mq处理，前端轮询另一个接口获取状态
    public Result<String> generateOrderForm(@RequestBody List<Goods> list,
        @RequestParam(required = false,name = "promotionId")Integer promotionId) {
        //每次generate成功一次，redis内的秒杀商品数量就减一
        System.out.println("generateOrderForm : "+list+" promotionId = "+promotionId);
        Result<String> result = new Result<>();
        if (list == null || list.size() == 0) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("请选择商品");
            return result;
        }
        HttpSession session = request.getSession();
        String id = session.getId();
        System.out.println("generate method get sessionId = "+id);
//        User user = (User)session.getAttribute("user");
        User user = (User) sessionUtil.getSession(Property.userSessionPrefix+id,User.class);
        if (user == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("请登录");
            return result;
        }
        String orderFormId = orderFormService.generateOrderForm(list,user,promotionId);
        result.setData(orderFormId);
        result.setCode(HttpStatus.OK.value());
        return result;


    }

    @GetMapping("/oneOrderForm")
    public Result<OrderForm> selectByOrderFormId(String orderFormId) {
        //读取redis数据，防止频繁读取数据库
        System.out.println("id = "+orderFormId);
        Jedis jedis = null;
        Integer flag = null;
        try {
            jedis = jedisUtil.getJedis();
            flag = Integer.valueOf(jedis.get(orderFormId));
            System.out.println("read orderForm status from redis=>"+flag);
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) jedis.close();
        }

        if (flag == null || flag.equals(0)) {
            return new Result<>().code(HttpStatus.ACCEPTED.value()).message("订单正在创建");
        }
        if (flag.equals(1)) {
            OrderForm orderForm = orderFormService.selectByOrderFormNumber(orderFormId);
            return Result.generateSuccessResult(orderForm, "订单创建成功");
        }
        if (flag.equals(2)) {
            return new Result<>("订单创建失败",HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
        return Result.generateSuccessResult(null,null);


    }




    //user用的
    @GetMapping("/myOrderForm")
    @ApiOperation("pageNum,beginTime,endTime均可选参数")
    public Result<PageInfo<OrderFormVO>> selectOneOrderForm(HttpSession session,
            @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
            @RequestParam(required = false,value = "beginTime") Date beginTime,
            @RequestParam(required = false,value = "endTime") Date endTime,
            Integer orderFormStatus) {
        //判断session为不为空，分页
        Result<PageInfo<OrderFormVO>> result = new Result();
        //User u = (User)session.getAttribute("user");
        User u = (User) sessionUtil.getSession(Property.userSessionPrefix+session.getId(),User.class);
        if (u == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("未登录");
            return result;
        }
        PageInfo<OrderForm> list = orderFormService.selectOrderForm(u.getUserId(),beginTime,endTime,pageNum,orderFormStatus);
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
    @ApiOperation("唯一参数orderFormId")
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
    @ApiOperation("唯一参数orderFormId")
    public Result deleteOrderForm(Integer orderFormId) {
        Result result = new Result();
        orderFormService.deleteOrderForm(orderFormId);

        result.setCode(HttpStatus.OK.value());
        result.setMessage("delete success");
        return result;
    }
}
