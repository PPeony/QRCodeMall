package com.qrcodemall.service;

import com.qrcodemall.entity.User;
import com.qrcodemall.entity.UserAddress;
import com.qrcodemall.entity.UserBill;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/26 13:17
 */
public interface UserService {

    Integer login(String account, String password);

    Integer signin(String phone,String verifyCode);

    Integer register(User user);

    List<UserAddress> selectUserAddress(Integer userId, Integer pageNum);

    Integer insertUserAddress(UserAddress userAddress);

    Integer updateUserAddress(UserAddress userAddress);

    Integer deleteUserAddress(Integer userAddressId);

    User selectUser(Integer userId);

    UserBill selectUserBill(Integer userId, Integer pageNum);

    //理论上用不上这个功能
    Integer deleteUserBill(Integer userId);


}
