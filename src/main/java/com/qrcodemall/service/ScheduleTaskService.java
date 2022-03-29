package com.qrcodemall.service;

import com.qrcodemall.entity.ScheduleTask;

/**
 * @Author: Peony
 * @Date: 2022/3/29 15:20
 */
public interface ScheduleTaskService {
    Integer createTask(Integer promotionId,Integer goodsId,String startTime,Integer duration);
    ScheduleTask selectTaskByPromotionId(Integer promotionId);
    Integer deleteTask(Integer promotionId);
    Integer deleteTaskByGoodsId(Integer goodsId);
}
