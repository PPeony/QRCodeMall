package com.qrcodemall.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Peony
 * @Date: 2022/3/24 9:09
 */
@Data
public class GoodsVO implements Serializable {

    private Integer goodsId;
    private Integer goodsTypeId;
    private String goodsName;
    private BigDecimal goodsPrice;
    private String goodsTypeName;
    private Integer goodsQrcodeQuantity;
    private String goodsPicture;
    private String goodsDetail;
    private Integer goodsStatus;
    //diff
    private Date beginTime;
    private Date endTime;
    //over
    private Integer isDeleted;
    private String goodsIntroduction;


}
