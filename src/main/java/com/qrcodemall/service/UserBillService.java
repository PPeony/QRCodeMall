package com.qrcodemall.service;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.entity.UserBill;

import java.util.Date;

/**
 * @Author: Peony
 * @Date: 2022/3/7 11:40
 */
public interface UserBillService {
    Integer updateUserBill(UserBill userBill);

    Integer insertUserBill(UserBill userBill);

    PageInfo<UserBill> selectUserBill(Date beginTime, Date endTime, Integer pageNum);

    Integer deleteUserBill(Integer userBillId);

    PageInfo<UserBill> selectByUserId(Integer userId,Integer pageNum);
}
