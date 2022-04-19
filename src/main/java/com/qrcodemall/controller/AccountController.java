package com.qrcodemall.controller;

import com.qrcodemall.entity.Account;
import com.qrcodemall.entity.User;
import com.qrcodemall.service.AccountService;
import com.qrcodemall.service.UserService;
import com.qrcodemall.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/24 13:23
 */
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;


    @GetMapping("/myAccount")
    @ApiOperation(value = "通过session的userId查询，no param")
    public Result<List<Account>> selectByUserId(HttpSession session) {
        Result<List<Account>> result = new Result<>();
        User u = (User)session.getAttribute("user");
        if (u == null) {
            result.code(HttpStatus.UNAUTHORIZED.value())
                    .message("未登录");
            return result;
        }
        Account account = new Account();
        account.setUserId(u.getUserId());
        List<Account> accounts = accountService.selectAccountBySelective(account);

        result.code(HttpStatus.OK.value())
                .data(accounts)
                .message("success");
        return result;
    }

    @GetMapping("/userName")
    @ApiOperation(value = "admin select by userName")
    public Result<List<Account>> selectByUserName(@RequestParam("userName") String userName) {
        Result<List<Account>> result = new Result<>();
        User user = userService.selectByUserName(userName);
        Account account = new Account();
        account.setUserId(user.getUserId());
        List<Account> accountList = accountService.selectAccountBySelective(account);
        result.code(HttpStatus.OK.value())
                .data(accountList)
                .message("success");
        return result;
    }

    @PostMapping("/insert")
    public Result insert(@RequestParam("goodsTypeName") String goodsTypeName,
                         @RequestParam("goodsTypeQrcodeQuantity") Integer goodsTypeQrcodeQuantity,
                         HttpSession session) {
        Result result = new Result();
        User u = (User) session.getAttribute("user");
        if (u == null) {
            result.code(HttpStatus.UNAUTHORIZED.value())
                    .message("未登录");
            return result;
        }
        Account account = new Account();
        account.setUserId(u.getUserId());
        account.setGoodsTypeQrcodeQuantity(goodsTypeQrcodeQuantity);
        account.setGoodsTypeName(goodsTypeName);
        accountService.insertAccount(account);
        result.code(HttpStatus.CREATED.value())
                .message("success");
        return result;
    }

    @PutMapping("/update")
    @ApiOperation(value = "goodsTypeName和quantity")
    public Result updateAccount(@RequestParam("goodsTypeName") String goodsTypeName,
                                @RequestParam("goodsTypeQrcodeQuantity") Integer goodsTypeQrcodeQuantity,
                                HttpSession session) {
        Result result = new Result();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.code(HttpStatus.UNAUTHORIZED.value())
                    .message("未登录");
            return result;
        }
        Account account = new Account();
        account.setGoodsTypeQrcodeQuantity(goodsTypeQrcodeQuantity);
        account.setUserId(user.getUserId());
        account.setGoodsTypeName(goodsTypeName);
        accountService.updateAccount(account);
        result.code(HttpStatus.OK.value())
                .message("success");
        return result;
    }
}
