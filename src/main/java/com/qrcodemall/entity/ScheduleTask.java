package com.qrcodemall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Peony
 * @Date: 2022/3/29 15:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleTask {
    Integer scheduleTaskId;
    Integer promotionId;
    String startId;
    String endId;
    Integer isDeleted;
}
