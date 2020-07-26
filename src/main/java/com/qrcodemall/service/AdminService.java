package com.qrcodemall.service;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.entity.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/26 13:17
 */

public interface AdminService {

    Integer login(String account, String password);

    PageInfo<Goods> selectGoods(Goods goods, Integer pageNum, Date beginTime, Date endTime);

    Integer insertGoods(Goods goods);

    Integer updateGoods(Goods goods);

    Integer deleteGoods(Integer goodsId);

    PageInfo<Notice> selectNotice(Notice notice, Integer pageNum, Date beginTime, Date endTime);

    Integer insertNotice(Notice notice);

    Integer updateNotice(Notice notice);

    Integer deleteNotice(Integer noticeId);

    PageInfo<Qrcode> selectQrcode(String userName, Integer pageNum);

    Integer insertQrcode(Qrcode qrcode);

    Integer updateQrcode(Qrcode qrcode);

    Integer deleteQrcode(Integer qrcodeId);

    PageInfo<User> selectUser(User user, Integer pageNum);

    Integer addUser(User user);

    Integer updateUser(User user);

    Integer deleteUser(Integer userId);

    PageInfo<UserBill> selectUserBill(Date beginTime, Date endTime, Integer pageNum);

    //直接存入计算之后的，计算过程在controller
    Integer updateUserBill(UserBill userBill);

    PageInfo<BigDecimal> selectSales(Date beginTime, Date endTime, Integer pageNum);


}
