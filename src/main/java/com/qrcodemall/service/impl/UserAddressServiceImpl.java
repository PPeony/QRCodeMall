package com.qrcodemall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.PageProperty;
import com.qrcodemall.dao.UserAddressMapper;
import com.qrcodemall.entity.UserAddress;
import com.qrcodemall.entity.UserAddressExample;
import com.qrcodemall.service.UserAddressService;
import com.qrcodemall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/27 14:18
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    UserAddressMapper userAddressMapper;


    @Override
    public PageInfo<UserAddress> selectAllUserAddress(Integer pageNum) {
        PageHelper.startPage(pageNum, PageProperty.PAGESIZE);
        UserAddressExample userAddress = new UserAddressExample();
        UserAddressExample.Criteria criteria = userAddress.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        List<UserAddress> list = userAddressMapper.selectByExample(userAddress);
        return new PageInfo<>(list);
    }

    @Override
    public List<UserAddress> selectUserAddress(Integer userId) {
        UserAddressExample example = new UserAddressExample();
        UserAddressExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        criteria.andUserIdEqualTo(userId);
        List<UserAddress> list = userAddressMapper.selectByExample(example);
        return list;
    }



    @Override
    public Integer insertUserAddress(UserAddress userAddress) {
        userAddress.setGmtCreated(new Date());
        userAddress.setGmtModified(new Date());
        return userAddressMapper.insertSelective(userAddress);
    }

    @Override
    public Integer updateUserAddress(UserAddress userAddress) {
        if (userAddress.getUserAddressDefault() != null && userAddress.getUserAddressDefault() == 1) {
            UserAddressExample example = new UserAddressExample();
            UserAddressExample.Criteria criteria = example.createCriteria();
            criteria.andIsDeletedEqualTo(0);
            criteria.andUserAddressDefaultEqualTo(1);
            criteria.andUserAddressIdEqualTo(userAddress.getUserAddressId());
            List<UserAddress> list = userAddressMapper.selectByExample(example);
            if (list.size() > 0) {
                //已有默认地址了，不能再修改
                return -1;
            }
        }
        userAddress.setGmtModified(new Date());
        return userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Override
    public Integer deleteUserAddress(Integer userAddressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setGmtModified(new Date());
        userAddress.setUserAddressId(userAddressId);
        userAddress.setIsDeleted(1);
        return userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Override
    public UserAddress selectByPrimaryKey(Integer userAddressId) {
        UserAddressExample example = new UserAddressExample();
        UserAddressExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        criteria.andUserAddressIdEqualTo(userAddressId);
        List<UserAddress> list = userAddressMapper.selectByExample(example);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
}
