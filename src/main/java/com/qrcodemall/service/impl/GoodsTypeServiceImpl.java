package com.qrcodemall.service.impl;

import com.qrcodemall.dao.GoodsTypeMapper;
import com.qrcodemall.entity.GoodsType;
import com.qrcodemall.entity.GoodsTypeExample;
import com.qrcodemall.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/27 15:47
 */
@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {
    @Autowired
    GoodsTypeMapper goodsTypeMapper;

    @Override
    public List<GoodsType> selectAllGoodsType() {
        GoodsTypeExample example = new GoodsTypeExample();
        GoodsTypeExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        return goodsTypeMapper.selectByExample(example);
    }

    @Override
    public Integer insertGoodsType(GoodsType goodsType) {
        return goodsTypeMapper.insertSelective(goodsType);
    }

    @Override
    public Integer updateGoodsType(GoodsType goodsType) {
        return goodsTypeMapper.updateByPrimaryKeySelective(goodsType);
    }

    @Override
    public Integer deleteGoodsType(Integer goodsTypeId) {
        GoodsType goodsType = new GoodsType();
        goodsType.setIsDeleted(1);
        goodsType.setGoodsTypeId(goodsTypeId);
        return goodsTypeMapper.updateByPrimaryKeySelective(goodsType);
    }
}
