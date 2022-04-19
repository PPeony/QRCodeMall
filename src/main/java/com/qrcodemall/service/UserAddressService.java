package com.qrcodemall.service;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.entity.UserAddress;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/7 13:46
 */
public interface UserAddressService {

    PageInfo<UserAddress> selectAllUserAddress(Integer pageNum);

    List<UserAddress> selectUserAddress(Integer userId);

    Integer insertUserAddress(UserAddress userAddress);

    Integer updateUserAddress(UserAddress userAddress);

    Integer deleteUserAddress(Integer userAddressId);

    UserAddress selectByPrimaryKey(Integer userAddressId);
}
