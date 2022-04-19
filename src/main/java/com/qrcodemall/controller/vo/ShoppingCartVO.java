package com.qrcodemall.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Peony
 * @Date: 2022/3/31 11:22
 */
//购物车里面的东西,好像没用上
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartVO implements Serializable {
    private Integer goodsId;

    private Integer goodsTypeId;

    private String goodsName;

    private BigDecimal goodsPrice;

    private String goodsTypeName;

    private Integer goodsQrcodeQuantity;

    private String goodsPicture;

    private String goodsDetail;

    private Integer goodsStatus;

    private Date gmtCreated;

    private Date gmtModified;

    private Integer isDeleted;
}
