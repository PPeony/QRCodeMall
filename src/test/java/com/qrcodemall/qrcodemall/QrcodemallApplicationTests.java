package com.qrcodemall.qrcodemall;

import com.qrcodemall.controller.vo.GoodsVO;
import com.qrcodemall.entity.Goods;
import com.qrcodemall.util.BeanUtil;
import com.qrcodemall.util.DesUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class QrcodemallApplicationTests {

    @Test
    void contextLoads() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(new Date()));
    }

}

