package com.qrcodemall.controller;

import com.github.pagehelper.PageInfo;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
        System.out.println(account+" *** "+password);
        Result result = new Result();
        //登陆完之后id存入session
        User u = userService.login(account,password);
        if (u != null) {
            result.setCode(HttpStatus.OK.value());
            result.setMessage("success");
            session.setAttribute("user",u);
            return result;
        }
        result.setCode(HttpStatus.UNAUTHORIZED.value());
        result.setMessage("该用户未注册");
        return result;
    }

    @PostMapping("/signin")//手机验证码登录,admin不需要
    //todo
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
    //目前无法对手机号发送验证码,todo
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
    public Result sendVerifyCode(String userPhone,HttpSession session) {
        Result result = new Result();
        System.out.println(userPhone);
        String code = SendSms.sms(userPhone);
        session.removeAttribute("verifyCode");
        session.setAttribute("verifyCode",code);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("验证码发送成功");
        return result;
    }

    @PostMapping("/checkVerifyCode")
    public Result checkVerifyCode(String verifyCode,HttpSession session) {
        Result result = new Result();
        String sessionCode = (String)session.getAttribute("verifyCode");
        if (sessionCode == null) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("验证码已过期，请重新发送验证码");
            return result;
        }
        if (!sessionCode.equals(verifyCode)) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("验证码错误");
            return result;
        }
        result.setCode(HttpStatus.OK.value());
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

        //传参方式有待商榷
        Result result = new Result();
        User user = (User)session.getAttribute("user");
        userAddress.setUserId(user.getUserId());
        userAddressService.updateUserAddress(userAddress);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("修改成功");
        return result;
    }

    @DeleteMapping("/deleteAddress")
    public Result deleteUserAddress(Integer userAddressId) {
        //url 传参
        //System.out.println(userAddressId);
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

}
