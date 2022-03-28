package com.qrcodemall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Peony
 * @Date: 2022/3/18 15:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionGoods {
    Integer promotionId;
    Integer goodsId;
    Date promotionStartTime;
    Integer promotionDuration;
    Integer promotionCount;
    BigDecimal promotionValue;
    Integer isDeleted;
}
