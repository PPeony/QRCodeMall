package com.qrcodemall.controller;

import com.qrcodemall.controller.vo.GoodsVO;
import com.qrcodemall.controller.vo.NoticeVO;
import com.qrcodemall.entity.*;
import com.qrcodemall.util.BeanUtil;
import com.qrcodemall.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/23 15:54
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/login")//密码登录
    public Result login(String account, String password) {
        Result result = new Result();
        //
        if (account == null || password == null) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("输入字段不能为空");
            return result;
        }
        if (account.equals("123@qq.com")) {//email
            if (password.equals("123456")) {
                result.setCode(HttpStatus.OK.value());
                result.setMessage("登录成功");
                return result;
            } else {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("用户名或者密码不正确");
                return result;
            }
        } else if (account.equals("18845678900")) {//phone
            if (password.equals("123")) {
                result.setCode(HttpStatus.OK.value());
                result.setMessage("登录成功");
                return result;
            } else {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("用户名或者密码不正确");
                return result;
            }
        } else if (account.equals("zhangsan")) {//name
            if (password.equals("123456789")) {
                result.setCode(HttpStatus.OK.value());
                result.setMessage("登录成功");
                return result;
            } else {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("用户名或者密码不正确");
                return result;
            }
        }
        result.setCode(HttpStatus.BAD_REQUEST.value());
        result.setMessage("该用户未注册");
        return result;
    }

    @PostMapping("/signin")//手机验证码登录
    public Result signin(String phone, HttpSession session) {
        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("登录成功");
        return result;
    }

//    @GetMapping("/test")
//    public String test(Admin admin) {
//        System.out.println(admin);
//        return "success";
//    }

    @GetMapping("/goods")
    public Result selectGoods(Goods goods,
    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
    @RequestParam(required = false,value = "beginTime") Date beginTime,
    @RequestParam(required = false,value = "endTime") Date endTime) {
        Result<List<Goods>> result = new Result();
        //全空查询所有
        //分页查询,一页十个，返回
        List<Goods> list = new LinkedList<>();
        Goods findGoods = new Goods();
        findGoods.setGoodsId(1);
        findGoods.setGoodsName("testGoodsA");
        list.add(findGoods);
        result.setCode(HttpStatus.OK.value());
        result.setData(list);
        result.setMessage("成功");
        return result;
    }

    @GetMapping("/notice")
    public Result selectNotice(Notice notice,
    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
    @RequestParam(required = false,value = "beginTime") Date beginTime,
    @RequestParam(required = false,value = "endTime") Date endTime) {
        Result<List<Notice>> result = new Result<>();
        List<Notice> list = new LinkedList<>();
        Notice findNotice = new Notice();
        //查询，分页
        findNotice.setNoticeId(123);
        findNotice.setNoticeMessage("testNoticeMessage");
        findNotice.setNoticeTittleName("testNoticeTittleName");
        list.add(findNotice);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功");
        result.setData(list);
        return result;
    }

    @GetMapping("/orderForm")
    public Result selectOrderForm(OrderForm orderForm,
    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
    @RequestParam(required = false,value = "beginTime") Date beginTime,
    @RequestParam(required = false,value = "endTime") Date endTime) {
        Result<List<OrderForm>> result = new Result<>();
        List<OrderForm> list = new LinkedList<>();
        //查询分页
        OrderForm findOrderForm = new OrderForm();
        findOrderForm.setOrderFormId(111);
        findOrderForm.setOrderFormPayTime(new Date());

        list.add(findOrderForm);
        result.setData(list);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功");
        return result;
    }

    @GetMapping("/QRCode")
    public Result selectQRCode(String userName,
    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {
        Result<List<Qrcode>> result = new Result<>();
        if (userName == null || userName.length() == 0) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("请输入用户名");
            return result;
        }

        List<Qrcode> list = new LinkedList<>();
        Qrcode qrcode = new Qrcode();
        //用户名找id
        qrcode.setUserId(123);
        list.add(qrcode);
        result.setCode(HttpStatus.OK.value());
        result.setData(list);
        result.setMessage("成功");
        return result;
    }

    @GetMapping("/user")
    public Result selectUser(User user,
    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {
        Result<List<User>> result = new Result();
        List<User> list = new LinkedList<>();
        User findUser = new User();
        findUser.setIsVip(1);
        findUser.setUserName("zhangsan");
        result.setCode(HttpStatus.OK.value());
        result.setData(list);
        result.setMessage("成功");
        return result;
    }

    @GetMapping("/userBill")
    public Result selectUserBill(@RequestParam(required = false,value = "beginTime") Date beginTime,
    @RequestParam(required = false,value = "endTime") Date endTime,
    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {

        Result<List<UserBill>> result = new Result();
        List<UserBill> list = new LinkedList<>();

        UserBill userBill = new UserBill();
        list.add(userBill);
        userBill.setUserId(1);
        //一定要用string构造
        userBill.setUserBillMoney(new BigDecimal("1.2"));
        userBill.setUserBillRemark("testRemark");
        result.setCode(HttpStatus.OK.value());
        result.setData(list);
        result.setMessage("成功");
        return result;
    }
}
