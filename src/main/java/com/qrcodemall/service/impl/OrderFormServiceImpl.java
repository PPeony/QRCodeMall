package com.qrcodemall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.PageProperty;
import com.qrcodemall.dao.OrderFormDetailMapper;
import com.qrcodemall.dao.OrderFormMapper;
import com.qrcodemall.entity.OrderForm;
import com.qrcodemall.entity.OrderFormDetail;
import com.qrcodemall.entity.OrderFormDetailExample;
import com.qrcodemall.service.OrderFormService;
import com.qrcodemall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/27 12:03
 */
@Service
public class OrderFormServiceImpl implements OrderFormService {

    @Autowired
    OrderFormMapper orderFormMapper;

    @Autowired
    OrderFormDetailMapper orderFormDetailMapper;

    @Override
    public PageInfo<OrderForm> selectOrderForm(Integer userId, Date beginTime, Date endTime, Integer pageNum) {
        OrderForm orderForm = new OrderForm();
        orderForm.setUserId(userId);
        return PageUtil.generatePageInfoByTime(orderForm,pageNum,beginTime,endTime,orderFormMapper);
    }

    @Override
    public PageInfo<OrderForm> selectAllOrderForms(Date beginTime, Date enTime, Integer pageNum) {
        OrderForm orderForm = new OrderForm();
        orderForm.setIsDeleted(0);
        return PageUtil.generatePageInfoByTime(orderForm,pageNum,beginTime,enTime,orderFormMapper);
    }

    @Override
    public BigDecimal getSalesSituation(Date beginTime, Date endtime) {
        OrderForm orderForm = new OrderForm();
        orderForm.setIsDeleted(0);
        List<OrderForm> list = orderFormMapper.selectByEntityAndTime(orderForm,beginTime,endtime);
        BigDecimal res = new BigDecimal("0.0");
        for (OrderForm form : list) {
            res.add(form.getOrderFormPrice());
        }
        return res;
    }

    @Override
    public Integer updateOrderForm(OrderForm orderForm) {
        orderForm.setGmtModified(new Date());
        return orderFormMapper.updateByPrimaryKeySelective(orderForm);
    }

    @Override
    public Integer insertOrderForm(OrderForm orderForm) {
        orderForm.setGmtModified(new Date());
        orderForm.setIsDeleted(0);
        orderForm.setGmtModified(new Date());
        return orderFormMapper.insertSelective(orderForm);
    }

    @Override
    public Integer deleteOrderForm(Integer orderFormId) {
        OrderForm orderForm = new OrderForm();
        orderForm.setIsDeleted(1);
        orderForm.setOrderFormId(orderFormId);
        orderForm.setGmtModified(new Date());
        return orderFormMapper.updateByPrimaryKeySelective(orderForm);
    }

    @Override
    public PageInfo<OrderFormDetail> selectOrderFormDetails(Integer orderFormId, Integer pageNum) {
        OrderFormDetailExample orderFormDetail = new OrderFormDetailExample();
        OrderFormDetailExample.Criteria criteria = orderFormDetail.createCriteria();
        criteria.andOrderFormIdEqualTo(orderFormId);
        criteria.andIsDeletedEqualTo(0);
        PageHelper.startPage(pageNum, PageProperty.PAGESIZE);
        List<OrderFormDetail> list = orderFormDetailMapper.selectByExample(orderFormDetail);
        return new PageInfo<>(list);
    }

    @Override
    public Integer insertOrderFormDetail(OrderFormDetail orderFormDetail) {
        orderFormDetail.setGmtCreated(new Date());
        orderFormDetail.setGmtModified(new Date());
        return orderFormDetailMapper.insertSelective(orderFormDetail);
    }

    @Override
    public Integer updateOrderFormDetail(OrderFormDetail orderFormDetail) {
        orderFormDetail.setGmtModified(new Date());
        return orderFormDetailMapper.updateByPrimaryKeySelective(orderFormDetail);
    }

    @Override
    public Integer deleteOrderFormDetail(Integer orderFormDetailId) {
        OrderFormDetail orderFormDetail = new OrderFormDetail();
        orderFormDetail.setIsDeleted(1);
        orderFormDetail.setOrderFormDeatilId(orderFormDetailId);
        orderFormDetail.setGmtModified(new Date());
        return orderFormDetailMapper.updateByPrimaryKeySelective(orderFormDetail);
    }


}
