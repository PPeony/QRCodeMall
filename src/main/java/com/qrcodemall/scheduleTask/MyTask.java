package com.qrcodemall.scheduleTask;

import com.qrcodemall.service.GoodsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Peony
 * @Date: 2022/3/29 13:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyTask implements Runnable {
    @Autowired
    GoodsService goodsService;

    private String id;
    private Integer promotionId;
    private Integer goodsId;
    private Integer type;//表示是开始任务还是结束任务

    public MyTask(String id, Integer promotionId, Integer goodsId, Integer type) {
        this.id = id;
        this.promotionId = promotionId;
        this.goodsId = goodsId;
        this.type = type;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" schedule " + id);
        if (this.type.equals(0)) {
            goodsService.scheduleStartPromotion(this.promotionId,this.goodsId);
        } else if (this.type.equals(1)) {
            goodsService.scheduleStopPromotion(this.promotionId,this.goodsId);
        } else {
            System.out.println("type 出现0/1 以外的值");
        }
    }
}
