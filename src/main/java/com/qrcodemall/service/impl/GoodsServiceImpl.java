package com.qrcodemall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.aop.annotation.RedisLock;
import com.qrcodemall.common.PageProperty;
import com.qrcodemall.common.Property;
import com.qrcodemall.controller.vo.OrderFormVO;
import com.qrcodemall.controller.vo.PromotionGoodsVO;
import com.qrcodemall.dao.GoodsMapper;
import com.qrcodemall.dao.GoodsTypeMapper;
import com.qrcodemall.dao.PromotionMapper;
import com.qrcodemall.dao.ScheduleTaskMapper;
import com.qrcodemall.entity.Goods;
import com.qrcodemall.entity.GoodsExample;
import com.qrcodemall.entity.OrderForm;
import com.qrcodemall.entity.PromotionGoods;
import com.qrcodemall.service.GoodsService;
import com.qrcodemall.util.BeanUtil;
import com.qrcodemall.util.DateUtil;
import com.qrcodemall.util.JedisUtil;
import com.qrcodemall.util.PageUtil;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
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

    @Autowired
    GoodsTypeMapper goodsTypeMapper;
    @Autowired
    PromotionMapper promotionMapper;
    @Autowired
    JedisUtil jedisUtil;
    @Autowired
    ScheduleTaskMapper scheduleTaskMapper;

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
        System.out.println("pageNum = "+pageNum);
        System.out.println("beginTime = "+beginTime);
        System.out.println("endTime = "+endTime);
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

    @Override
    public List<PromotionGoodsVO> selectPromotionGoods() {
        PromotionGoods promotionGoods = new PromotionGoods();
        promotionGoods.setIsDeleted(0);
        List<PromotionGoods> promotionGoodsList = promotionMapper.select(promotionGoods);
        System.out.println("promotion->"+promotionGoodsList);
        List<PromotionGoodsVO> promotionGoodsVOList = new ArrayList<>();
        for (PromotionGoods goods:promotionGoodsList) {
            Goods g = selectGoods(goods.getGoodsId());
            PromotionGoodsVO vo = new PromotionGoodsVO();
            BeanUtil.copyProperties(g,vo,"isDeleted");
            vo.setPromotionId(goods.getPromotionId());
            vo.setPromotionStartTime(DateUtil.dateToStr(goods.getPromotionStartTime()));
            vo.setPromotionDuration(goods.getPromotionDuration());
            vo.setIsDeleted(goods.getIsDeleted());
            vo.setPromotionCount(goods.getPromotionCount());
            vo.setPromotionValue(goods.getPromotionValue());
            promotionGoodsVOList.add(vo);
        }
        return promotionGoodsVOList;
    }

    @Override
    public Integer createPromotion(PromotionGoods promotionGoods) {
        promotionGoods.setIsDeleted(0);
        Integer r = promotionMapper.insert(promotionGoods);
        Goods goods = new Goods();
        goods.setGoodsStatus(1);
        goods.setGoodsId(promotionGoods.getGoodsId());
        goodsMapper.updateByPrimaryKeySelective(goods);
        return r;
    }

    @Override
    public Integer cancelPromotion(Integer goodsId) {
        promotionMapper.delete(goodsId);
        Goods goods = new Goods();
        goods.setGoodsId(goodsId);
        goods.setGoodsStatus(0);
        goodsMapper.updateByPrimaryKeySelective(goods);
        return 1;
    }

    @Override
    public PromotionGoodsVO getPromotionGoodsByPK(Integer promotionId) {
        PromotionGoods promotionGoods = new PromotionGoods();
        promotionGoods.setIsDeleted(0);
        promotionGoods.setPromotionId(promotionId);
        List<PromotionGoods> select = promotionMapper.select(promotionGoods);
        if (select.size() == 0) {
            return null;
        }
        Goods goods = goodsMapper.selectByPrimaryKey(select.get(0).getGoodsId());
        Goods g = selectGoods(goods.getGoodsId());
        PromotionGoodsVO vo = new PromotionGoodsVO();
        BeanUtil.copyProperties(g,vo,"isDeleted");
        vo.setPromotionId(select.get(0).getPromotionId());
        vo.setPromotionStartTime(DateUtil.dateToStr(select.get(0).getPromotionStartTime()));
        vo.setPromotionDuration(select.get(0).getPromotionDuration());
        vo.setIsDeleted(select.get(0).getIsDeleted());
        vo.setPromotionCount(select.get(0).getPromotionCount());
        vo.setPromotionValue(select.get(0).getPromotionValue());
        return vo;
    }

    @RedisLock(key = "promotion_schedule")
    @Override
    public Integer scheduleStartPromotion(Integer promotionId,Integer goodsId) {
        //设置redis
        Jedis jedis = null;
        try {
            jedis = jedisUtil.getJedis();
            PromotionGoodsVO promotionGoodsVO = getPromotionGoodsByPK(promotionId);
            jedis.set(String.valueOf(promotionId), JSON.toJSONString(promotionGoodsVO));
            System.out.println("start key1 " + promotionId+" key2 "+Property.promotionRedisKeyPrefix+promotionId);
            jedis.set(Property.promotionRedisKeyPrefix+promotionId,String.valueOf(promotionGoodsVO.getPromotionCount()));
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) jedis.close();
        }
        return 1;
    }

    @RedisLock(key = "promotion_schedule")
    @Override
    public Integer scheduleStopPromotion(Integer promotionId,Integer goodsId) {
        System.out.println("end promotionId = "+promotionId+" goodsId = "+goodsId);
        //设置数据库促销状态,设置redis
        cancelPromotion(goodsId);
        Jedis jedis = null;
        try {
            jedis = jedisUtil.getJedis();
            jedis.del(String.valueOf(promotionId));
            jedis.del(Property.promotionRedisKeyPrefix+promotionId);
            scheduleTaskMapper.delete(promotionId);
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) jedis.close();
        }
        return 1;
    }
}
