package com.qrcodemall.service;

import com.qrcodemall.entity.Goods;
import com.qrcodemall.entity.GoodsType;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/26 13:18
 */
public interface GoodsService {

    List<Goods> selectAllGoods(Integer pageNum);

    List<GoodsType> selectAllGoodsType(Integer pageNum);

    Goods selectGoods(Integer goodsId);

    Integer insertToShoppingCart(Goods goods,Integer userId);

    List<Goods> selectShoppingCart(Integer userId, Integer pageNum);


}
