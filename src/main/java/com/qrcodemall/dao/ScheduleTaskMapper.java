package com.qrcodemall.dao;

import com.qrcodemall.entity.ScheduleTask;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/29 15:09
 */
public interface ScheduleTaskMapper {
    Integer insert(Integer promotionId,String startId,String endId);
    List<ScheduleTask> select(Integer promotionId);
    Integer delete(Integer promotionId);
}
