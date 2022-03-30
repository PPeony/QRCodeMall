package com.qrcodemall.service.impl;

import com.qrcodemall.dao.PromotionMapper;
import com.qrcodemall.dao.ScheduleTaskMapper;
import com.qrcodemall.entity.PromotionGoods;
import com.qrcodemall.entity.ScheduleTask;
import com.qrcodemall.scheduleTask.DynamicTask;
import com.qrcodemall.scheduleTask.MyTask;
import com.qrcodemall.service.GoodsService;
import com.qrcodemall.service.ScheduleTaskService;
import com.qrcodemall.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Peony
 * @Date: 2022/3/29 15:23
 */
@Service
public class ScheduleTaskServiceImpl implements ScheduleTaskService {
    @Autowired
    ScheduleTaskMapper scheduleTaskMapper;
    @Autowired
    DynamicTask task;
    @Autowired
    PromotionMapper promotionMapper;
    @Autowired
    GoodsService goodsService;

    @Override
    public Integer createTask(Integer promotionId,Integer goodsId, String startTime, Integer duration) {
        String startCron = task.generateCron(startTime);
        Date startTimeDate = DateUtil.strToDate(startTime);
        Long endTimeStamp = startTimeDate.getTime()+duration*60*1000;
        String endTimeCron = task.generateCron(DateUtil.dateToStr(new Date(endTimeStamp)));
        String startId = UUID.randomUUID().toString(), endId = UUID.randomUUID().toString();
        MyTask startTask = new MyTask(startId,promotionId,goodsId,0,goodsService);
        task.startCron(startCron,startId, startTask);
        MyTask endTask = new MyTask(endId,promotionId,goodsId,1,goodsService);
        task.startCron(endTimeCron,endId,endTask);
        Integer r = scheduleTaskMapper.insert(promotionId, startId, endId);
        return r;
    }

    @Override
    public ScheduleTask selectTaskByPromotionId(Integer promotionId) {
        List<ScheduleTask> list = scheduleTaskMapper.select(promotionId);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Integer deleteTask(Integer promotionId) {
        Integer r = scheduleTaskMapper.delete(promotionId);
        return r;
    }

    @Override
    public Integer deleteTaskByGoodsId(Integer goodsId) {
        // 先通过goodsId查出最新的promotionId，然后停止任务
        Integer promotionId = promotionMapper.selectLatestPromotionByGoodsId(goodsId);
//        Integer promotionId = promotionGoods.getPromotionId();
        ScheduleTask scheduleTask = selectTaskByPromotionId(promotionId);
        task.stop(scheduleTask.getStartId());
        task.stop(scheduleTask.getEndId());
        Integer r = scheduleTaskMapper.delete(promotionId);
        return r;
    }
}
