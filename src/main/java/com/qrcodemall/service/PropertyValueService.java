package com.qrcodemall.service;

import com.qrcodemall.entity.PropertyValue;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/14 14:56
 */
public interface PropertyValueService {

    List<PropertyValue> selectByQRCodeId(Integer QRCodeId);

    Integer insertPropertyValue(List<PropertyValue> propertyValue);

    Integer updatePropertyValue(List<PropertyValue> propertyValues);

    Integer deletePropertyValue(Integer propertyValueId);


}
