package com.qrcodemall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.PageProperty;
import com.qrcodemall.dao.QrcodeMapper;
import com.qrcodemall.dao.UserMapper;
import com.qrcodemall.entity.Qrcode;
import com.qrcodemall.entity.QrcodeExample;
import com.qrcodemall.entity.User;
import com.qrcodemall.entity.UserExample;
import com.qrcodemall.service.QrcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/27 11:58
 */
@Service
public class QrcodeServiceImpl implements QrcodeService {

    @Autowired
    QrcodeMapper qrcodeMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public PageInfo<Qrcode> selectQrcode(String userName, Integer pageNum) {
        //先通过name查id
        UserExample userExample = new UserExample();
        UserExample.Criteria userExampleCriteria = userExample.createCriteria();
        userExampleCriteria.andUserNameEqualTo(userName);
        userExampleCriteria.andIsDeletedEqualTo(0);
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList == null || userList.size() == 0) {
            return null;
        }
        PageHelper.startPage(pageNum, PageProperty.PAGESIZE);
        QrcodeExample example = new QrcodeExample();
        QrcodeExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userList.get(0).getUserId());
        criteria.andIsDeletedEqualTo(0);
        List<Qrcode> qrcodeList = qrcodeMapper.selectByExample(example);
        PageInfo<Qrcode> pageInfo = new PageInfo<>(qrcodeList);
        return pageInfo;
    }

    @Override
    public Integer insertQrcode(Qrcode qrcode) {
        qrcode.setGmtCreated(new Date());
        qrcode.setGmtModified(new Date());
        qrcodeMapper.insertSelective(qrcode);
        return qrcode.getQrcodeId();
    }

    @Override
    public Integer updateQrcode(Qrcode qrcode) {
        qrcode.setGmtModified(new Date());
        return qrcodeMapper.updateByPrimaryKeySelective(qrcode);
    }

    @Override
    public Integer deleteQrcode(Integer qrcodeId) {
        Qrcode qrcode = new Qrcode();
        qrcode.setQrcodeId(qrcodeId);
        qrcode.setIsDeleted(1);
        qrcode.setGmtModified(new Date());
        return qrcodeMapper.updateByPrimaryKeySelective(qrcode);
    }
}
