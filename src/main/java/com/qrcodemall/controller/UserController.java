package com.qrcodemall.controller;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.Property;
import com.qrcodemall.controller.vo.UserLoginVO;
import com.qrcodemall.dao.UserAddressMapper;
import com.qrcodemall.dao.UserMapper;
import com.qrcodemall.entity.User;
import com.qrcodemall.entity.UserAddress;
import com.qrcodemall.entity.UserBill;
import com.qrcodemall.service.UserAddressService;
import com.qrcodemall.service.UserBillService;
import com.qrcodemall.service.UserService;
import com.qrcodemall.util.Result;
import com.qrcodemall.util.SendSms;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Peony
 * @Date: 2020/7/24 14:36
 */
@RestController
@RequestMapping("/user")
@Api(tags = "数据库里面密码都为12345678，注意密码长度在8-16之间")
public class UserController {

    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;

    @Autowired
    UserAddressService userAddressService;

    @Autowired
    UserBillService userBillService;

    @PostMapping("/login")//密码登录
    public Result login(@Valid @RequestBody UserLoginVO user,
                        HttpSession session,Errors errors) {
        String account = user.getAccount();
        String password = user.getPassword();
        //System.out.println(account+" *** "+password);
        Result result = new Result();
        //登陆完之后id存入session
        User u = userService.login(account,password);
        if (u != null) {
            result.setCode(HttpStatus.OK.value());
            result.setMessage("success");
            session.setAttribute("user",u);
            session.setMaxInactiveInterval(3600);
            return result;
        }
        result.setCode(HttpStatus.UNAUTHORIZED.value());
        result.setMessage("该用户未注册");
        return result;
    }

    @ApiOperation(value = "验证码登录(已废弃)",notes = "在checkVerifyCode之后，返回true直接跳转到正确页面")
    @PostMapping("/signin")//手机验证码登录,admin不需要
    //废弃
    public Result signin(@RequestBody Map<String,Object> json, HttpSession session) {
        String phone = json.get("phone").toString();
        System.out.println("###:= "+phone);
        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("登录成功");
        //id存入session
        return result;
    }

    @GetMapping("/logout")//退出登录
    public Result logout(HttpSession session) {
        session.removeAttribute("user");
        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @PostMapping("/register")
    public Result register(@RequestBody @Valid User user, Errors errors) {
        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        Integer r = userService.addUser(user);
        //errors.getAllErrors().get(0).getDefaultMessage();
//        if (user.getUserName().equals("zhangsan")) {
//            result.setCode(HttpStatus.BAD_REQUEST.value());
//            result.setMessage("该名字已被注册");
//            return  result;
//        }
//        if (user.getUserEmail().equals("123456@abc.com")) {
//            result.setCode(HttpStatus.BAD_REQUEST.value());
//            result.setMessage("该邮箱已被注册");
//            return  result;
//        }
//        if (user.getUserPhone().equals("123456789")) {
//            result.setCode(HttpStatus.BAD_REQUEST.value());
//            result.setMessage("该手机号已被注册");
//            return result;
//        }
        //正常注册,service
        if (r < 0) {
            result.setCode(HttpStatus.OK.value());
            switch (r) {
                case -1:result.setMessage("没有父级代理名字");break;
                case -2:result.setMessage("名字重复");break;
                case -3:result.setMessage("手机号重复");break;
                case -4:result.setMessage("邮箱重复");break;
            }
            return result;
        }

        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("注册成功");
        return result;
    }

    @PostMapping("/sendVerifyCode")
    @ApiParam(name = "userPhone",value = "发送短信的手机号")
    public Result sendVerifyCode(@RequestBody Map<String,Object> userPhone,HttpSession session) {
        String userPhones = (String)userPhone.get("userPhone");
        Result result = new Result();
        //首先判断是否存在这个手机号
        User user = userService.selectByUserPhone(userPhones);
        if (user == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("该用户不存在");
            return result;
        }

        System.out.println(userPhones);
        String code = SendSms.sms(userPhones);
        System.out.println("code = "+code);
        session.removeAttribute("verifyCode");
        session.setAttribute("verifyCode",code);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("验证码发送成功");
        //result.setData(code);
        return result;
    }

    @PostMapping("/checkVerifyCode")
    @ApiOperation(value = "验证验证码",notes = "两个参数，userPhone和verifyCode")
    public Result checkVerifyCode(@RequestBody Map<String,Object> verifyCode,HttpSession session) {
        String verifyCodes = (String)verifyCode.get("verifyCode");
        String userPhones = (String)verifyCode.get("userPhone");
        Result result = new Result();
        System.out.println(verifyCodes);
        String sessionCode = (String)session.getAttribute("verifyCode");
        System.out.println("sessionCode = "+sessionCode);
        if (sessionCode == null) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("验证码已过期，请重新发送验证码");
            return result;
        }
        if (!sessionCode.equals(verifyCodes)) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("验证码错误");
            return result;
        }
        User u = userService.selectByUserPhone(userPhones);
        session.setAttribute("user",u);
        session.setMaxInactiveInterval(3600);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }
    /**address**/
    @GetMapping("/userAddress/{userAddressId}")
    public Result<UserAddress> selectByAddressId(@PathVariable Integer userAddressId){
        Result<UserAddress> result = new Result<>();
        UserAddress userAddress = userAddressService.selectByPrimaryKey(userAddressId);
        result.setCode(HttpStatus.OK.value());
        result.setData(userAddress);
        result.setMessage("success");
        return result;
    }

    @PostMapping("/addAddress")
    public Result insertUserAddress(@RequestBody @Valid UserAddress userAddress, Errors errors ) {
        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        if (session.getAttribute("user") == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("请重新登录");
            return result;
        }
        User tmp = (User)session.getAttribute("user");
        userAddress.setUserId(tmp.getUserId());
        Integer r = userAddressService.insertUserAddress(userAddress);
        //insert into table
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("添加成功");
        return result;
    }



    @PutMapping("/updateAddress")
    public Result updateUserAddress(@RequestBody UserAddress userAddress) {
        Result result = new Result();
        //User user = (User)session.getAttribute("user");
        //userAddress.setUserId(user.getUserId());
        int r = userAddressService.updateUserAddress(userAddress);
        if (r == -1) {
            //已有默认地址，不能再添加
            result.code(HttpStatus.BAD_REQUEST.value()).
                    message("已有默认地址，不能再添加");
            return result;
        }
        result.setCode(HttpStatus.OK.value());
        result.setMessage("修改成功");
        return result;
    }

    @DeleteMapping("/deleteAddress")
    public Result deleteUserAddress(Integer userAddressId) {
        //url 传参
        System.out.println(userAddressId);
        Result result = new Result();
        userAddressService.deleteUserAddress(userAddressId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("删除成功");
        return result;
    }


    @GetMapping("/myAddress")
    public Result<List<UserAddress>> selectUserAddress(@RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
                                                           HttpSession session) {
        //判断session不为空,根据id看地址
        Result<List<UserAddress>> result = new Result();
        if (session.getAttribute("user") == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("请重新登录");
            return result;
        }
        User user = (User)session.getAttribute("user");

        //不分页
        List<UserAddress> userAddressList = userAddressService.selectUserAddress(user.getUserId());

        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功");
        result.setData(userAddressList);
        return result;
    }

    @GetMapping("/my")//查看自己的所有信息
    public Result<User> selectUser(HttpSession session) {
        //不暴露id
        Result<User> result = new Result();
        if (session.getAttribute("user") == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("请重新登录");
            return result;
        }
        User user = (User)session.getAttribute("user");
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(user);
        return result;
    }
/**my proxy**/
    @ApiOperation(value = "查看自己下级代理")
    @GetMapping("/invitees")
    public Result<List<List<User>>> findInvitees(HttpSession session) {
        Result<List<List<User>>> result = new Result<>();
        if (session == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("failure");
            return result;
        }
        User user = (User)session.getAttribute("user");
        if (user == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("请登录");
            return result;
        }
        List<User> invitees1 = userService.findFirstInvitees(user.getUserId());
        List<User> invitees2 = userService.findSecondInvitees(user.getUserId());
        List<List<User>> res = new ArrayList<>(2);
        res.add(invitees1);
        res.add(invitees2);
        result.setCode(HttpStatus.OK.value());
        result.setData(res);
        result.setMessage("success");
        return result;
    }
/**bill**/
    @GetMapping("/myBill")
    public Result<PageInfo<UserBill>> selectUserBill(@RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
                                 HttpSession session) {
        //判断session不为空，分页
        Result<PageInfo<UserBill>> result = new Result<>();
        if (session.getAttribute("user") == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("请重新登录");
            return result;
        }
        User user = (User)session.getAttribute("user");
        PageInfo<UserBill> pageInfo = userBillService.selectByUserId(user.getUserId(),pageNum);

        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(pageInfo);
        return result;
    }

    @PostMapping("/addBill")
    public Result insertBill(@RequestBody UserBill userBill,HttpSession session) {
        Result result = new Result();
        User u = (User)session.getAttribute("user");
        if (u == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("未登录");
            return result;
        }
        userBill.setUserId(u.getUserId());
        userBillService.insertUserBill(userBill);
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("success");
        return result;
    }

    @PutMapping("/updateBill")
    public Result updateBill(@RequestBody UserBill userBill) {
        Result result = new Result();
        userBillService.updateUserBill(userBill);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @DeleteMapping("/deleteBill")
    public Result deleteBill(Integer userBillId) {
        Result result = new Result();
        userBillService.deleteUserBill(userBillId);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }
    //todo，积分换钱接口，同时要在上面controller增加代理时候更新积分
    //10积分一块钱，一级代理500积分，二级代理200积分
    @PostMapping("/usePoints")
    @ApiOperation(value = "传的是变化多少，注意兑换积分时候传负数,只有一个参数为userPoint")
    public Result usePoints(@RequestParam("userPoint") Integer userPoint,HttpSession session) {
        Result result = new Result();
        System.out.println(userPoint+" ===");
        User u = (User) session.getAttribute("user");
        if (u == null) {
            return result.code(HttpStatus.UNAUTHORIZED.value()).message("未登录");
        }
        Integer r = userService.updatePoints(u.getUserId(),userPoint);
        if (r < 0) {
            return result.code(HttpStatus.BAD_REQUEST.value()).message("没有足够积分");
        }
        UserBill userBill = new UserBill();
        userBill.setUserId(u.getUserId());
        userBill.setUserBillDirection(0);
        userBill.setUserBillRemark(Property.remark);
        userBill.setUserBillMoney(new BigDecimal(r / Property.times));
        userBillService.insertUserBill(userBill);
        return result.code(HttpStatus.CREATED.value()).message("success");
    }
}
