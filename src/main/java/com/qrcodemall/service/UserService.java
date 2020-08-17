package com.qrcodemall.service;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.entity.User;
import com.qrcodemall.entity.UserAddress;
import com.qrcodemall.entity.UserBill;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/26 13:17
 */
public interface UserService {

    User login(String account, String password);

    Integer signin(String phone,String verifyCode);

    Integer deleteUser(Integer userId);

    Integer updateUser(User user);

    Integer addUser(User user);//登录，或者管理员手动添加

    PageInfo<User> selectUser(User user, Integer pageNum);

    ////

    User selectUser(Integer userId);

    List<User> findFirstInvitees(Integer userId);

    List<User> findSecondInvitees(Integer userId);

    User selectByUserPhone(String userPhone);


}
