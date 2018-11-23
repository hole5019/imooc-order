package com.imooc.order.message;

import com.imooc.order.entity.ProductInfo;
import com.imooc.order.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by helei on 2018-11-22.
 */
@Component
@Slf4j
public class ProductInfoReceiver {

    private static final String PRODUCT_STOCK_TEMPLAGE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        ProductInfo productInfo = (ProductInfo) JsonUtil.fromJson(message,ProductInfo.class);
        log.info("从队列【{}】接收到消息：{}","productInfo",productInfo);
        //存储到redis中
        stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLAGE,productInfo.getProductId()),
                String.valueOf(productInfo.getProductStock()));
    }
}
