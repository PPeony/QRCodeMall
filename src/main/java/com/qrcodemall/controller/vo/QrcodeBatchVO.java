package com.qrcodemall.controller.vo;

import com.qrcodemall.entity.UserAddress;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Author: Peony
 * @Date: 2020/8/26 16:49
 */
@Data
public class QrcodeBatchVO implements Serializable {
    @NotEmpty(message = "数量不能为空")
    private Integer qrcodeNumber;

    @NotEmpty(message = "二维码id不能为空")
    private Integer qrcodeId;

    @NotEmpty(message = "用户地址id不能为空")
    private Integer userAddressId;

    @NotEmpty(message = "种类不能为空")
    private String goodsTypeName;

    private UserAddress userAddress;
}
