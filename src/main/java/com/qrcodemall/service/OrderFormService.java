package com.qrcodemall.service;

import com.qrcodemall.entity.OrderForm;
import com.qrcodemall.entity.OrderFormDetail;

import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/26 13:17
 */
public interface OrderFormService {

    Integer updateOrderForm(OrderForm orderForm);

    List<OrderForm> selectOrderForm(Integer userId, Date beginTime, Date enTime, Integer pageNum);

    List<OrderForm> selectAllOrderForms(Date beginTime, Date enTime, Integer pageNum);

    List<OrderFormDetail> selectOrderFormDetails(Integer orderFormId);

    Integer insertOrderForm(OrderForm orderForm);

    Integer deleteOrderForm(Integer orderFormId);

}
