package com.qrcodemall.util;

import java.util.UUID;

/**
 * @Author: Peony
 * @Date: 2020/8/16 13:41
 */
public class OrderFormNumberGenerator {
    public static String generate() {
        String id= UUID.randomUUID().toString().replaceAll("-", "");
        return id;
    }
}
