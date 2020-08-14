package com.qrcodemall.service;

import com.qrcodemall.entity.Property;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/8/14 14:53
 */
public interface PropertyService {

    List<Property> selectPropertyByType(String goodsTypeName);

    Integer insertProperty(List<Property> properties);

    Integer updateProperty(List<Property> properties);

    Integer deleteProperty(Integer propertyId);
}
