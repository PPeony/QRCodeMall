package com.qrcodemall.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Peony
 * @Date: 2020/7/24 9:09
 */
@Data
public class GoodsVO implements Serializable {

    private Integer goodsId;
    private Integer goodsTypeId;
    private String goodsName;
    private Long goodsPrice;
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
