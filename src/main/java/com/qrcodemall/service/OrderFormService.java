package com.qrcodemall.service;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.entity.OrderForm;
import com.qrcodemall.entity.OrderFormDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/26 13:17
 */
public interface OrderFormService {



    PageInfo<OrderForm> selectOrderForm(Integer userId, Date beginTime, Date enTime, Integer pageNum);

    PageInfo<OrderForm> selectAllOrderForms(Date beginTime, Date enTime, Integer pageNum);

    Integer updateOrderForm(OrderForm orderForm);

    Integer insertOrderForm(OrderForm orderForm);

    Integer deleteOrderForm(Integer orderFormId);

    BigDecimal getSalesSituation(Date beginTime,Date endtime);


    PageInfo<OrderFormDetail> selectOrderFormDetails(Integer orderFormId,Integer pageNum);

    Integer insertOrderFormDetail(OrderFormDetail orderFormDetail);

    Integer updateOrderFormDetail(OrderFormDetail orderFormDetail);

    Integer deleteOrderFormDetail(Integer orderFormDetailId);

}
