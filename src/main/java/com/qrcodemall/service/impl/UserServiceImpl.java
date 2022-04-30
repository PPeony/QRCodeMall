package com.qrcodemall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.Exception.GlobalException;
import com.qrcodemall.common.PageProperty;
import com.qrcodemall.common.Property;
import com.qrcodemall.dao.UserMapper;
import com.qrcodemall.entity.User;
import com.qrcodemall.entity.UserExample;
import com.qrcodemall.service.UserService;
import com.qrcodemall.service.serviceHelper.UserSelector;
import com.qrcodemall.util.DesUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/7 11:55
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserSelector userSelector;

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

     @SneakyThrows
     public User login2(String account,String password) {
        User u = new User();
        u.setUserName(account);
        u.setUserPhone(account);
        u.setUserEmail(account);
        User[] users = userSelector.searchUser(u);
        for (User t : users) {
            if (t != null) {
                if (DesUtils.encrypt(password).equals(t.getUserPassword())) {
                    return t;
                }
            }
        }
        return null;
     }
     @SneakyThrows
    @Override
    public User login3(String account, String password) {
        UserExample example = new UserExample();
        example.or().andUserNameEqualTo(account);
        example.or().andUserEmailEqualTo(account);
        example.or().andUserPhoneEqualTo(account);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0) {
            return null;
        }
        return DesUtils.encrypt(password).equals(users.get(0).getUserPassword()) ? users.get(0) : null;
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
        if (user.getUserFatherProxyName() != null && !"".equals(user.getUserFatherProxyName())) {
            user.setIsVip(1);
            //查询父级代理
            if (user.getUserFatherProxyId() == null) {
                UserExample t = new UserExample();
                UserExample.Criteria criteria = t.createCriteria();
                criteria.andUserNameEqualTo(user.getUserFatherProxyName());
                List<User> list = userMapper.selectByExample(t);
                if (list.size() == 0) {
                    return -1;
                }
                Integer fatherId = list.get(0).getUserId();
                user.setUserFatherProxyId(fatherId);
            }
            //查询grandFather
            User t = userMapper.selectByPrimaryKey(user.getUserFatherProxyId());
            if (t != null) {
                user.setUserGrandfatherProxyId(t.getUserFatherProxyId());
                user.setUserGrandfatherProxyName(t.getUserFatherProxyName());
            }

        }
        //密码加密
        user.setUserPassword(DesUtils.encrypt(user.getUserPassword()));

        return userMapper.insertSelective(user);
    }

    @Override
    public Integer addPoint(User user0) {
        User user = selectUser(user0.getUserId());
        //每个用户的积分只加一次，不是买一次东西加一次
        if (user.getIsVip() == 0) {
            return 0;
        }
        Integer fatherId = user.getUserFatherProxyId();
        Integer grandFatherId = null;
        if (fatherId == null) {
            return 1;
        }
        updatePoints(fatherId,Property.firstPoint);
        User father = userMapper.selectByPrimaryKey(fatherId);
        if (father.getUserFatherProxyId() == null) {
            return 1;
        }
        grandFatherId = father.getUserFatherProxyId();
        updatePoints(grandFatherId, Property.secondPoint);
        return 1;
    }

    public Integer updatePoints(Integer userId, Integer points) {
        User tu = new User();
        User user = userMapper.selectByPrimaryKey(userId);
        Integer newPoints = user.getUserPoint() + points;
        if (newPoints < 0) {
            return newPoints;
        }
        tu.setUserPoint(newPoints);
        tu.setUserId(userId);
        userMapper.updateByPrimaryKeySelective(tu);
        return newPoints;
    }

    @Override
    public Integer checkUpdatePoints(Integer userId, Integer changeNum) {
        User user = selectUser(userId);
        Integer newPoints = user.getUserPoint()+changeNum;
        return newPoints;
    }

    private Integer userIfRepeat(User user) {
        //插入修改都需要检验名字，手机号，邮箱重复，拽出来
        User u = new User();
        List<User> l0;
        if (user.getUserName() != null) {
            u.setUserName(user.getUserName());
            l0 = userMapper.selectBySelective(u);
            if (l0.size() > 0) {//名字不能重复
                return -2;
            }
        }
        if (user.getUserPhone() != null) {
            u = new User();
            u.setUserPhone(user.getUserPhone());
            l0 = userMapper.selectBySelective(u);
            if (l0.size() > 0) {
                //手机号不能重复
                return -3;
            }
        }
        if (user.getUserEmail() != null) {
            u = new User();
            u.setUserEmail(user.getUserEmail());
            l0 = userMapper.selectBySelective(u);
            if (l0.size() > 0) {
                //邮箱不能重复
                return -4;
            }
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
        //更新代理的名字
        if (user.getUserName() != null) {
            updateProxyName(user.getUserId(),user.getUserName());
        }
        return userMapper.updateByPrimaryKeySelective(user);
    }

    private Integer updateProxyName(Integer originId,String newName) {
        UserExample example = new UserExample();
        example.or().andUserFatherProxyIdEqualTo(originId);
        User newUser = new User();
        newUser.setUserGrandfatherProxyName(newName);
        userMapper.updateByExampleSelective(newUser,example);
        example.or().andUserGrandfatherProxyIdEqualTo(originId);
        newUser = new User();
        newUser.setUserGrandfatherProxyName(newName);
        userMapper.updateByExampleSelective(newUser,example);
        return 1;
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
            return null;
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
