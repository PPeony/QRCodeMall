package com.qrcodemall.controller;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.controller.vo.AdminLoginVO;
import com.qrcodemall.controller.vo.GoodsVO;
import com.qrcodemall.controller.vo.NoticeVO;
import com.qrcodemall.entity.*;
import com.qrcodemall.service.AdminService;
import com.qrcodemall.util.BeanUtil;
import com.qrcodemall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
//delete直接在url拼id
/**
 * @Author: Peony
 * @Date: 2020/7/23 15:54
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/login")//密码登录
    public Result login(@RequestBody @Valid AdminLoginVO admin,Errors errors) {
        String account = admin.getAccount();
        String password = admin.getPassword();
        System.out.println(account+" *** "+password);
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


    @GetMapping("/logout")//退出登录
    public Result logout(HttpSession session) {
        session.removeAttribute("adminId");
        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

//    @PostMapping("/signin")//手机验证码登录,admin不需要
//    public Result signin(String phone, HttpSession session) {
//        Result result = new Result();
//        result.setCode(HttpStatus.OK.value());
//        result.setMessage("登录成功");
//        return result;
//    }
/**goods**/
    @GetMapping("/goods")
    public Result selectGoods(Goods goods,
    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
    @RequestParam(required = false,value = "beginTime") Date beginTime,
    @RequestParam(required = false,value = "endTime") Date endTime) {
        Result<PageInfo<Goods>> result = new Result();
        System.out.println(goods);
        //全空查询所有
        //分页查询,一页十个，返回
        /*
        List<Goods> list = new LinkedList<>();
        Goods findGoods = new Goods();
        findGoods.setGoodsId(1);
        findGoods.setGoodsName("testGoodsA");
        list.add(findGoods);
         */
        PageInfo<Goods> list = adminService.selectGoods(goods, pageNum, beginTime, endTime);
        result.setCode(HttpStatus.OK.value());
        result.setData(list);
        result.setMessage("成功");
        return result;
    }

    @PostMapping("/addGoods")//添加商品
    public Result insertGoods(@Valid @RequestBody Goods goods, Errors errors) {
        //System.out.println(goods);
        // decimal的json直接写12.6就行，不用引号
        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        //insert
        Integer r = adminService.insertGoods(goods);
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("success");
        return result;
    }

    @PutMapping("/updateGoods")//所有的update都是根据id来的
    public Result updateGoods(@RequestBody Goods goods) {
        Result result = new Result();
        //update
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @DeleteMapping("/deleteGoods")
    public Result deleteGoods(Integer goodsId) {
        //delete
        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("delete success");
        return result;
    }
/**notice**/
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

    @PostMapping("/addNotice")
    public Result insertNotice(@Valid @RequestBody Notice notice, Errors errors) {

        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        //insert
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("success");
        return result;
    }

    @PutMapping("/updateNotice")//所有的update都是根据id来的
    public Result updateNotice(@RequestBody Notice notice) {
        Result result = new Result();
        //update
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @DeleteMapping("/deleteNotice")
    public Result deleteNotice(Integer noticeId) {
        //delete
        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("delete success");
        return result;
    }
/**orderForm**/
//下面这些都在orderFormController
//    @GetMapping("/orderForm")
//    public Result selectOrderForm(
//    @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
//    @RequestParam(required = false,value = "beginTime") Date beginTime,
//    @RequestParam(required = false,value = "endTime") Date endTime) {
//        Result<List<OrderForm>> result = new Result<>();
//        List<OrderForm> list = new LinkedList<>();
//        //查询分页
//        OrderForm findOrderForm = new OrderForm();
//        findOrderForm.setOrderFormId(111);
//        findOrderForm.setOrderFormPayTime(new Date());
//
//        list.add(findOrderForm);
//        result.setData(list);
//        result.setCode(HttpStatus.OK.value());
//        result.setMessage("成功");
//        return result;
//    }

//    //订单详情
//    @GetMapping("/orderForm/{orderFormId}")
//    public Result selectOrderDetails(@PathVariable Integer orderFormId,
//                                     @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum) {
//        //orderDetail表照着id查
//        Result<List<OrderFormDetail>> result = new Result<>();
//        List<OrderFormDetail> list = new LinkedList<>();
//        result.setCode(HttpStatus.OK.value());
//        result.setMessage("success");
//        result.setData(list);
//        return result;
//    }
//
//    @PostMapping("/addOrderForm")
//    public Result insertOrderForm(@RequestBody @Valid OrderForm orderForm,Errors errors) {
//        Result result = new Result();
//        if (errors.hasErrors()) {
//            result.setCode(HttpStatus.BAD_REQUEST.value());
//            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
//            return result;
//        }
//        //存数据库
//        result.setCode(HttpStatus.CREATED.value());
//        result.setMessage("add success");
//        return result;
//
//    }
//
//    @PutMapping("/updateOrderForm")
//    public Result updateOrderForm(@RequestBody OrderForm orderForm) {
//        Result result = new Result();
//        ///
//        result.setCode(HttpStatus.OK.value());
//        result.setMessage("update success");
//        return result;
//    }
//
//    @DeleteMapping("/deleteOrderForm")
//    public Result deleteOrderForm(Integer orderFormId) {
//        Result result = new Result();
//        ///
//        result.setCode(HttpStatus.OK.value());
//        result.setMessage("delete success");
//        return result;
//    }

/**qrcode**/
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

    @PostMapping("/addQRCode")
    public Result insertQrcode(@RequestBody @Valid Qrcode qrcode,Errors errors) {
        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        //存数据库
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("add success");
        return result;
    }

    @PutMapping("/updateQRCode")
    public Result updateQrcode(@RequestBody Qrcode qrcode) {
        Result result = new Result();
        ///
        result.setCode(HttpStatus.OK.value());
        result.setMessage("update success");
        return result;
    }

    @DeleteMapping("/deleteQRCode")
    public Result deleteQrcode(Integer qrcodeId) {
        Result result = new Result();
        ///
        result.setCode(HttpStatus.OK.value());
        result.setMessage("delete success");
        return result;
    }
/**user**/
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
    @PostMapping("/addUser")
    public Result insertUser(@RequestBody @Valid User user,Errors errors) {
        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        //存数据库
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("add success");
        return result;
    }

    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody User user) {
        Result result = new Result();
        ///
        result.setCode(HttpStatus.OK.value());
        result.setMessage("update success");
        return result;
    }

    @DeleteMapping("/deleteUser")
    public Result deleteUser(Integer userId) {
        Result result = new Result();
        ///
        result.setCode(HttpStatus.OK.value());
        result.setMessage("delete success");
        return result;
    }
   /**userBill**/

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



    @PutMapping("/outputMoney")//给用户打钱,money是打多少
    public Result outputMoney(@RequestBody UserBill orign) {
        System.out.println(orign);
        Result result = new Result();
        Integer userId = orign.getUserId();
        BigDecimal money = orign.getUserBillMoney();
        if (userId == null || money == null || money.equals(new BigDecimal("0"))) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("请输入正确的参数");
            return result;
        }
        UserBill userBill = new UserBill();
        userBill.setUserId(userId);
        userBill.setUserBillMoney(new BigDecimal("0"));
        BigDecimal userMoney = userBill.getUserBillMoney();
        userMoney = userMoney.add(money);
        userBill.setUserBillMoney(userMoney);
        System.out.println(userBill);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功");
        return result;
    }

/**销售情况总览**/
    @GetMapping("/sales")
    public Result selectSales(
            @RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
            @RequestParam(required = false,value = "beginTime") Date beginTime,
            @RequestParam(required = false,value = "endTime") Date endTime
    ) {
        Result<BigDecimal> result = new Result();
        //从orderForm里面查询符合日期的，在把和相加
        BigDecimal decimal = new BigDecimal("152.45");
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(decimal);
        return result;
    }

}
