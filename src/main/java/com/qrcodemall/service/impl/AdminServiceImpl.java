package com.qrcodemall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.PageProperty;
import com.qrcodemall.common.UserException;
import com.qrcodemall.dao.*;
import com.qrcodemall.entity.*;
import com.qrcodemall.service.AdminService;
import com.qrcodemall.util.BeanUtil;
import com.qrcodemall.util.DesUtils;
import com.qrcodemall.util.PageUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @Author: Peony
 * @Date: 2020/7/26 14:08
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @SneakyThrows
    @Override
    public Integer login(String account, String password) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andAdminNameEqualTo(account);

        List<Admin> list = adminMapper.selectByExample(example);
        if (list.get(0).getAdminPassword().equals(DesUtils.encrypt(password))) {
            return 1;
        } else {
            return 0;
        }
    }















}
