package com.imooc.order.service.impl;

import com.imooc.order.client.ProductClient;
import com.imooc.order.dao.OrderDetailDao;
import com.imooc.order.dao.OrderMasterDao;
import com.imooc.order.dto.CartDTO;
import com.imooc.order.dto.OrderDto;
import com.imooc.order.entity.OrderDetail;
import com.imooc.order.entity.OrderMaster;
import com.imooc.order.entity.ProductInfo;
import com.imooc.order.enums.OrderStatusEnum;
import com.imooc.order.enums.PayStatusEnum;
import com.imooc.order.service.OrderService;
import com.imooc.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by helei on 2018-11-15.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private ProductClient productClient;

    @Override
    public OrderDto create(OrderDto orderDto) {
        String orderId = KeyUtil.genUniqueKey();
        // 查询商品信息（调用商品服务）
        List<String> productIdList = orderDto.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfo> productInfoList = productClient.listForOrder(productIdList);
        // 计算总价
        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);
        for(OrderDetail orderDetail : orderDto.getOrderDetailList()){
            for(ProductInfo productInfo : productInfoList){
                if(productInfo.getProductId().equals(orderDetail.getProductId())){
                    //单价*数量
                    orderAmout = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmout);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    //订单详情入库
                    orderDetailDao.save(orderDetail);
                }
            }
        }
        //扣库存（调动商品服务）
        List<CartDTO> cartDTOList = orderDto.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(cartDTOList);
        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderAmount(orderAmout);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterDao.save(orderMaster);
        return orderDto;
    }
}
