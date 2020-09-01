package com.qrcodemall.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Peony
 * @Date: 2020/9/1 13:44
 */
@Data
@AllArgsConstructor
public class VerifyCodeVO implements Serializable {
    private String userPhone;
    private String verifyCode;
}
