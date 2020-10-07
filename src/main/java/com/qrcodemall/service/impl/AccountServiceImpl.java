package com.qrcodemall.service.impl;

import com.qrcodemall.common.Exception.GlobalException;
import com.qrcodemall.dao.AccountMapper;
import com.qrcodemall.entity.Account;
import com.qrcodemall.entity.AccountExample;
import com.qrcodemall.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/8/24 12:59
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;

    //根据userId和goodsTypeName查询
    @Override
    public List<Account> selectAccountBySelective(Account account) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        if (account.getAccountId() != null) {
            criteria.andAccountIdEqualTo(account.getAccountId());
        }
        if (account.getUserId() != null) {
            criteria.andUserIdEqualTo(account.getUserId());
        }
        if (account.getGoodsTypeName() != null) {
            criteria.andGoodsTypeNameEqualTo(account.getGoodsTypeName());
        }
        return accountMapper.selectByExample(example);
    }

    //增加传正数，减少传负数
    @Override
    public Integer updateAccount(Account account) {
        List<Account> list = selectAccountBySelective(account);
        Account account0 = list.get(0);
        int r = account0.getGoodsTypeQrcodeQuantity() +
                account.getGoodsTypeQrcodeQuantity();
        if (r < 0) {
            GlobalException.fail("没有足够的二维码");
        }
        account.setGoodsTypeQrcodeQuantity(r);
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(account.getUserId());
        criteria.andGoodsTypeNameEqualTo(account.getGoodsTypeName());
        accountMapper.updateByExampleSelective(account,example);
        return 1;
    }

    public Integer updateAccount0(Account account) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsTypeNameEqualTo(account.getGoodsTypeName());
        criteria.andUserIdEqualTo(account.getUserId());
        accountMapper.updateByExampleSelective(account,example);
        return 1;
    }

    //根据userId，type，quantity添加
    @Override
    public Integer insertAccount(Account account) {
        List<Account> list0 = selectAccountBySelective(account);
        if (list0.size() == 0) {
            //没有买过对应的type
            return accountMapper.insertSelective(account);
        }
        //买过就update
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(account.getUserId());
        criteria.andGoodsTypeNameEqualTo(account.getGoodsTypeName());
        account.setGoodsTypeQrcodeQuantity(list0.get(0).getGoodsTypeQrcodeQuantity()+account.getGoodsTypeQrcodeQuantity());
        accountMapper.updateByExampleSelective(account,example);
        return 1;
    }
}
