package com.qrcodemall.service.impl;

import com.qrcodemall.dao.PropertyMapper;
import com.qrcodemall.entity.Property;
import com.qrcodemall.entity.PropertyExample;
import com.qrcodemall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/8/14 15:01
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    PropertyMapper propertyMapper;


    @Override
    public List<Property> selectPropertyByType(String goodsTypeName) {
        PropertyExample example = new PropertyExample();
        PropertyExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsTypeNameEqualTo(goodsTypeName);
        criteria.andIsDeletedEqualTo(0);
        List<Property> list = propertyMapper.selectByExample(example);
        return list;
    }

    @Override
    public Integer insertProperty(List<Property> properties) {
        for (int i = 0; i < properties.size(); i++) {
            Integer r = check(properties.get(i));
            //第i个参数重复
            if (r < 0) {
                return i * (-1);
            }
        }
        for (int i = 0; i < properties.size(); i++) {
            insert(properties.get(i));
        }
        return 1;
    }
    private Integer check(Property property) {
        //校验key字段不能重复,同一个的type不能有相同的key
        String propertyKey = property.getPropertyKey();
        String goodsType = property.getGoodsTypeName();
        PropertyExample example = new PropertyExample();
        PropertyExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        criteria.andGoodsTypeNameEqualTo(goodsType);
        criteria.andPropertyKeyEqualTo(propertyKey);
        List<Property> list = propertyMapper.selectByExample(example);
        if (list.size() != 0) {
            //字段重复
            return -1;
        }
        return 1;
    }
    private Integer insert(Property property) {
        property.setGmtCreated(new Date());
        property.setGmtModified(new Date());
        propertyMapper.insertSelective(property);
        return 1;
    }


    @Override
    public Integer updateProperty(List<Property> properties) {
        for (Property property : properties) {
            Integer r = check(property);
            if (r <= 0) {
                return r;
            }
        }
        for (Property property : properties) {
            property.setGmtModified(new Date());
            propertyMapper.updateByPrimaryKeySelective(property);
        }
        return 1;
    }

    @Override
    public Integer deleteProperty(Integer propertyId) {
        Property property = new Property();
        property.setGmtModified(new Date());
        property.setIsDeleted(1);
        property.setPropertyId(propertyId);
        propertyMapper.updateByPrimaryKeySelective(property);
        return 1;
    }
}
