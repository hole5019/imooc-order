package com.imooc.order.service;

import com.imooc.order.dto.OrderDto;

/**
 * Created by helei on 2018-11-15.
 */
public interface OrderService {
    /**
     * 创建订单
     * @param orderDto
     * @return
     */
    public OrderDto create(OrderDto orderDto);

    /**
     * 完结订单(只能卖家操作)
     * @param orderId
     * @return
     */
    OrderDto finish(String orderId);
}
