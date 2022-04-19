package com.qrcodemall.service.impl;

import com.qrcodemall.dao.PropertyValueMapper;
import com.qrcodemall.entity.PropertyValue;
import com.qrcodemall.entity.PropertyValueExample;
import com.qrcodemall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/14 15:11
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    PropertyValueMapper propertyValueMapper;

    @Override
    public List<PropertyValue> selectByQRCodeId(Integer QRCodeId) {
        PropertyValueExample example = new PropertyValueExample();
        PropertyValueExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        criteria.andQrcodeIdEqualTo(QRCodeId);
        List<PropertyValue> list = propertyValueMapper.selectByExampleWithBLOBs(example);
        return list;
    }

    @Override
    public Integer insertPropertyValue(List<PropertyValue> propertyValues) {
        //检查qrcodeId是否存在，已经存在不能再添加,不传id
        PropertyValueExample example = new PropertyValueExample();
        PropertyValueExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        criteria.andQrcodeIdEqualTo(propertyValues.get(0).getQrcodeId());
        List<PropertyValue> list = propertyValueMapper.selectByExample(example);
        if (list.size() > 0) {
            //已存在不能再添加
            return -1;
        }
        for (PropertyValue propertyValue : propertyValues) {
            insert(propertyValue);
        }
        return 1;
    }

    private Integer insert(PropertyValue propertyValue) {
        propertyValue.setGmtCreated(new Date());
        propertyValue.setGmtModified(new Date());
        return propertyValueMapper.insertSelective(propertyValue);
    }


    @Override
    public Integer updatePropertyValue(List<PropertyValue> propertyValues) {
        for (PropertyValue propertyValue : propertyValues) {
            propertyValue.setGmtModified(new Date());
            propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
        }
        return 1;
    }

    @Override
    public Integer deletePropertyValue(Integer propertyValueId) {
        PropertyValue propertyValue = new PropertyValue();
        propertyValue.setIsDeleted(1);
        propertyValue.setGmtModified(new Date());
        propertyValue.setPropertyValueId(propertyValueId);
        return propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }
}
