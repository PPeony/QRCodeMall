package com.qrcodemall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Peony
 * @Date: 2022/3/25 14:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test implements Serializable {
    private String id;
    private Integer count;
}
