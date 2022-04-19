package com.qrcodemall.service;

import com.qrcodemall.entity.Account;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/4 12:55
 */
public interface AccountService {
    List<Account> selectAccountBySelective(Account account);

    Integer updateAccount0(Account account);

    Integer updateAccount(Account account);

    Integer insertAccount(Account account);
}
