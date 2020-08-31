package com.qrcodemall.service.serviceHelper;

import com.qrcodemall.dao.UserMapper;
import com.qrcodemall.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @Author: Peony
 * @Date: 2020/8/30 9:03
 */
//不知道建在哪，随便开了一个包
//多线程查询，不如直接修改底层语句
@Component
public class UserSelector {

    @Autowired
    ExecutorService executorService;

    @Autowired
    UserMapper userMapper;

    public User[] searchUser(User user) {
        User[] users = new User[3];
        User f = null;
        Future<User> user1 = null,user2 = null,user3 = null;
        //long start = System.currentTimeMillis();
        //User user1 = null,user2 = null,user3 = null;
        if (user.getUserName() != null) {
            f = new User();
            f.setUserName(user.getUserName());
            user1 = searchUserSelective(f);
        }
        if (user.getUserPhone() != null) {
            f = new User();
            f.setUserPhone(user.getUserPhone());
            user2 = searchUserSelective(f);
        }

        if (user.getUserEmail() != null) {
            f = new User();
            f.setUserEmail(user.getUserEmail());
            user3 = searchUserSelective(f);
        }
        //FutureTask类
        //System.out.println(user1.getClass());
        try {
            users[0] = user1.get();
            users[1] = user2.get();
            users[2] = user3.get();
            //long end = System.currentTimeMillis();
            //System.out.println("inner selector = "+(end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //System.out.println(Arrays.toString(users));
            return users;
        }
    }

    private Future<User> searchUserSelective(User user) {

        Future<User> fal  = executorService.submit(new Callable<User>() {
            @Override
            public User call() {
                List<User> userList = userMapper.selectBySelective(user);
                if (userList.size() == 0) {
                    return null;
                }
                return userList.get(0);
            }
        });
        return fal;
    }

}
