package com.qrcodemall.service.impl;

import com.qrcodemall.dao.*;
import com.qrcodemall.entity.*;
import com.qrcodemall.service.AdminService;
import com.qrcodemall.util.DesUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/6 14:08
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
        if (list.size() == 0) {
            return -1;
        }
        if (list.get(0).getAdminPassword().equals(DesUtils.encrypt(password))) {
            return 1;
        } else {
            return 0;
        }
    }















}
