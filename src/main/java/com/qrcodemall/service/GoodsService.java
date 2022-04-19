package com.qrcodemall.service;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.controller.vo.PromotionGoodsVO;
import com.qrcodemall.entity.Goods;
import com.qrcodemall.entity.PromotionGoods;
import com.qrcodemall.entity.GoodsType;

import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/6 13:18
 */
public interface GoodsService {

    PageInfo<Goods> selectAllGoods(Integer pageNum);

    Goods selectGoods(Integer goodsId);

    Integer insertToShoppingCart(Goods goods,Integer userId);

    List<Goods> selectShoppingCart(Cookie[] cookies);

    Integer deleteGoods(Integer goodsId);

    Integer updateGoods(Goods goods);

    Integer insertGoods(Goods goods);

    PageInfo<Goods> selectGoods(Goods goods, Integer pageNum, Date beginTime, Date endTime);

    List<PromotionGoodsVO> selectPromotionGoods();

    Integer createPromotion(PromotionGoods promotionGoods);

    Integer cancelPromotion(Integer goodsId);

    PromotionGoodsVO getPromotionGoodsByPK(Integer promotionId);


    Integer scheduleStartPromotion(Integer promotionId,Integer goodsId);

    Integer scheduleStopPromotion(Integer promotionId,Integer goodsId);
}
