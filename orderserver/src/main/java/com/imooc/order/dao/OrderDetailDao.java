package com.imooc.order.dao;

import com.imooc.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by helei on 2018-11-15.
 */
public interface OrderDetailDao extends JpaRepository<OrderDetail,String> {
    /**
     * 根据订单id查询订单详情
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}
