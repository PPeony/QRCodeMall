package com.qrcodemall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.PageProperty;
import com.qrcodemall.dao.GoodsMapper;
import com.qrcodemall.entity.Goods;
import com.qrcodemall.entity.GoodsExample;
import com.qrcodemall.service.GoodsService;
import com.qrcodemall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/27 12:01
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public PageInfo<Goods> selectAllGoods(Integer pageNum) {
        PageHelper.startPage(pageNum, PageProperty.PAGESIZE);
        GoodsExample example = new GoodsExample();
        GoodsExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        List<Goods> list = goodsMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public Goods selectGoods(Integer goodsId) {
        GoodsExample example = new GoodsExample();
        GoodsExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        criteria.andGoodsIdEqualTo(goodsId);
        List<Goods> list = goodsMapper.selectByExample(example);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override//cookie操作，建议在controller解决这个业务
    public Integer insertToShoppingCart(Goods goods, Integer userId) {
        return null;
    }

    @Override
    public List<Goods> selectShoppingCart(Cookie[] cookies) {
        List<Goods> list = new ArrayList<>();
        for (Cookie cookie : cookies) {
            Goods goods = selectGoods(Integer.valueOf(cookie.getValue()));
            list.add(goods);
        }
        return list;
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
        return PageUtil.generatePageInfoByTime(goods, pageNum, beginTime, endTime, goodsMapper);
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
        goods.setGmtModified(new Date());
        return goodsMapper.updateByPrimaryKeySelective(goods);
    }
}
