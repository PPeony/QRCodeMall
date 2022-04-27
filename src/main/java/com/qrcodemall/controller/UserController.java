package com.qrcodemall.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.gson.internal.$Gson$Preconditions;
import com.qrcodemall.common.Property;
import com.qrcodemall.controller.vo.UserLoginVO;
import com.qrcodemall.controller.vo.VerifyCodeVO;
import com.qrcodemall.dao.UserAddressMapper;
import com.qrcodemall.dao.UserMapper;
import com.qrcodemall.entity.User;
import com.qrcodemall.entity.UserAddress;
import com.qrcodemall.entity.UserBill;
import com.qrcodemall.service.UserAddressService;
import com.qrcodemall.service.UserBillService;
import com.qrcodemall.service.UserService;
import com.qrcodemall.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author: Peony
 * @Date: 2022/3/4 14:36
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

    @Autowired
    JedisUtil jedisUtil;

    @Autowired
    SessionUtil sessionUtil;
    /*
    @PostMapping("/login")//密码登录，查三次
    @ApiOperation("account和password。account可以是手机号，用户名，邮箱")
    public Result login(@Valid @RequestBody UserLoginVO user,
                        HttpSession session,Errors errors) {
        long start = System.currentTimeMillis();
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
            long end = System.currentTimeMillis();
            System.out.println("login : "+(end - start));
            return result;
        }
        result.setCode(HttpStatus.UNAUTHORIZED.value());
        result.setMessage("该用户未注册");
        long end = System.currentTimeMillis();
        System.out.println("login : "+(end - start));
        return result;
    }

    @PostMapping("/login2")//密码登录,多线程版，感觉没有直接修改sql语句快
    @ApiOperation("account和password。account可以是手机号，用户名，邮箱")
    public Result login2(@Valid @RequestBody UserLoginVO user,
                        HttpSession session,Errors errors) {
        long start = System.currentTimeMillis();
        String account = user.getAccount();
        String password = user.getPassword();
        //System.out.println(account+" *** "+password);
        Result result = new Result();
        //登陆完之后id存入session
        User u = userService.login2(account,password);
        if (u != null) {
            result.setCode(HttpStatus.OK.value());
            result.setMessage("success");
            session.setAttribute("user",u);
            session.setMaxInactiveInterval(3600);
            long end = System.currentTimeMillis();
            System.out.println("login2 : "+(end - start));
            return result;
        }
        result.setCode(HttpStatus.UNAUTHORIZED.value());
        result.setMessage("该用户未注册");
        long end = System.currentTimeMillis();
        System.out.println("login2 : "+(end - start));
        return result;
    }

     */


    @PostMapping("/login")//密码登录，修改sql语句
    @ApiOperation("account和password。account可以是手机号，用户名，邮箱")
    public Result login3(@Valid @RequestBody UserLoginVO user, Errors errors, HttpServletRequest request,
                         HttpServletResponse response) {
        //long start = System.currentTimeMillis();
        String account = user.getAccount();
        String password = user.getPassword();
        //System.out.println(account+" *** "+password);
        Result result = new Result();
        //登陆完之后id存入session
        User u = userService.login3(account,password);
        if (u != null) {
            result.setCode(HttpStatus.OK.value());
            result.setMessage("success");
            String id = session.getId();
            Jedis jedis = null;
            try {
                jedis = jedisUtil.getJedis();
                jedis.setex(Property.userSessionPrefix+id, Property.sessionExpireTime, JSON.toJSONString(u));
            } catch (Exception e) {
                throw e;
            } finally {
                if (jedis != null) jedis.close();
            }
            //session.setAttribute("user",u);
            //session.setMaxInactiveInterval(3600);
            //跨域处理SameSite

            System.out.println("userLoginSessionId = "+id);

            HttpCookie cookie = CookieUtils.generateSetCookie3(request, "JSESSIONID", id, Duration.ofHours(3));
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            //
            //long end = System.currentTimeMillis();
            //System.out.println("login3 : "+(end - start));
            return result;
        }
        result.setCode(HttpStatus.UNAUTHORIZED.value());
        result.setMessage("该用户未注册");
        //long end = System.currentTimeMillis();
        //System.out.println("login3 : "+(end - start));
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
    @ApiOperation("no param")
    public Result logout(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
        //session.removeAttribute("user");
        String id = session.getId();
        System.out.println("sessionLogOutId = "+id);
        Jedis jedis = null;
        try {
            jedis = jedisUtil.getJedis();
            jedis.del(Property.userSessionPrefix+id);
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) jedis.close();
        }
        HttpCookie cookie = CookieUtils.generateSetCookie3(request, "JSESSIONID", id, Duration.ZERO);
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }

    @PostMapping("/register")
    @ApiOperation("userName,userPhone,userEmail,userPassword必传。userFatherProxyName可选。")
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

    @PutMapping("/modify")
    @ApiOperation("修改个人信息")
    public Result updateMyProfile(@RequestBody User user,HttpSession session) {
        Result result = new Result();
        //User u = (User)session.getAttribute("user");
        User u = sessionUtil.getSession(Property.userSessionPrefix+session.getId(),User.class);
        if (u == null) {
            return result.code(HttpStatus.UNAUTHORIZED.value()).message("未登录");
        }
        String check = checkUpdateForm(user);
        if (check != null) {
            return result.code(HttpStatus.BAD_REQUEST.value()).message(check);
        }
        user.setUserId(u.getUserId());
        Integer r = userService.updateUser(user);
        if (r < 0) {
            return Result.badUserParams(r);
        }
        return result.code(HttpStatus.CREATED.value()).message("success");

    }

    private String checkUpdateForm(User user) {
        boolean res = true;
        if (user.getUserPhone() != null) {
            res = (res && Pattern.matches("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$",user.getUserPhone()));
            if (res == false) {
                return "phone number illegal";
            }
        }
        if (user.getUserEmail() != null) {
            res = (res && Pattern.matches("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?",user.getUserEmail()));
            if (res == false) {
                return "email illegal";
            }
        }
        if (user.getUserPassword() != null) {
            if (user.getUserPassword().length() < 8 || user.getUserPassword().length() > 16) {
                return "password length not illegal";
            }
        }
        return null;

    }

    @PostMapping("/sendVerifyCode")
    @ApiOperation("userPhone发送短信的手机号")
    @ApiParam(name = "userPhone",value = "发送短信的手机号")
    public Result sendVerifyCode(@RequestBody Map<String,Object> userPhone,
                                 HttpSession session,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        String userPhones = (String)userPhone.get("userPhone");
        //System.out.println("**"+userPhones+"**");
        Result result = new Result();
        //System.out.println(userPhones);
        String code = SendSms.sms(userPhones);
        //System.out.println("code = "+code);
        String id = session.getId();
        //session.removeAttribute("verifyCode");
        sessionUtil.removeSession(id+"-verifyCode");
        System.out.println("test get id: "+id+" verifyCode = "+code);
        VerifyCodeVO vcv = new VerifyCodeVO(userPhones,code);
        //session.setAttribute("verifyCode",vcv);
        sessionUtil.setSession(id+"-verifyCode",vcv);
        //sameSite
        HttpCookie cookie = CookieUtils.generateSetCookie3(request, "JSESSIONID", id, Duration.ofHours(3));
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
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
        if (verifyCodes == null || userPhones == null) {
            return result.code(HttpStatus.BAD_REQUEST.value()).message("手机号或者验证码不能为空");
        }

        //VerifyCodeVO vcv = (VerifyCodeVO)session.getAttribute("verifyCode");
        VerifyCodeVO vcv = (VerifyCodeVO)sessionUtil.getSession(session.getId()+"-verifyCode",VerifyCodeVO.class);
        System.out.println("jsessionId = "+session.getId()+"check = "+verifyCodes+" vcv = "+vcv);
//        session.removeAttribute("verifyCode");
        sessionUtil.removeSession(session.getId()+"-verifyCode");
        //System.out.println("sessionCode = "+sessionCode);
        if (vcv == null) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("验证码已过期，请重新发送验证码");
            return result;
        }
        if (!vcv.getUserPhone().equals(userPhones)) {
            return result.code(HttpStatus.BAD_REQUEST.value()).message("手机号与验证码不匹配");
        }
        if (!vcv.getVerifyCode().equals(verifyCodes)) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage("验证码错误");
            return result;
        }
//        User u = userService.selectByUserPhone(userPhones);
//        session.setAttribute("user",u);
//        session.setMaxInactiveInterval(3600);
        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        return result;
    }
    /**address**/
    @GetMapping("/userAddress/{userAddressId}")
    @ApiOperation("唯一参数userAddressId")
    public Result<UserAddress> selectByAddressId(@PathVariable Integer userAddressId){
        Result<UserAddress> result = new Result<>();
        UserAddress userAddress = userAddressService.selectByPrimaryKey(userAddressId);
        result.setCode(HttpStatus.OK.value());
        result.setData(userAddress);
        result.setMessage("success");
        return result;
    }

    @PostMapping("/addAddress")
    @ApiOperation("receiveName,receivePhone,userAddressDetail,userAddressDistrict,userAddressCity,userAddressProvince" +
            "必须传")
    public Result insertUserAddress(@RequestBody @Valid UserAddress userAddress, Errors errors ) {
        Result result = new Result();
        if (errors.hasErrors()) {
            result.setCode(HttpStatus.BAD_REQUEST.value());
            result.setMessage(errors.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        //User tmp = (User)session.getAttribute("user");
        User tmp = (User)sessionUtil.getSession(Property.userSessionPrefix+session.getId(),User.class);
        if (tmp == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("请重新登录");
            return result;
        }
        userAddress.setUserId(tmp.getUserId());
        Integer r = userAddressService.insertUserAddress(userAddress);
        //insert into table
        result.setCode(HttpStatus.CREATED.value());
        result.setMessage("添加成功");
        return result;
    }



    @PutMapping("/updateAddress")
    @ApiOperation("不要传userId，所有修改都是按照主键id来的，所以userAddressId一定要有")
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
    @ApiOperation("唯一参数userAddressId")
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
    @ApiOperation("no param.operate by session")
    public Result<List<UserAddress>> selectUserAddress(@RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
                                                           HttpSession session) {
        //判断session不为空,根据id看地址
        Result<List<UserAddress>> result = new Result();
//        User user = (User)session.getAttribute("user");
        User user = (User)sessionUtil.getSession(Property.userSessionPrefix+session.getId(),User.class);
        if (session.getAttribute("user") == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("请重新登录");
            return result;
        }


        //不分页
        List<UserAddress> userAddressList = userAddressService.selectUserAddress(user.getUserId());

        result.setCode(HttpStatus.OK.value());
        result.setMessage("成功");
        result.setData(userAddressList);
        return result;
    }

    @GetMapping("/my")//查看自己的所有信息
    @ApiOperation("no param.operate by session")
    public Result<User> selectUser(HttpSession session) {
        //不暴露id
        Result<User> result = new Result();
        //User user = (User)session.getAttribute("user");
        System.out.println("my sessionId="+session.getId());
        User user = (User) sessionUtil.getSession(Property.userSessionPrefix+session.getId(),User.class);
        if (user == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("请重新登录");
            return result;
        }

        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(user);
        return result;
    }
/**my proxy**/
    @ApiOperation(value = "查看自己下级代理.no param.operate by session")
    @GetMapping("/invitees")
    public Result<List<List<User>>> findInvitees(HttpSession session) {
        Result<List<List<User>>> result = new Result<>();
        //User user = (User)session.getAttribute("user");
        User user = (User) sessionUtil.getSession(Property.userSessionPrefix+session.getId(),User.class);
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
    @ApiOperation("pageNum页号")
    public Result<PageInfo<UserBill>> selectUserBill(@RequestParam(required = false,defaultValue = "1",value = "pageNum")Integer pageNum,
                                 HttpSession session) {
        //判断session不为空，分页
        Result<PageInfo<UserBill>> result = new Result<>();
        User user = (User) sessionUtil.getSession(Property.userSessionPrefix+session.getId(),User.class);
        if (user == null) {
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            result.setMessage("请重新登录");
            return result;
        }
        //User user = (User)session.getAttribute("user");
        PageInfo<UserBill> pageInfo = userBillService.selectByUserId(user.getUserId(),pageNum);

        result.setCode(HttpStatus.OK.value());
        result.setMessage("success");
        result.setData(pageInfo);
        return result;
    }

    @PostMapping("/addBill")
    public Result insertBill(@RequestBody UserBill userBill,HttpSession session) {
        Result result = new Result();
        //User u = (User)session.getAttribute("user");
        User u = (User) sessionUtil.getSession(Property.userSessionPrefix+session.getId(),User.class);
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
    //积分换钱接口，同时要在上面controller增加代理时候更新积分
    //10积分一块钱，一级代理500积分，二级代理200积分
    @PostMapping("/usePoints")
    @ApiOperation(value = "传的是变化多少，注意兑换积分时候传负数,只有一个参数为userPoint")
    public Result usePoints(@RequestParam("userPoint") Integer userPoint,HttpSession session) {
        Result result = new Result();
        System.out.println(userPoint+" ===");
        //User u = (User) session.getAttribute("user");
        User u = (User) sessionUtil.getSession(Property.userSessionPrefix+session.getId(),User.class);
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
        userBill.setUserBillMoney(new BigDecimal(userPoint*(-1) / Property.times));
        userBillService.insertUserBill(userBill);
        return result.code(HttpStatus.CREATED.value()).message("success");
    }
}
