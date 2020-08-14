package com.qrcodemall.qrcodemall;

import com.qrcodemall.controller.vo.GoodsVO;
import com.qrcodemall.entity.Goods;
import com.qrcodemall.entity.User;
import com.qrcodemall.util.BeanUtil;
import com.qrcodemall.util.DesUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class QrcodemallApplicationTests {

    @Test
    void contextLoads() {
        User o = new User();
        o.setUserName("name");
        try {
            Field field = o.getClass().getDeclaredField("userName");
            field.setAccessible(true);
            String v = (String)field.get(o);
            System.out.println(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

