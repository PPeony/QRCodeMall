package com.qrcodemall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.Exception.GlobalException;
import com.qrcodemall.common.PageProperty;
import com.qrcodemall.dao.UserMapper;
import com.qrcodemall.entity.User;
import com.qrcodemall.entity.UserExample;
import com.qrcodemall.service.UserService;
import com.qrcodemall.util.DesUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/27 11:55
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Integer signin(String phone, String verifyCode) {
        return phone.equals(verifyCode) ? 1:0;
    }

    @SneakyThrows
    @Override
    public User login(String account, String password) {

        UserExample t = new UserExample();
        UserExample.Criteria criteria = t.createCriteria();
        criteria.andUserNameEqualTo(account);
        criteria.andIsDeletedEqualTo(0);
        List<User> search = userMapper.selectByExample(t);
        if (search != null && search.size() != 0) {
            return DesUtils.encrypt(password).equals(search.get(0).getUserPassword()) ? search.get(0) : null;
        }
        t = new UserExample();
        criteria = t.createCriteria();
        criteria.andUserEmailEqualTo(account);
        criteria.andIsDeletedEqualTo(0);
        search = userMapper.selectByExample(t);
        if (search != null && search.size() != 0) {
            return DesUtils.encrypt(password).equals(search.get(0).getUserPassword()) ? search.get(0) : null;
        }
        t = new UserExample();
        criteria = t.createCriteria();
        criteria.andUserPhoneEqualTo(account);
        criteria.andIsDeletedEqualTo(0);
        search = userMapper.selectByExample(t);
        if (search != null && search.size() != 0) {
            return DesUtils.encrypt(password).equals(search.get(0).getUserPassword()) ? search.get(0) : null;
        }
        return null;
     }

    @Override
    public PageInfo<User> selectUser(User user, Integer pageNum) {
        if (user.getIsDeleted() == null) {
            user.setIsDeleted(0);
        }
        PageHelper.startPage(pageNum, PageProperty.PAGESIZE);
        List<User> userList = userMapper.selectBySelective(user);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @SneakyThrows
    @Override
    public Integer addUser(User user) {
        Integer flag = userIfRepeat(user);
        if (flag < 0) {
            return flag;
        }
        if (user.getUserFatherProxyName() != null) {
            if (user.getUserFatherProxyId() == null) {
                UserExample t = new UserExample();
                UserExample.Criteria criteria = t.createCriteria();
                criteria.andUserNameEqualTo(user.getUserFatherProxyName());
                List<User> list = userMapper.selectByExample(t);
                if (list == null || list.size() == 0) {
                    return -1;
                }
                user.setUserFatherProxyId(list.get(0).getUserId());
            }
            User t = new User();
            t.setUserId(user.getUserFatherProxyId());
            t = userMapper.selectByPrimaryKey(user.getUserFatherProxyId());
            if (t == null) {
                GlobalException.fail("该父级的上一层代理不存在");
            }
            user.setUserGrandfatherProxyId(t.getUserGrandfatherProxyId());
            user.setUserGrandfatherProxyName(t.getUserGrandfatherProxyName());
        }
        //密码加密
        user.setUserPassword(DesUtils.encrypt(user.getUserPassword()));
        //添加用户同时要添加userBill表，这个service在下面
        return userMapper.insertSelective(user);
    }

    private Integer userIfRepeat(User user) {
        //插入修改都需要检验名字，手机号，邮箱重复，拽出来
        User u = new User();
        u.setUserName(user.getUserName());
        List<User> l0 = userMapper.selectBySelective(u);
        if (l0.size() > 0) {//名字不能重复
            return -2;
        }
        u = new User();
        u.setUserPhone(user.getUserPhone());
        l0 = userMapper.selectBySelective(u);
        if (l0.size() > 0) {
            //手机号不能重复
            return -3;
        }
        u = new User();
        u.setUserEmail(user.getUserEmail());
        l0 = userMapper.selectBySelective(u);
        if (l0.size() > 0) {
            //邮箱不能重复
            return -4;
        }
        return 0;
    }

    @SneakyThrows
    @Override
    public Integer updateUser(User user) {
        Integer flag = userIfRepeat(user);
        if (flag < 0) {
            return flag;
        }
        if (user.getUserPassword() != null) {
            user.setUserPassword(DesUtils.encrypt(user.getUserPassword()));
        }
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Integer deleteUser(Integer userId) {
        User user = new User();
        user.setIsDeleted(1);
        user.setUserId(userId);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User selectUser(Integer userId) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        criteria.andUserIdEqualTo(userId);
        List<User> userList = userMapper.selectByExample(example);
        if (userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public List<User> findFirstInvitees(Integer userId) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        criteria.andUserFatherProxyIdEqualTo(userId);
        List<User> list = userMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<User> findSecondInvitees(Integer userId) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        criteria.andUserGrandfatherProxyIdEqualTo(userId);
        List<User> list = userMapper.selectByExample(example);
        return list;
    }

    @Override
    public User selectByUserPhone(String userPhone) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserPhoneEqualTo(userPhone);
        List<User> list = userMapper.selectByExample(example);
        if (list.size() == 0) {
            GlobalException.fail("此手机号不存在");
        }
        return list.get(0);
    }

    @Override
    public User selectByUserName(String userName) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(userName);
        List<User> userList = userMapper.selectByExample(example);
        if (userList.size() == 0) {
            GlobalException.fail("此用户名不存在");
        }
        return userList.get(0);
    }
}
