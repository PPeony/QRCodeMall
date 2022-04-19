package com.qrcodemall.service;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.entity.GoodsType;
import com.qrcodemall.util.PageUtil;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/7 13:42
 */
public interface GoodsTypeService {
    List<GoodsType> selectAllGoodsType();

    Integer insertGoodsType(GoodsType goodsType);

    Integer updateGoodsType(GoodsType goodsType);

    Integer deleteGoodsType(Integer goodsTypeId);

    GoodsType selectByGoodsTypeName(String goodsTypeName);
}
