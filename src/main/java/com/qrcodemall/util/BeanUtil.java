package com.qrcodemall.util;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/24 9:25
 */
// test word
public class BeanUtil {

    public static Object copyProperties(Object source, Object target, String... ignoreProperties) {
        if (source == null) {
            return target;
        }
        //把target深拷贝给source，最后一个传的是忽略的属性String...参数
        BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }

    //判断list里面每个对象的value属性是否有重复的
    public static <T> boolean checkIfRepeat(List<T> list,String value) {
        HashSet<String> set = new HashSet<>();
        try {
            for (T o : list) {
                Field field = o.getClass().getDeclaredField(value);
                field.setAccessible(true);
                String v = (String)field.get(o);
                if (set.contains(v)) {
                    return false;
                }
                set.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
