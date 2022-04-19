package com.qrcodemall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.PageProperty;
import com.qrcodemall.dao.UserBillMapper;
import com.qrcodemall.entity.UserBill;
import com.qrcodemall.entity.UserBillExample;
import com.qrcodemall.service.UserBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/7 11:51
 */
@Service
public class UserBillServiceImpl implements UserBillService {

    @Autowired
    UserBillMapper userBillMapper;

    @Override
    public PageInfo<UserBill> selectUserBill(Date beginTime, Date endTime, Integer pageNum) {
        PageHelper.startPage(pageNum, PageProperty.PAGESIZE);
        UserBillExample example = new UserBillExample();
        UserBillExample.Criteria criteria = example.createCriteria();
        if (beginTime != null) {
            criteria.andGmtCreatedGreaterThanOrEqualTo(beginTime);
        }
        if (endTime != null) {
            criteria.andGmtModifiedLessThanOrEqualTo(endTime);
        }
        criteria.andIsDeletedEqualTo(0);
        List<UserBill> list = userBillMapper.selectByExample(example);
        PageInfo<UserBill> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Integer insertUserBill(UserBill userBill) {
        userBill.setGmtCreated(new Date());
        userBill.setGmtModified(new Date());
        return userBillMapper.insertSelective(userBill);
    }

    @Override
    public Integer updateUserBill(UserBill userBill) {
        userBill.setGmtModified(new Date());
        return userBillMapper.updateByPrimaryKeySelective(userBill);
    }

    @Override
    public Integer deleteUserBill(Integer userBillId) {
        UserBill u = new UserBill();
        u.setUserBillId(userBillId);
        u.setGmtModified(new Date());
        u.setIsDeleted(1);
        return userBillMapper.updateByPrimaryKeySelective(u);
    }

    @Override
    public PageInfo<UserBill> selectByUserId(Integer userId,Integer pageNum) {
        UserBillExample example = new UserBillExample();
        UserBillExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andIsDeletedEqualTo(0);
        PageHelper.startPage(pageNum,PageProperty.PAGESIZE);
        List<UserBill> list = userBillMapper.selectByExample(example);
        return new PageInfo<>(list);
    }
}
