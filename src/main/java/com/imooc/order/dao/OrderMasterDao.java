package com.imooc.order.dao;

import com.imooc.order.entity.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by helei on 2018-11-15.
 */
public interface OrderMasterDao extends JpaRepository<OrderMaster,String> {
}
