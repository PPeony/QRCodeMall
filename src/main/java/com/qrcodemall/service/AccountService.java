package com.qrcodemall.service;

import com.qrcodemall.entity.Account;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/8/24 12:55
 */
public interface AccountService {
    List<Account> selectAccountBySelective(Account account);


    Integer updateAccount(Account account);

    Integer insertAccount(Account account);
}
