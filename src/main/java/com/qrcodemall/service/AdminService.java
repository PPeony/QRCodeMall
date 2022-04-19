package com.qrcodemall.service;

import com.github.pagehelper.PageInfo;
import com.qrcodemall.entity.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/6 13:17
 */

public interface AdminService {

    Integer login(String account, String password);

    //PageInfo<BigDecimal> selectSales(Date beginTime, Date endTime, Integer pageNum);


}
