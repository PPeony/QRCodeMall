package com.qrcodemall.rabbitmq;

import com.qrcodemall.entity.Goods;
import com.qrcodemall.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/22 15:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFormMessage implements Serializable {
    private String orderFormId;
    private List<Goods> list;
    private User user;
}
