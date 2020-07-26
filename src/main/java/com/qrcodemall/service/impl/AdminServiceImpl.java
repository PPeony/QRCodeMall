package com.qrcodemall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.dao.*;
import com.qrcodemall.entity.*;
import com.qrcodemall.service.AdminService;
import com.qrcodemall.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/26 14:08
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    GoodsTypeMapper goodsTypeMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public Integer login(String account, String password) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andAdminNameEqualTo(account);

        List<Admin> list = adminMapper.selectByExample(example);
        if (list.get(0).getAdminPassword().equals(password)) {
            return 1;
        } else {
            return 0;
        }
    }



    @Override
    public PageInfo<Goods> selectGoods(Goods goods, Integer pageNum, Date beginTime, Date endTime) {
//        System.out.println("pageNum = "+pageNum);
//        System.out.println("beginTime = "+beginTime);
//        System.out.println("endTime = "+endTime);
//        PageHelper.startPage(pageNum,3);
//        if (goods.getIsDeleted() == null) {
//            goods.setIsDeleted(0);
//        }
//        //按照修改时间查询
//        List<Goods> list = goodsMapper.selectByEntityAndTime(goods,beginTime,endTime);
//
//        PageInfo<Goods> pageInfo = new PageInfo<>(list);
//        //System.out.println(pageInfo);
//        return pageInfo;
        return generatePageInfoByTime(goods,pageNum,beginTime,endTime,goodsMapper);
    }

    @Override
    public Integer insertGoods(Goods goods) {
        goods.setGmtCreated(new Date());
        goods.setGmtModified(new Date());
        return goodsMapper.insertSelective(goods);
    }

    @Override
    public Integer updateGoods(Goods goods) {
        goods.setGmtModified(new Date());
        return goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public Integer deleteGoods(Integer goodsId) {
        Goods goods = new Goods();
        goods.setIsDeleted(1);
        goods.setGoodsId(goodsId);
        return goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public PageInfo<Notice> selectNotice(Notice notice, Integer pageNum, Date beginTime, Date endTime) {
        return generatePageInfoByTime(notice,pageNum,beginTime,endTime,noticeMapper);
    }
    private static <T,K>PageInfo<T> generatePageInfoByTime(T obj,Integer pageNum,Date beginTime,Date endTime,K mapper) {
        //多个查询都长这样，拽出来写
        PageHelper.startPage(pageNum,3);
        Class clz = obj.getClass();
        List<T> list = new LinkedList<>();
        try {
            Field field = clz.getDeclaredField("isDeleted");
            field.setAccessible(true);
            if (field.get(obj) == null) {
                field.set(obj,0);
            }
            Class mapperClz = mapper.getClass();
            Method method = mapperClz.getDeclaredMethod("selectByEntityAndTime",clz,Date.class,Date.class);

//            System.out.println(obj);
            list = (List<T>) method.invoke(mapper,obj,beginTime,endTime);

        } catch (Exception e) {
            e.printStackTrace();
        }
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Integer insertNotice(Notice notice) {
        return null;
    }

    @Override
    public Integer updateNotice(Notice notice) {
        return null;
    }

    @Override
    public Integer deleteNotice(Integer noticeId) {
        return null;
    }

    @Override
    public PageInfo<Qrcode> selectQrcode(String userName, Integer pageNum) {
        return null;
    }

    @Override
    public Integer insertQrcode(Qrcode qrcode) {
        return null;
    }

    @Override
    public Integer updateQrcode(Qrcode qrcode) {
        return null;
    }

    @Override
    public Integer deleteQrcode(Integer qrcodeId) {
        return null;
    }

    @Override
    public PageInfo<User> selectUser(User user, Integer pageNum) {
        return null;
    }

    @Override
    public Integer addUser(User user) {
        return null;
    }

    @Override
    public Integer updateUser(User user) {
        return null;
    }

    @Override
    public Integer deleteUser(Integer userId) {
        return null;
    }

    @Override
    public PageInfo<UserBill> selectUserBill(Date beginTime, Date endTime, Integer pageNum) {
        return null;
    }

    @Override
    public Integer updateUserBill(UserBill userBill) {
        return null;
    }

    @Override
    public PageInfo<BigDecimal> selectSales(Date beginTime, Date endTime, Integer pageNum) {
        return null;
    }
}
