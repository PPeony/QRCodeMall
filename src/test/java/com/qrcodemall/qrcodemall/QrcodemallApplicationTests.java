package com.qrcodemall.qrcodemall;

import com.qrcodemall.controller.vo.GoodsVO;
import com.qrcodemall.entity.Goods;
import com.qrcodemall.entity.User;
import com.qrcodemall.util.BeanUtil;
import com.qrcodemall.util.DesUtils;
import com.qrcodemall.util.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

@SpringBootTest
class QrcodemallApplicationTests {

    @Test
    void contextLoads() {
        try {
            System.out.println(DesUtils.encrypt("12345678"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void change(testUser tu) {
        testUser nt = new testUser();
        nt.name = "123";
    }
    Result<Integer> testResult() {
        Result<Integer> result = new Result<>();
        return result.code(200).message("success").data("123&");
    }

}
class testUser{
    public String name;
}
