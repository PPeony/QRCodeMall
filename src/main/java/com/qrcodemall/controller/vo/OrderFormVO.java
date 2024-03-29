package com.qrcodemall.controller.vo;

import com.qrcodemall.entity.OrderForm;
import com.qrcodemall.entity.OrderFormDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Peony
 * @Date: 2022/3/28 14:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFormVO {
    private OrderForm orderForm;
    private List<OrderFormDetail> details;
}
