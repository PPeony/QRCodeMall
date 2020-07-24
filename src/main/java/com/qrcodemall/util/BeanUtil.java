package com.qrcodemall.util;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;

/**
 * @Author: Peony
 * @Date: 2020/7/24 9:25
 */
public class BeanUtil {

    public static Object copyProperties(Object source, Object target, String... ignoreProperties) {
        if (source == null) {
            return target;
        }
        //把target深拷贝给source，最后一个传的是忽略的属性String...参数
        BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }


    //检查对象是否全null,全null返回true
    public static boolean checkPojoNullField(Object o, Class<?> clz) {
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (field.get(o) != null) {
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return true;
                }
            }
            return true;
    }
}
