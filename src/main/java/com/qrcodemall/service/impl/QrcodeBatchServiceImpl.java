package com.qrcodemall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.Exception.GlobalException;
import com.qrcodemall.common.PageProperty;
import com.qrcodemall.common.Property;
import com.qrcodemall.dao.AccountMapper;
import com.qrcodemall.dao.QrcodeBatchMapper;
import com.qrcodemall.entity.Account;
import com.qrcodemall.entity.AccountExample;
import com.qrcodemall.entity.QrcodeBatch;
import com.qrcodemall.entity.QrcodeBatchExample;
import com.qrcodemall.service.AccountService;
import com.qrcodemall.service.QrcodeBatchService;
import com.qrcodemall.util.BeanUtil;
import com.qrcodemall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/6 13:40
 */
@Service
public class QrcodeBatchServiceImpl implements QrcodeBatchService {

    @Autowired
    QrcodeBatchMapper qrcodeBatchMapper;

    @Autowired
    AccountMapper accountMapper;


    @Override
    public PageInfo<QrcodeBatch> selectQrcodeBatch(QrcodeBatch qrcodeBatch,int pageNum) {
        QrcodeBatchExample example = new QrcodeBatchExample();
        QrcodeBatchExample.Criteria criteria = example.createCriteria();
        if (qrcodeBatch.getQrcodeBatchId() != null) {
            criteria.andQrcodeBatchIdEqualTo(qrcodeBatch.getQrcodeBatchId());
        }
        if (qrcodeBatch.getUserId() != null) {
            criteria.andUserIdEqualTo(qrcodeBatch.getUserId());
        }
        if (qrcodeBatch.getIsMail() != null) {
            criteria.andIsMailEqualTo(qrcodeBatch.getIsMail());
        }
        if (qrcodeBatch.getQrcodeId() != null) {
            criteria.andQrcodeIdEqualTo(qrcodeBatch.getQrcodeId());
        }
        PageHelper.startPage(pageNum,PageProperty.PAGESIZE);
        PageInfo<QrcodeBatch> batches = new PageInfo<>(qrcodeBatchMapper.selectByExample(example));
        return batches;
    }

    @Override
    public Integer insertList(List<QrcodeBatch> qrcodeBatches,Integer userId) {
        for (QrcodeBatch qrcodeBatch:qrcodeBatches) {
            qrcodeBatch.setIsMail(0);
            qrcodeBatch.setGmtCreated(new Date());
            qrcodeBatch.setGmtModified(new Date());
            qrcodeBatch.setUserId(userId);
        }
        qrcodeBatchMapper.insertList(qrcodeBatches);
        return 1;
    }



    //主键更新
    @Override
    public Integer updateQrcodeBatch(QrcodeBatch qrcodeBatch) {
        qrcodeBatch.setGmtModified(new Date());
        if (qrcodeBatch.getQrcodeNumber() != null) {
            if (qrcodeBatch.getUserId() != null) {
                Account account = new Account();
                QrcodeBatch batch = selectQrcodeBatch(qrcodeBatch, 1).getList().get(0);
                Integer r0 = batch.getQrcodeNumber() + qrcodeBatch.getQrcodeNumber();
                //原有订单不够数量或者account不够数量
                if (r0 < 0) {
                    return r0;
                }
                Integer r = checkIfEnough(qrcodeBatch,account);
                if (r < 0) {
                    return r;
                }
                updateAccount(r,account);
                qrcodeBatch.setQrcodeNumber(r0);
            } else {
                GlobalException.fail("userId未知");
            }
        }
        qrcodeBatchMapper.updateByPrimaryKeySelective(qrcodeBatch);
        return 1;
    }

    private Integer checkIfEnough(QrcodeBatch qrcodeBatch,Account account) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsTypeNameEqualTo(qrcodeBatch.getGoodsTypeName());
        criteria.andUserIdEqualTo(qrcodeBatch.getUserId());
        List<Account> list = accountMapper.selectByExample(example);
        BeanUtil.copyProperties(list.get(0),account);
        return account.getGoodsTypeQrcodeQuantity()-qrcodeBatch.getQrcodeNumber();
    }

    private Integer updateAccount(Integer newQuantity,Account account) {
        account.setGoodsTypeQrcodeQuantity(newQuantity);
        accountMapper.updateByPrimaryKeySelective(account);
        return 1;
    }

    @Override
    public Integer insertQrcodeBatch(QrcodeBatch qrcodeBatch) {
        qrcodeBatch.setGmtModified(new Date());
        qrcodeBatch.setGmtCreated(new Date());
        qrcodeBatchMapper.insertSelective(qrcodeBatch);
        return qrcodeBatch.getQrcodeBatchId();
    }

    @Override
    public Integer deleteQrcodeBatch(QrcodeBatch qrcodeBatch) {
        //QrcodeBatch batch = selectQrcodeBatch(qrcodeBatch, 1).getList().get(0);
        if (qrcodeBatch.getIsMail() == 0) {
            Account account = selectAccount(qrcodeBatch);
            updateAccount(account.getGoodsTypeQrcodeQuantity()+qrcodeBatch.getQrcodeNumber(),account);
        }
        qrcodeBatchMapper.deleteByPrimaryKey(qrcodeBatch.getQrcodeBatchId());
        return 1;
    }

    private Account selectAccount(QrcodeBatch qrcodeBatch) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(qrcodeBatch.getUserId());
        criteria.andGoodsTypeNameEqualTo(qrcodeBatch.getGoodsTypeName());
        return accountMapper.selectByExample(example).get(0);
    }

    @Override
    public boolean checkIfMailed(QrcodeBatch qrcodeBatch) {
        QrcodeBatch origin = qrcodeBatchMapper.selectByPrimaryKey(qrcodeBatch.getQrcodeBatchId());
        if (origin.getIsMail() == 1) {
            return true;
        } else {
            return false;
        }
    }
}
