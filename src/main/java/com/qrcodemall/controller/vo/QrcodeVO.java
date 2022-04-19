package com.qrcodemall.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Peony
 * @Date: 2022/3/31 10:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrcodeVO implements Serializable {
    private Integer qrcodeId;

    private Integer userId;

    private String userName;

    private Integer goodsId;

    private String goodsName;

    private String goodsTypeName;

    private String qrcodeLink;

    private Date gmtCreated;

    private Date gmtModified;

    private Integer isDeleted;
}
