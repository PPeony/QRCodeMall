package com.qrcodemall.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Peony
 * @Date: 2020/7/27 10:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserException extends RuntimeException{
    private String message;

    private Integer code;


}
