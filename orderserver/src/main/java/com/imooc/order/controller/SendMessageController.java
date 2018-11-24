package com.imooc.order.controller;

import com.imooc.order.message.StreamClient;
import com.imooc.order.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by helei on 2018-11-21.
 */
@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

//    @GetMapping("/sendMessage")
//    public void process(){
//        String message = "now " + new Date();
//        streamClient.output().send(MessageBuilder.withPayload(message).build());
//    }

    /**
     * 发送 orderDto对象
     */
    @GetMapping("/sendMessage")
    public void process(){
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId("123456");
        streamClient.output().send(MessageBuilder.withPayload(orderDto).build());
    }
}
