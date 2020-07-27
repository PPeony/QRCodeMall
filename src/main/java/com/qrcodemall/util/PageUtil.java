package com.qrcodemall.util;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.PageProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2020/7/27 11:49
 */
public class PageUtil {


    public static <T,K> PageInfo<T> generatePageInfoByTime(T obj, Integer pageNum, Date beginTime, Date endTime, K mapper) {
        //多个查询都长这样，拽出来写
        PageHelper.startPage(pageNum, PageProperty.PAGESIZE);
        Class clz = obj.getClass();
        List<T> list = new LinkedList<>();
        try {
            Field field = clz.getDeclaredField("isDeleted");
            field.setAccessible(true);
            if (field.get(obj) == null) {
                field.set(obj,0);
            }
            Class mapperClz = mapper.getClass();
            Method method = mapperClz.getDeclaredMethod("selectByEntityAndTime",clz,Date.class,Date.class);

//            System.out.println(obj);
            list = (List<T>) method.invoke(mapper,obj,beginTime,endTime);

        } catch (Exception e) {
            e.printStackTrace();
        }
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
