package com.qrcodemall.common.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Peony
 * @Date: 2020/7/27 10:44
 */
@Data
public class GlobalException extends RuntimeException{

    public GlobalException(String message) {
        super(message);
    }

    public static void fail(String message) {
        throw new GlobalException(message);
    }


}
