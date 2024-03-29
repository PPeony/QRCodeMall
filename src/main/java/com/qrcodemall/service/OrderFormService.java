package com.qrcodemall.service;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.entity.Goods;
import com.qrcodemall.entity.OrderForm;
import com.qrcodemall.entity.OrderFormDetail;
import com.qrcodemall.entity.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/6 13:17
 */
public interface OrderFormService {



    PageInfo<OrderForm> selectOrderForm(Integer userId, Date beginTime, Date enTime, Integer pageNum,
                                        Integer orderFormStatus);

    PageInfo<OrderForm> selectAllOrderForms(Date beginTime, Date enTime, Integer pageNum);

    Integer updateOrderForm(OrderForm orderForm);

    Integer insertOrderForm(OrderForm orderForm);

    Integer deleteOrderForm(Integer orderFormId);

    BigDecimal getSalesSituation(Date beginTime,Date endtime);

    OrderForm selectByOrderFormNumber(String orderFormNumber);

    String generateOrderForm(List<Goods> list, User user,Integer promotionStatus);

    Integer buyingSuccessfully(String orderFormNumber);

    List<OrderFormDetail> selectOrderFormDetailWithoutPage(Integer orderFormId);

    PageInfo<OrderFormDetail> selectOrderFormDetails(Integer orderFormId,Integer pageNum);

    Integer insertOrderFormDetail(OrderFormDetail orderFormDetail);

    Integer updateOrderFormDetail(OrderFormDetail orderFormDetail);

    Integer deleteOrderFormDetail(Integer orderFormDetailId);

}
