package com.imooc.order.dao;

import com.imooc.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by helei on 2018-11-15.
 */
public interface OrderDetailDao extends JpaRepository<OrderDetail,String> {
}
