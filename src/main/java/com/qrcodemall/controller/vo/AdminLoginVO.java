package com.qrcodemall.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: Peony
 * @Date: 2022/3/5 15:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginVO {

    @NotEmpty(message = "请输入账号")
    private String account;

    @NotEmpty(message = "请输入密码")
    private String password;
}
