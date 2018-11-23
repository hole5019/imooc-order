package com.imooc.order.dto;

import com.imooc.order.entity.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by helei on 2018-11-15.
 */
@Data
public class OrderDto {
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;

    private List<OrderDetail> orderDetailList;
}
