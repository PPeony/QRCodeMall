package com.qrcodemall.dao;

import com.qrcodemall.entity.PromotionGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/19 14:07
 */
public interface PromotionMapper {
    Integer insert(PromotionGoods promotionGoods);
    Integer delete(@Param("goodsId") Integer goodsId);//删除
    List<PromotionGoods> select(PromotionGoods promotionGoods);
    Integer selectLatestPromotionByGoodsId(Integer goodsId);
}
